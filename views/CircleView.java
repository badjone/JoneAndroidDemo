package newstudiowork.newtest.UI.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author badjone
 * @Date 2016/4/29 10:03
 */
public class CircleView extends View {

    Paint paint, mPaintBg,mPaintBar,mPaintSmallBg;
//    RectF area;
//    int value = 100;
//    LinearGradient shader;

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CircleView(Context context) {
        super(context);
    }


    //    public void init() {
//
//
//        paint = new Paint();
//        paint.setStrokeWidth(50f);
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);
//        textpaint = new Paint();
//        textpaint.setTextSize(50f);
//        textpaint.setColor(Color.WHITE);
//        area = new RectF(100, 100, 500, 500);
//
//        shader = new LinearGradient(0, 0, 400, 0, new int[]{
//                Color.BLUE, Color.WHITE}, null,
//                Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//
//    }
    private RectF mRectF = null;

    private int diameter = 480;//直径
    private int startAngle=140;//开始倾斜角度
    private int bgStrokeWidth=44;//

    @Override
    protected void onDraw(Canvas canvas) {
//        init();
        //画弧形的范围 向右向下的距离
        mRectF = new RectF(40, 40, diameter + 20, diameter + 20);
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.RED);//画笔颜色

        int cx1 = (diameter + 15) / 2;
        int cy1 = (diameter + 15) / 2;
        int arcRadius = (diameter - 15) / 2;

        canvas.drawCircle(
                (float) (cx1 + arcRadius * Math.cos(startAngle * 3.14 / 180)),
                (float) (cy1 + arcRadius * Math.sin(startAngle * 3.14 / 180)),
                bgStrokeWidth / 2, paint);

        canvas.drawCircle(
                (float) (cx1 + arcRadius
                        * Math.cos((180 - startAngle) * 3.14 / 180)),
                (float) (cy1 + arcRadius
                        * Math.sin((180 - startAngle) * 3.14 / 180)),
                bgStrokeWidth / 2, paint);// 小圆

        // 弧形背景。
        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.STROKE);
        mPaintBg.setStrokeWidth(30);
        mPaintBg.setColor(Color.YELLOW);
        canvas.drawArc(mRectF, startAngle, 260, false, mPaintBg);

        // 弧形小背景。
            mPaintSmallBg = new Paint();
            mPaintSmallBg.setAntiAlias(true);
            mPaintSmallBg.setStyle(Paint.Style.STROKE);
            mPaintSmallBg.setStrokeWidth(40);
            mPaintSmallBg.setColor(Color.GRAY);
            canvas.drawArc(mRectF, startAngle, 260, false, mPaintSmallBg);

        // 弧形ProgressBar。
        mPaintBar = new Paint();
        mPaintBar.setAntiAlias(true);
        mPaintBar.setStyle(Paint.Style.STROKE);
        mPaintBar.setStrokeWidth(20);
        mPaintBar.setColor(Color.BLACK);
        canvas.drawArc(mRectF, startAngle, 260, false, mPaintBar);



        invalidate();
    }

}