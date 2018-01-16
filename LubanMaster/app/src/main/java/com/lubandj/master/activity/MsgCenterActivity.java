package com.lubandj.master.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.InstanceUtil.NotifyMsgInstance;
import com.lubandj.master.Iview.IMsgCenterListview;
import com.lubandj.master.Presenter.MsgCenterPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.TestBean;
import com.lubandj.master.db.DbInstance;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        DbInstance.getInstance().queryDatas();
//        msgBeens.addAll(NotifyMsgInstance.getInstance().getNotifyBeens());
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
//        msgBeens.addAll(NotifyMsgInstance.getInstance().getNotifyBeens());
        sort(msgBeens);
        msgCenterAdapter.notifyDataSetChanged();
    }

    private void sort(List<MsgCenterBeen.InfoBean.ListBean> msgBeens) {
        //排序规则，这里是以年龄先排序，如果年龄相同
        Comparator<MsgCenterBeen.InfoBean.ListBean> comparator = new Comparator<MsgCenterBeen.InfoBean.ListBean>() {
            public int compare(MsgCenterBeen.InfoBean.ListBean s1, MsgCenterBeen.InfoBean.ListBean s2) {
                String time1 = s1.getDatatime();
                String time2 = s2.getDatatime();

             return (-(int) (StringUtil.getTimeMill(time1)-StringUtil.getTimeMill(time2)));
            }
        };

        //这里就会自动根据规则进行排序
        Collections.sort(msgBeens, comparator);
    }
}
