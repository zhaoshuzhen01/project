package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.adapter.PingJIaAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.PingJiaBeen;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;
import com.lubandj.master.model.PingJiaModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class PingJiaFragment extends BaseRefreshFragment implements IbaseView<PingJiaBeen.InfoBean.ResultBean> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PingJIaAdapter msgCenterAdapter;
    private List<PingJiaBeen.InfoBean.ResultBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter ;
    private String service_id;
    private RelativeLayout top_lay_pingjia;

    public static PingJiaFragment newInstance(String index) {
        PingJiaFragment myFragment = new PingJiaFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("serviceId", index);
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
        service_id = (String) getArguments().get("serviceId");
        top_lay_pingjia = view.findViewById(R.id.top_lay_pingjia);
        top_lay_pingjia.setVisibility(View.GONE);
    }
    @Override
    protected void initData() {
        isFirst = false;
        msgCenterAdapter = new PingJIaAdapter(msgBeens,getActivity());
        initRecyclerView(recyclerView, new LinearLayoutManager(getActivity()), msgCenterAdapter);
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(getActivity(),this,new PingJiaModel(getActivity()));
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onRefresh() {
//        pullToRefreshAndPushToLoadView.finishRefreshing();
        msgCenterPresenter.getReflushData(Integer.parseInt(service_id));
    }

    @Override
    public void onLoadMore() {
//        pullToRefreshAndPushToLoadView.finishLoading();
        msgCenterPresenter.getMoreData(Integer.parseInt(service_id));
    }
    @Override
    public void getDataLists(List<PingJiaBeen.InfoBean.ResultBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        if (datas==null){

        }else {
            msgBeens.clear();
            msgBeens.addAll(datas);
            msgCenterAdapter.notifyDataSetChanged();
        }
    }
}
