package newstudiowork.newtest.UI.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import newstudiowork.newtest.R;

/**
 * 自定义的圆形进度条
 *
 * @author badjone
 * @Date 2016/5/4 18:13
 */
public class MyCircularView extends View {
    private Paint mPaint;//画笔
    private int radius = 100;//半径
    private RectF rectF;//绘画区域
    private int maxPro = 100;//最大进度
    private int proNum;//进度
    private final int mTxtStrokeWidth = 2;//绘制text画笔的宽度


    private int StrokeWidth = 10;//画笔划线的宽度
    private int startAngel = 135;//开始角度
    private int sweepAngel=270;//弧度角度，为360时是一个完整的圆，否则是圆弧

    private String topText;//进度文字顶部

    private String bottomText;//进度文字底部

    private int proBgColor;
    private int proColor;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public MyCircularView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.circleview);
        //设置默认颜色
        proBgColor = a.getInt(R.styleable.circleview_proBgColor, Color.GRAY);
        proColor = a.getInt(R.styleable.circleview_proColor, Color.YELLOW);

        mPaint = new Paint();
        rectF = new RectF();
        //释放资源
        a.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        //设置画笔和画布相关属性
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(proBgColor);
        mPaint.setStrokeWidth(StrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.TRANSPARENT);

        // 位置
        rectF.left = StrokeWidth / 2; // 左上角x
        rectF.top = StrokeWidth / 2; // 左上角y
        rectF.right = width - StrokeWidth / 2; // 左下角x
        rectF.bottom = height - StrokeWidth / 2; // 右下角y


        //绘制圆圈进度条背景
        canvas.drawArc(rectF, startAngel, sweepAngel, false, mPaint);

        mPaint.setColor(proColor);
        canvas.drawArc(rectF, startAngel, ((float) proNum / maxPro) * sweepAngel, false, mPaint);


        // 绘制进度文案显示、
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text = proNum + "%";
        int textHeight = height / 4;
        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(topText)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = topText;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            mPaint.setColor(Color.rgb(0x99, 0x99, 0x99));
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(bottomText)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = bottomText;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }

    }

    /**
     * 设置进度
     *
     * @param proNum
     */
    public void setProgress(int proNum) {
        this.proNum = proNum;
        this.invalidate();
    }

    /**
     * 设置开始时的角度
     *
     * @param startAngel
     */
    public void setStartAngel(int startAngel) {
        this.startAngel = startAngel;
    }


    public int getMaxProgress() {
        return maxPro;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxPro = maxProgress;
    }

    public String getTopText() {
        return topText;
    }

    /**
     * 设置进度顶部txt
     *
     * @param topText
     */
    public void setTopText(String topText) {
        this.topText = topText;
    }

    /**
     * 设置进度底部txt
     *
     * @return
     */
    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    /**
     * 设置进度条颜色
     *
     * @param proColor
     */
    public void setProColor(int proColor) {
        this.proColor = proColor;
    }

    /**
     * 设置进度条的圆背景颜色
     *
     * @param proBgColor
     */
    public void setProBgColor(int proBgColor) {

    }
}
