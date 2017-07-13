package newstudiowork.newtest.UI.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决scrollview 嵌套Gridview时高度显示一行的问题
 *
 * @author wugaoxiang
 * @date 2016-1-27
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
} 
