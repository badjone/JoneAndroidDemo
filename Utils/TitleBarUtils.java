package newstudiowork.newtest.UI.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import newstudiowork.newtest.R;

/**
 * 头部标题栏title
 *
 * @author: wugx
 * @date: 2016-03-17 17:10
 */
public class TitleBarUtils {

    /**
     * 传值为空则不显示
     *
     * @param title    中间标题
     * @param rightTxt 右边按钮
     */
    public static void showTitle(final Activity activity, String title, String rightTxt) {
        ImageButton img_titile_back = (ImageButton) activity.findViewById(R.id.img_titile_back);
        TextView tv_title_center = (TextView) activity.findViewById(R.id.tv_title_center);
        Button btn_title_right = (Button) activity.findViewById(R.id.btn_title_right);
        img_titile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        if (!"".equals(title) || title != null) {
            tv_title_center.setVisibility(View.VISIBLE);
            tv_title_center.setText(title);
        } else {
            tv_title_center.setVisibility(View.INVISIBLE);
        }
        if (!"".equals(rightTxt) && rightTxt != null) {
            btn_title_right.setText(rightTxt);
            btn_title_right.setVisibility(View.VISIBLE);
        } else {
            btn_title_right.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * * 中间切换按钮的title
     *
     * @param activity      当前activity
     * @param showCenterBtn 是否显示中间的button
     * @param menuTxt            String[]  0为左按钮，1为右按钮，2为右边文字按钮  没有可以传null
     * @param oncheck       radiogroup的切换监听事件
     * @param onclick       右边按钮点击监听事件
     */
    public static void showCenterBtn(final Activity activity, boolean showCenterBtn, String[] menuTxt, RadioGroup.OnCheckedChangeListener oncheck, View.OnClickListener onclick) {
        ImageButton img_titile_back = (ImageButton) activity.findViewById(R.id.img_titile_back);
        Button btn_title_right = (Button) activity.findViewById(R.id.btn_title_right);
        LinearLayout ll_title_two_button = (LinearLayout) activity.findViewById(R.id.ll_title_two_button);
        RadioGroup rg_title_menu = (RadioGroup) activity.findViewById(R.id.rg_title_menu);
        RadioButton rb_title_left = (RadioButton) activity.findViewById(R.id.rb_title_left);
        RadioButton rb_title_right = (RadioButton) activity.findViewById(R.id.rb_title_right);
        if (showCenterBtn) {
            ll_title_two_button.setVisibility(View.VISIBLE);
            rg_title_menu.check(R.id.rb_title_left);
            rb_title_left.setText(menuTxt[0]);
            rb_title_right.setText(menuTxt[1]);
            rg_title_menu.setOnCheckedChangeListener(oncheck);
        } else {
            ll_title_two_button.setVisibility(View.GONE);
        }
        img_titile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        if (!"".equals(menuTxt[2]) || menuTxt[2] != null) {
            btn_title_right.setText(menuTxt[2]);
            if (null != onclick) {
                btn_title_right.setOnClickListener(onclick);
            }
            btn_title_right.setVisibility(View.VISIBLE);
        } else {
            btn_title_right.setVisibility(View.GONE);
        }
    }
}
