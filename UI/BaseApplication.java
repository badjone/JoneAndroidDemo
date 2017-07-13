package com.badjone;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.badjone.constant.Constants;
import com.badjone.entity.User;
import com.badjone.manager.AppConfig;
import com.badjone.utils.ACache;
import com.badjone.utils.AppUtil;
import com.badjone.utils.L;
import com.badjone.utils.SPUtil;
import com.badjone.xutil.db.DbUtil;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.badjone.constant.CommonCode.CONNECTTIMEOUT;
import static com.badjone.constant.CommonCode.READTIMEOUT;
import static com.badjone.constant.Constants.SP.SP_GENSTURE;


public class BaseApplication extends Application {

    /**
     * 全局应用实例
     */
    private static BaseApplication mInstance;

    /**
     * 获取全局应用实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 存放全局信息集合
     */
    private HashMap<String, Object> dataMap;

    /**
     * 根据key取出全局信息
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return dataMap.get(key);
    }

    /**
     * 存放全局信息
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        dataMap.put(key, value);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String curProcessName = AppUtil.getAppNameByPid(this);
        if (curProcessName.equals(getPackageName())) {
            mInstance = this;
            //内存泄漏检测
            dataMap = new HashMap<>();
            AppConfig.initApp(this);
            MultiDex.install(this);
            L.d("程序创建:curProcessName:" + curProcessName);
//            L.isDebug = AppUtil.isApkDebugable(this) ? true : false;
            setOkHttp();
            //阿里热修复
            setHotFix();
        }
        L.d("主程序初始化");
    }

    /**
     * 初始化okhttp
     * https://github.com/hongyangAndroid/okhttputils
     */
    private void setOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(CONNECTTIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READTIMEOUT, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    //存用户信息
    public User getUserInfo() {
        User user = (User) get(Constants.App.APP_USERINFO);
        try {
            if (user == null) {
                user = DbUtil.getDbManager().selector(User.class).where("mobile", "=", SPUtil.getData(this, Constants.SP.SP_USER_ID, "")).findFirst();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (user != null) {
            put(Constants.App.APP_USERINFO, user);
        }
        return user;
    }

    public boolean isLogin() {
        if (getUserInfo() == null) return false;
        return true;
    }

    //取用户信息
    public void setUserInfo(Context context, User user) {
        try {
            DbUtil.getDbManager().saveOrUpdate(user);
            BaseApplication.getInstance().put(Constants.App.APP_USERINFO, user);
            SPUtil.saveData(context, Constants.SP.SP_USER_ID, user.mobile);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    public void unLogin() {
        try {
            SPUtil.saveData(this, Constants.SP.SP_USER_ID, "");
            BaseApplication.getInstance().put(Constants.App.APP_USERINFO, null);
            SPUtil.saveData(BaseApplication.getInstance(), "x-token", "");
            ACache.get(this).remove(SP_GENSTURE);//清除手势密码
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 阿里热修复
     * <p>
     * <p>
     * mode: 补丁模式, 0:正常请求模式 1:扫码模式 2:本地补丁模式
     * code: 补丁加载状态码, 详情查看PatchStatusCode类说明
     * info: 补丁加载详细说明, 详情查看PatchStatusCode类说明
     * handlePatchVersion: 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * code: 1 补丁加载成功
     * code: 6 服务端没有最新可用的补丁
     * code: 11 RSASECRET错误，官网中的密钥是否正确请检查
     * code: 12 当前应用已经存在一个旧补丁, 应用重启尝试加载新补丁
     * code: 13 补丁加载失败, 导致的原因很多种, 比如UnsatisfiedLinkError等异常, 此时应该严格检查logcat异常日志
     * code: 16 APPSECRET错误，官网中的密钥是否正确请检查
     * code: 18 一键清除补丁
     * code: 403 签名不匹配,可能是APPID APPSECRET填错，请检测
     */
    private void setHotFix() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion(AppUtil.getVersionName(this))
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            L.e("加载成功");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            L.e("需要重启");
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            SophixManager.getInstance().cleanPatches();
                            L.e("内部引擎异常");
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            L.e("其他错误");
                        }
                    }
                }).initialize();
    }
}
