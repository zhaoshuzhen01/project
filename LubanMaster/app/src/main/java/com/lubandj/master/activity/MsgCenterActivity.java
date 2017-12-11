package com.lubandj.master.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.TestBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MsgCenterActivity extends BaseRefreshActivity {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MsgCenterAdapter msgCenterAdapter;
    private List<TestBean> testBeen = new ArrayList<>();
    @Override
    public int getLayout() {
        return R.layout.activity_msg_center;
    }

    @Override
    public void initView() {
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
        for (int i = 0; i < 20; i++) {
            testBeen.add(new TestBean("", ""));
        }
        msgCenterAdapter = new MsgCenterAdapter(testBeen,this);
        initRecyclerView(recyclerView, new LinearLayoutManager(this), msgCenterAdapter);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 20, 0));
    }
    @Override
    public void titleLeftClick() {
        finish();
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        toast(this,"刷新");
        pullToRefreshAndPushToLoadView.finishRefreshing();
    }

    @Override
    public void onLoadMore() {
        toast(this,"加载更多");
        pullToRefreshAndPushToLoadView.finishLoading();
    }
}
