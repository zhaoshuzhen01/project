package com.example.baselibrary;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public abstract class BaseRefreshActivity extends TitleBaseActivity implements PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener  {

    protected PullToRefreshAndPushToLoadView6 pullToRefreshAndPushToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerRefreshLayout();

    }
    private void initRecyclerRefreshLayout() {

        if (allowPullToRefresh()) {
            pullToRefreshAndPushToLoadView.setOnRefreshAndLoadMoreListener(this);
            pullToRefreshAndPushToLoadView.setCanRefresh(true);
            pullToRefreshAndPushToLoadView.setCanAutoLoadMore(true);
        } else {
            pullToRefreshAndPushToLoadView.setCanRefresh(false);
        }
    }

    public boolean allowPullToRefresh() {
        return true;
    }
    public void initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
