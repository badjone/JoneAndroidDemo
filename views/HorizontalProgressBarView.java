package newstudiowork.newtest.UI.views;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import newstudiowork.newtest.R;

/**
 * 自定义横向进度条 progressbar
 *
 * @author badjone
 * @Date 2016/8/11 10:06
 */
public class HorizontalProgressBarView extends View {
    //背景图片
    private BitmapDrawable bgDrawable;
    //进度颜色半圆
    private BitmapDrawable righthalfDrawable;
    //进度line
    private BitmapDrawable lineDrawable;
    private BitmapDrawable lefthaltDrawable;
    //宽、高
    private int viewWidth;
    private int viewHeight;
    //进度值
    private int index;
    //进度条高度
    private int proHeight;
    //进度条宽度
    private int proWidth;
    //x轴偏移值
    private int offsetx = 0;
    //y轴偏移值
    private int offsety = 0;
    //小圆半径
    private int radiusHalf;

    public HorizontalProgressBarView(Context context) {
        super(context);
        init();
    }

    public HorizontalProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setStrokeWidth(viewHeight);
//        paint.setColor(Color.parseColor("#000000"));
//        canvas.drawRect(0, 0, viewWidth, viewHeight, paint);

        bgDrawable.setBounds(offsetx, offsety, offsetx+proWidth, proHeight);
        bgDrawable.draw(canvas);
        //真实的进度条长度，包含两个小半圆+进度线，减掉左右偏移值
        double realLenght = proWidth * index / 100d;
        int leftLenght = offsetx + radiusHalf;
        int line_x = (int) (realLenght + offsetx - radiusHalf);
        if (index > 0) {
            if (realLenght < (offsetx + radiusHalf)) {
                //只显示右边半圆 宽度压缩为原来的 ½
                //左边小圆
                drawDrawable(canvas, lefthaltDrawable, offsetx, offsety, offsetx + radiusHalf / 2, proHeight);
                //右边小圆
                drawDrawable(canvas, righthalfDrawable, offsetx + radiusHalf / 2, offsety, offsetx + radiusHalf, proHeight);
            } else {
                //左边小圆
                drawDrawable(canvas, lefthaltDrawable, offsetx, offsety, leftLenght, proHeight);
                //线条
                drawDrawable(canvas, lineDrawable,leftLenght, offsety, line_x, proHeight);
                //右边小圆
                drawDrawable(canvas, righthalfDrawable, line_x, offsety, (line_x + radiusHalf), proHeight);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();

        proWidth = viewWidth - (offsetx * 2);
        proHeight = (int) (proWidth / 17.35);
        if(viewHeight<proHeight){
            viewHeight= proHeight;
        }
        radiusHalf = proHeight / 2;
    }

    //初始化
    private void init() {
        bgDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_bg));
        righthalfDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_schec));
        lineDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_line));
        lefthaltDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_left_half));

    }

    /**
     * 设置进度值
     *
     * @param i
     */
    public void setProgress(int i) {
        index = i;
        invalidate();
    }

    /**
     * draw
     *
     * @param canvas
     * @param drawable
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void drawDrawable(Canvas canvas, BitmapDrawable drawable, int left, int top, int right, int bottom) {
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);
    }

}
