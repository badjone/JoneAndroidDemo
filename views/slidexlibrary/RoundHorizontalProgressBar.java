package newstudiowork.newtest.UI.views.slidexlibrary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import newstudiowork.newtest.R;

/**
 * @author badjone
 * @Date 2016/8/10 15:40
 */
public class RoundHorizontalProgressBar extends ProgressBar {
    private BitmapDrawable bitmapDrawable;
    public RoundHorizontalProgressBar(Context context) {
        super(context);
        init();
    }

    public RoundHorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundHorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        bitmapDrawable=new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
