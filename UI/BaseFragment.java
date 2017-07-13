package newstudiowork.newtest.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 *
 * @author: wugx
 * @date: 2016-02-22 15:34
 */
public abstract class BaseFragment extends Fragment {
    private View view;
    abstract protected int getLayoutId();

    abstract protected void findViews(View view);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(getLayoutId(), container, false);
        findViews(view);
        return view;
    }
}
