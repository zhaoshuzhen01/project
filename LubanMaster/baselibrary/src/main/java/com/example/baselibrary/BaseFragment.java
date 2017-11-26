package com.example.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.squareup.leakcanary.RefWatcher;

/**
 * fragment基类
 * Created by liel on 16-4-15.
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isFirst = true;
    public View view;// 缓存页面

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);// 先移除
        }
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract void initData();


    protected abstract void initView();


    public abstract int getLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
