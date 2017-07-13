package newstudiowork.newtest.UI.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author badjone
 * @Date 2016/5/11 10:34
 */
public class MyScrollView extends ScrollView {
    private static String TAG = "MyScrollView";

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    //处理
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "处理>>onTouchEvent");
        return super.onTouchEvent(ev);
    }

    //分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "分发>>dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    //拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "拦截>>onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
