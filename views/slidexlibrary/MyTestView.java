package newstudiowork.newtest.UI.views.slidexlibrary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import newstudiowork.newtest.R;

/**
 * @author badjone
 * @Date 2016/7/13 11:50
 */
public class MyTestView extends View implements Runnable {
    //初始化paint
    private Paint paint = new Paint();
    //view的宽度
    private int viewWidth;
    //view的高度
    private int viewHeight;
    //背景颜色
    private String viewBgColor = "#FF3E96";
    //矩形区域
    private RectF rectF;
    private Rect rect;
    //x轴偏移值
    private int offsetx = 15;
    //y轴偏移值
    private int offsety = 20;


    public MyTestView(Context context) {
        super(context);
        init();
    }

    BitmapDrawable bitmapDrawable;

    private void init() {

        bitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.progress_blue_groove));
        this.post(this);
    }

    public MyTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor(viewBgColor));
        paint.setAntiAlias(true);

//        rect = new Rect(offsetx, offsety, viewWidth-offsetx, viewHeight-offsety);
        bitmapDrawable.setBounds(rect);
        bitmapDrawable.draw(canvas);

//        drawRect(canvas,bitmapDrawable,offsetx,offsety,viewWidth,viewHeight);

    }


    /**
     * 传统矩形坐标方法
     */
    public void drawRect(Canvas canvas, Drawable d, int x, int y, int width,
                         int height) {
        d.setBounds(x - width / 2, y - height / 2, x + width / 2, y + height
                / 2);
        d.draw(canvas);
    }

    @Override
    public void run() {
        viewWidth = MyTestView.this.getWidth();
        viewHeight = MyTestView.this.getHeight();
        rectF = new RectF(offsetx, offsety,viewWidth - offsetx, viewHeight - offsety);
        //参数 x开始位置，y开始位置，x结束位置,y结束位置
        rect = new Rect(offsetx, offsety,viewWidth - offsetx,  viewHeight - offsety);
        invalidate();
        System.out.println("wid" + (viewWidth - offsetx) + "hei" + (viewHeight - offsety));
    }
}
