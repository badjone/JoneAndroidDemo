package newstudiowork.newtest.UI.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import newstudiowork.newtest.R;
import newstudiowork.newtest.UI.BaseApplication;

/**
 * Activity基类
 *
 * @author: wugx
 * @date: 2016-02-22 15:31
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected abstract void findViews();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().activitys.add(BaseActivity.this);
        setContentView(getLayoutId());
        setTheme(R.style.AppTheme_NoActionBar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViews();
    }
}
