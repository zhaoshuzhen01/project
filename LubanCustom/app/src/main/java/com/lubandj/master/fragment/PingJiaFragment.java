package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.adapter.PingJIaAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class PingJiaFragment extends BaseRefreshFragment implements IbaseView<MsgCenterBeen.InfoBean.ListBean> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PingJIaAdapter msgCenterAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter ;
    public static PingJiaFragment newInstance(String index) {
        PingJiaFragment myFragment = new PingJiaFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Override
    public int getLayout() {
        return R.layout.fragment_pingjia;
    }

    @Override
    protected void initView(View view) {
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6)view.findViewById(R.id.prpt);

    }
    @Override
    protected void initData() {
        isFirst = false;
        msgCenterAdapter = new PingJIaAdapter(msgBeens,getActivity());
        initRecyclerView(recyclerView, new LinearLayoutManager(getActivity()), msgCenterAdapter);
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(getActivity(),this,new MsgCenterModel(getActivity()));
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onRefresh() {
        pullToRefreshAndPushToLoadView.finishRefreshing();
    }

    @Override
    public void onLoadMore() {
        pullToRefreshAndPushToLoadView.finishLoading();
    }
    @Override
    public void getDataLists(List<MsgCenterBeen.InfoBean.ListBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        msgBeens.clear();
        msgBeens.addAll(datas);
        msgCenterAdapter.notifyDataSetChanged();
    }
}
