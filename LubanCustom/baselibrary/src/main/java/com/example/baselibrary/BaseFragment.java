package com.example.baselibrary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

//import com.squareup.leakcanary.RefWatcher;

/**
 * fragment基类
 * Created by liel on 16-4-15.
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isFirst = true;
    public View view;// 缓存页面
    public ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);// 先移除
        }
        ButterKnife.inject(this, view);
        initView(view);
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
        lazyLoad();
    }

    protected abstract void initData();


    protected abstract void initView(View view);

    protected abstract void lazyLoad();


    public abstract int getLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public ProgressDialog initProgressDialog(@StringRes int content) {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(content));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
