package newstudiowork.newtest.UI.views;

/**
 * @author badjone
 * @Date 2016/5/5 10:07
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 弧形进度条
 *
 * @author Eric
 */
public class ArcProgressbar2 extends View {

    public ArcProgressbar2(Context context) {
        super(context);
    }

    public ArcProgressbar2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init(canvas);
    }

    private void init(Canvas canvas) {
        // 画弧形的矩阵区域。
        rectBg = new RectF(15, 15, diameter, diameter);

        // 计算弧形的圆心和半径。
        int cx1 = (diameter + 15) / 2;
        int cy1 = (diameter + 15) / 2;
        int arcRadius = (diameter - 15) / 2;
        // ProgressBar结尾和开始画2个圆，实现ProgressBar的圆角。

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);//抗锯齿

        mPaintDroitBg = new Paint();
        mPaintDroitBg.setAntiAlias(true);//抗锯齿
        Log.e("进度值","进度的值为" + progress);
        //进度不同状态时边缘显示的颜色
        if (progress == 0) {
            mPaintDroitBg.setColor(Color.WHITE);
        } else if (progress > 0 && progress < 100 * 2.6) {
            mPaintDroitBg.setColor(Color.WHITE);//设置画笔的颜色
        } else if (progress == 100 * 2.6) {
            mPaintDroitBg.setColor(Color.RED);//设置画笔的颜色
        }
        Paint paint11 = new Paint();
        paint11.setAntiAlias(true);//抗锯齿
        paint11.setColor(Color.RED);//设置画笔的颜色

        canvas.drawCircle(
                (float) (cx1 + arcRadius * Math.cos(startAngle * 3.14 / 180)),
                (float) (cy1 + arcRadius * Math.sin(startAngle * 3.14 / 180)),
                bgStrokeWidth / 2, paint11);// 右边小圆

        canvas.drawCircle(
                (float) (cx1 + arcRadius
                        * Math.cos((180 - startAngle) * 3.14 / 180)),
                (float) (cy1 + arcRadius
                        * Math.sin((180 - startAngle) * 3.14 / 180)),
                bgStrokeWidth / 2, mPaintDroitBg);// 左边小圆

        // 弧形背景。
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Style.STROKE);
        mPaintBg.setStrokeWidth(bgStrokeWidth);
        mPaintBg.setColor(bgColor);

        canvas.drawArc(rectBg, startAngle, -endAngle, false, mPaintBg);

        // 弧形小背景。
        if (showSmallBg) {
            mPaintSmallBg = new Paint();
            mPaintSmallBg.setAntiAlias(true);
            mPaintSmallBg.setStyle(Style.STROKE);
            mPaintSmallBg.setStrokeWidth(barStrokeWidth);
            mPaintSmallBg.setColor(smallBgColor);
            canvas.drawArc(rectBg, startAngle, endAngle, false, mPaintSmallBg);
        }

        // 弧形ProgressBar。
        mPaintBar = new Paint();
        mPaintBar.setAntiAlias(true);
        mPaintBar.setStyle(Style.STROKE);
        mPaintBar.setStrokeWidth(barStrokeWidth);
        mPaintBar.setColor(barColor);

        canvas.drawArc(rectBg, startAngle, -progress, false, mPaintBar);

        // 随ProgressBar移动的圆。
        if (showMoveCircle) {
            mPaintCircle.setColor(barColor);
            canvas.drawCircle(
                    (float) (cx1 + arcRadius
                            * Math.cos((-progress+startAngle) * 3.14 / 180)),
                    (float) (cy1 + arcRadius
                            * Math.sin((-progress+startAngle) * 3.14 / 180)),
                    bgStrokeWidth / 2, mPaintCircle);// 小圆
        }
    }

    public void addProgress(int _progress) {
        progress += _progress * 2.6;
//        angleOfMoveCircle += _progress * 2.6;
        System.out.println(progress);
        if (progress > endAngle) {
            progress = 0;
//            angleOfMoveCircle = startAngle;
        }
        invalidate();
    }

    /**
     * 设置弧形背景的画笔宽度。
     */
    public void setBgStrokeWidth(int bgStrokeWidth) {
        this.bgStrokeWidth = bgStrokeWidth;
    }

    /**
     * 设置弧形ProgressBar的画笔宽度。
     */
    public void setBarStrokeWidth(int barStrokeWidth) {
        this.barStrokeWidth = barStrokeWidth;
    }

    /**
     * 设置弧形背景的颜色。
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 设置弧形ProgressBar的颜色。
     */
    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    /**
     * 设置弧形小背景的颜色。
     */
    public void setSmallBgColor(int smallBgColor) {
        this.smallBgColor = smallBgColor;
    }

    /**
     * 设置弧形的直径。
     */
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    /**
     * 是否显示小背景。
     */
    public void setShowSmallBg(boolean showSmallBg) {
        this.showSmallBg = showSmallBg;
    }

    /**
     * 是否显示移动的小圆。
     */
    public void setShowMoveCircle(boolean showMoveCircle) {
        this.showMoveCircle = showMoveCircle;
    }

    private int bgStrokeWidth = 44;
    private int barStrokeWidth = 15;
    private int bgColor = Color.GRAY;
    private int barColor = Color.RED;
    private int smallBgColor = Color.WHITE;
    private int progress = 0;
//    private int angleOfMoveCircle = 40;// 移动小园的起始角度。
    private int startAngle = 40;
    private int endAngle = 260;
    private Paint mPaintBar = null;
    private Paint mPaintSmallBg = null;
    private Paint mPaintBg = null;
    private Paint mPaintCircle = null;
    private Paint mPaintDroitBg = null;
    private RectF rectBg = null;
    /**
     * 直徑。
     */
    private int diameter = 450;

    private boolean showSmallBg = true;// 是否显示小背景。
    private boolean showMoveCircle = true;// 是否显示移动的小园。

}

