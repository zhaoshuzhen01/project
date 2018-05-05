package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.CouponsAdapter;
import com.lubandj.master.adapter.GetCoupsAdapter;
import com.lubandj.master.been.Allcon;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.model.AllCouponseList;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GetCoupsActivity extends BaseRefreshActivity implements IbaseView<Allcon.InfoBean> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.in_get)
    TextView inget;
    private GetCoupsAdapter msgCenterAdapter;
    private List<Allcon.InfoBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter;

    @Override
    public int getLayout() {
        return R.layout.activity_msg_center;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GetCoupsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6) findViewById(R.id.prpt);
        setTitleText("领券中心");
        inget.setVisibility(View.GONE);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        initData();
    }

    @Override
    public void initData() {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        msgCenterAdapter = new GetCoupsAdapter(msgBeens, this);
        initRecyclerView(recyclerView, new LinearLayoutManager(this), msgCenterAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(25, 25, 0, 25));

        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(this, this, new AllCouponseList(this));
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

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
    public void getDataLists(List<Allcon.InfoBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        msgBeens.clear();
        if (datas!=null)
        msgBeens.addAll(datas);
//        msgBeens.addAll(NotifyMsgInstance.getInstance().getNotifyBeens());
        msgCenterAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.in_get)
    public void onClick() {

    }
}
