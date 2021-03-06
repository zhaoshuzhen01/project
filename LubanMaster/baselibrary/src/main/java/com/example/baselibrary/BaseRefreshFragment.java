package com.example.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.RefreshLayout;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;


/**
 * Created by dingboyang on 2017/5/27.//
 */

public abstract class BaseRefreshFragment extends BaseFragment implements PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener {
    protected RefreshLayout refreshLayout;
    protected PullToRefreshAndPushToLoadView6 pullToRefreshAndPushToLoadView;
    private BaseQuickAdapter mOriginAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerRefreshLayout(view);
    }

    private void initRecyclerRefreshLayout(View view) {

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

    public boolean isFirstPage() {
        return mOriginAdapter==null || mOriginAdapter.getItemCount() <= 0;
    }

    public void initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter) {
        mOriginAdapter =adapter;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mOriginAdapter);
    }


}
