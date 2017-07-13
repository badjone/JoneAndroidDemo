package newstudiowork.newtest.UI;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Application
 *
 * @author: wugx
 * @date: 2016-02-22 16:57
 */
public class BaseApplication extends Application {
    //所有activity的管理集合类
    public ArrayList<Activity> activitys = new ArrayList<Activity>();
    public static BaseApplication mInstance;

    public SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }


    public SQLiteDatabase getDb() {
        return db;
    }


    /**
     * 退出账号（部分数据需要清除）， 退出app（只是关闭所有的界面）
     */
    public static final int EXIT_ACCOUNT = 0;
    public static final int EXIT_APP = 1;
    public static final int EXIT_ALERT_PWD = 2;

    public void exit(int type) {
        if (type == EXIT_ACCOUNT || type == EXIT_ALERT_PWD) {
            //做一些用户退出的其他操作
        }
        // mScreenObserver.stopScreenStateUpdate();
        for (Activity activity : activitys) {
            activity.finish();
        }
    }
}
