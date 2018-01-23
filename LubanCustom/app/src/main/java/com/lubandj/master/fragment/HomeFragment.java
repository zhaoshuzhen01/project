package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.HomeListAdapter;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.customview.HomeTopView;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class HomeFragment extends BaseRefreshFragment implements IbaseView<MsgCenterBeen.InfoBean.ListBean> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HomeListAdapter homeListAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter;
    private HomeTopView homeTopView;

    public static HomeFragment newInstance(int index) {
        HomeFragment myFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6) view.findViewById(R.id.prpt);

        homeListAdapter = new HomeListAdapter(msgBeens, getActivity());
        homeTopView = new HomeTopView(getActivity());
        homeListAdapter.addHeaderView(homeTopView);
        homeTopView.initViewPager(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2); //spanCount为列数，默认方向vertical
        initRawRecyclerView(recyclerView, manager, homeListAdapter);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(50,50,0,0));
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(getActivity(), this, new MsgCenterModel(getActivity()));
    }

    @Override
    protected void initData() {
        isFirst = false;
        msgCenterPresenter.getReflushData(0);

    }

    @Override
    public void onRefresh() {
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onLoadMore() {
        msgCenterPresenter.getMoreData(0);
    }

    @Override
    public void getDataLists(List<MsgCenterBeen.InfoBean.ListBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        msgBeens.clear();
        msgBeens.addAll(datas);
        homeListAdapter.notifyDataSetChanged();
    }
}
