package com.lubandj.master.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.master.Iview.IworkListView;
import com.lubandj.master.Presenter.SheetListPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.IntroduceAdapter;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.customview.BackLayout;
import com.lubandj.master.model.workList.WorkListClickModel;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceFragment extends BaseFragment  {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private IntroduceAdapter introduceAdapter ;
    protected  boolean isVisible = false;
    public static IntroduceFragment newInstance(int index) {
        IntroduceFragment myFragment = new IntroduceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragmnet_introduce;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.inject(this,view);
        introduceAdapter = new IntroduceAdapter(msgBeens,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(introduceAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (getActivity() != null)
                lazyLoad();
        } else {
            isVisible = false;
        }
    }

    protected void lazyLoad() {
        if (isVisible && isFirst) {
            initData();
        }
    }

    @Override
    protected void initData() {
        isFirst = false;
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        introduceAdapter.notifyDataSetChanged();
    }



}