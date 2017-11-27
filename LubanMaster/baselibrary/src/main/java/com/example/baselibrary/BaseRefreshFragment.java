package com.example.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.RefreshLayout;


/**
 * Created by dingboyang on 2017/5/27.
 */

public abstract class BaseRefreshFragment extends BaseFragment{
    protected RefreshLayout refreshLayout;

    private RecyclerView mRecyclerView;

    private BaseQuickAdapter mOriginAdapter;

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerRefreshLayout(view);
    }

    private void initRecyclerRefreshLayout(View view) {

        if (refreshLayout == null) {
            return;
        }
        if (allowPullToRefresh()) {
            refreshLayout.setNestedScrollingEnabled(true);
            refreshLayout.addOnPullListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefreshing(RefreshLayout refreshLayout) {
                    onRefresh();
                }
            });
        } else {
            refreshLayout.setEnabled(false);
        }
    }

    public boolean allowPullToRefresh() {
        return true;
    }

    public boolean isFirstPage() {
        return mOriginAdapter==null || mOriginAdapter.getItemCount() <= 0;
    }

    public void initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter) {
        mRecyclerView = recyclerView;
        mOriginAdapter =adapter;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mOriginAdapter);
    }

    public void requestComplete() {

        if (refreshLayout != null)
            refreshLayout.setRefreshing(false);

        if(mOriginAdapter!= null)
            mOriginAdapter.loadMoreComplete();
    }
    /**
     * 滑动到底部
     */
    public void requestEnd() {
        if(mOriginAdapter!= null)
            mOriginAdapter.loadMoreEnd();
    }

    /**
     * 加载错误
     */
    public void requestFailure() {
        if(mOriginAdapter!= null)
            mOriginAdapter.loadMoreFail();
    }
}
