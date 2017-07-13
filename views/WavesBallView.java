package newstudiowork.newtest.UI.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author badjone
 * @Date 2016/5/27 10:45
 */
public class WavesBallView extends FrameLayout {
    public WavesBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WavesBallView(Context context) {
        super(context);
    }

    /*继承viewgroup下的view一般用dispatchDraw,继承view则用onDraw*/
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


    }
}
