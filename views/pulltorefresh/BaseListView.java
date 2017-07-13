package newstudiowork.newtest.UI.views.pulltorefresh;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ListView;

/**
 * 去掉滑动到顶部和底部的阴影
 * 
 * @author Administrator
 * 
 */
public class BaseListView extends ListView {

	public BaseListView(Context context) {
		super(context);
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}
}
