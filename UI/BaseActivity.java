
package com.badjone.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.badjone.manager.ActivityStack;
import com.badjone.utils.CustomToast;
import com.badjone.view.TitleView;
import com.taobao.sophix.SophixManager;

import java.util.Map;


/**
 * 基类activity
 *
 * @author badjone
 * @Date 2016/12/8 11:35
 */
public abstract class BaseActivity extends AppCompatActivity {
    abstract protected int getLayoutId();

    abstract protected void initUI();

    //监听事件
    public void initListener() {
    }

    public void initWidget() {
    }

    public void initData() {
    }

    public boolean isShowDefaultTitle() {
        return false;
    }

    /**
     * mContext: 上下文
     */
    protected Context mContext;
    protected TitleView titleView;
    public Bundle savedInstanceState;
//    protected HeadView headView;
//    private LoadingDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SophixManager.getInstance().queryAndLoadNewPatch();
        mContext = this;
        this.savedInstanceState = savedInstanceState;
        ActivityStack.getInstanceData(this).add();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        showTitle();
        initUI();
        initWidget();
        initListener();
        initData();

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    private void showTitle() {
        if (isShowDefaultTitle()) {
            titleView = new TitleView(this);
            titleView.setContextView(this, getLayoutId());
            titleView.setType(TitleView.TitleType.text);
            titleView.setTitleTxt(getTitle().toString());
        } else {
            setContentView(getLayoutId());
        }
    }

    /**
     * 为fragment设置functions，具体实现子类来做
     *
     * @param fragmentId
     */
    public void setFunctionsForFragment(int fragmentId) {
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityStack.getInstanceData(this).setTopActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * toActivity : 跳转activity
     *
     * @param cls
     */

    public void toActivity(Class<? extends Activity> cls) {
        if (cls == null)
            return;
        startActivity(new Intent(mContext, cls));
    }

    /**
     * 跳转activity 传递参数
     *
     * @param to
     * @param extras
     */
    public void toActivity(Class<?> to, Map<String, Object> extras) {
        Intent i = new Intent(mContext, to);
        putExtras(extras, i);
        startActivity(i);
    }

    /**
     * 跳转activity 传递bundler
     *
     * @param cls
     * @param bundle
     */
    public void toActivity(Class<? extends Activity> cls, Bundle bundle) {
        if (cls == null)
            return;
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }

    /**
     * 取出来传递的bundler
     */
    public Bundle getActivityData() {
        Bundle data = getIntent().getBundleExtra("data");
        return data;
    }

    /**
     * intent 中 传递数据
     *
     * @param extras
     * @param i
     */
    private static void putExtras(Map<String, Object> extras, Intent i) {
        if (extras != null) {
            for (String name : extras.keySet()) {
                Object obj = extras.get(name);
                if (obj instanceof String) {
                    i.putExtra(name, (String) obj);
                }
                if (obj instanceof Integer) {
                    i.putExtra(name, (Integer) obj);
                }
                if (obj instanceof String[]) {
                    i.putExtra(name, (String[]) obj);
                }
                if (obj instanceof Boolean) {
                    i.putExtra(name, (boolean) obj);
                }
            }
        }
    }

    public void toast(String msg) {
        if (mContext != null)
            CustomToast.showToast(mContext, msg);
    }


//    public void showToast(String displayStr) {
//        CustomToast.showToast(this, displayStr, 2000);
//    }
//
//    public boolean isLogin() {
//        if (BaseApplication.getInstance().getUserInfo() != null) return true;
//        return false;
//    }


    @Override
    protected void onDestroy() {
        ActivityStack.getInstanceData(this).remove();
//        stopProgressDialog();
        super.onDestroy();
    }

    private Fragment mFragment;

    /**
     * @param to
     */
    protected void toFragment(Fragment to, int resContent) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mFragment != null) {
            if (mFragment != to) {
                // 先判断是否被add过，没有就添加，有直接show
                if (!to.isAdded()) {
                    ft.hide(mFragment).add(resContent, to);
                } else {
                    ft.hide(mFragment).show(to);
                }
                mFragment = to;
            }
        } else {
            ft.add(resContent, to);
            mFragment = to;
        }
        ft.commit();
    }

    protected String getTvStr(TextView tv) {
        return tv.getText().toString().trim();
    }

    protected void setTvStr(TextView tv, Object str) {
        if (str == null || str.equals("NULL")) {
            tv.setText("");
        } else {
            tv.setText(str.toString());
        }
    }

//    public void setProgressDialogText(String progressText) {
//        if (customProgressDialog == null) {
//            customProgressDialog = new LoadingDialog(this);
//        }
//        customProgressDialog.setMessage(progressText);
//    }

//    /**
//     * 开始progressDialog
//     */
//    public void startProgressDialog() {
//        if (customProgressDialog == null) {
//            customProgressDialog = new LoadingDialog(this);
//        }
//        String message = customProgressDialog.getMessage();
//        if (TextUtils.isEmpty(message)) {
//            customProgressDialog.setMessage("加载中...");
//        }
//        if (!customProgressDialog.isShowing())
//            customProgressDialog.show();
//    }

//    /**
//     * 停止progressDialog
//     */
//    public LoadingDialog getProgressDialog() {
//        if (customProgressDialog == null) {
//            customProgressDialog = new LoadingDialog(this);
//        }
//        return customProgressDialog;
//    }

//    /**
//     * 停止progressDialog
//     */
//    public void stopProgressDialog() {
//        if (customProgressDialog != null) {
//            if (customProgressDialog.isShowing())
//                customProgressDialog.dismiss();
//            customProgressDialog = null;
//        }
//    }
}
