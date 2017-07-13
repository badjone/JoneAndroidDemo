package com.badjone.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badjone.UI.Activity.BaseActivity;
import com.badjone.UI.Functions;

/**
 * Fragment基类
 *
 * @author badjone
 * @Date 2016/12/8 11:35
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity fa;
    public View latoutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        latoutView = inflater.inflate(getLayoutId(), container, false);
        getLayoutView(latoutView);
        return latoutView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            findViews();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    /**
     * 函数的集合
     */
    protected Functions mFunctions;

    /**
     * activity调用此方法进行设置Functions
     *
     * @param functions
     */
    public void setFunctions(Functions functions) {
        this.mFunctions = functions;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fa = (BaseActivity) context;
        fa.setFunctionsForFragment(getId());
    }

    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    abstract protected int getLayoutId();

    abstract protected void findViews();

    public void getLayoutView(View v) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}