package newstudiowork.newtest.UI.Utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author: wugx
 * @date: 2016-02-22 16:23
 */
public class ToastUtils {

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    //static long lasttime = 0;
    static Toast toast;

    public static void show(Context context, CharSequence text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
        // if(System.currentTimeMillis()-lasttime>1000) {
        // toast.show();
        // lasttime = System.currentTimeMillis();
        // }else {
        // toast.cancel();
        // }
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }

    /**
     * 中间位置弹出的toast
     *
     * @param context
     * @param text
     */
    public static void centerShow(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
