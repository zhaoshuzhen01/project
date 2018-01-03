package com.lubandj.master.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IMsgCenterListview;
import com.lubandj.master.Presenter.MsgCenterPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.TestBean;
import com.lubandj.master.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MsgCenterActivity extends BaseRefreshActivity implements IMsgCenterListview {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MsgCenterAdapter msgCenterAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private MsgCenterPresenter msgCenterPresenter ;
    @Override
    public int getLayout() {
        return R.layout.activity_msg_center;
    }

    @Override
    public void initView() {
        CommonUtils.setMsgCount(0);
        ButterKnife.inject(this);
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6)findViewById(R.id.prpt);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setTitleText(R.string.msg_center);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        initData();
    }


    @Override
    public void initData() {

        msgCenterAdapter = new MsgCenterAdapter(msgBeens,this);
        initRecyclerView(recyclerView, new LinearLayoutManager(this), msgCenterAdapter);
        msgCenterPresenter = new MsgCenterPresenter(this,this);
        msgCenterPresenter.getReflushData(0);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 20, 0));
    }
    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {
        
    }

    @Override
    public void onClick(View view) {

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
    public void getMsgCenterLists(List<MsgCenterBeen.InfoBean.ListBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        msgBeens.clear();
        msgBeens.addAll(datas);
        msgCenterAdapter.notifyDataSetChanged();
    }
}
