package com.example.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.RefreshLayout;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.util.NetworkUtils;

import butterknife.ButterKnife;


/**
 * Created //
 */

public abstract class BaseRefreshFragment extends BaseFragment implements PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener {
    protected RefreshLayout refreshLayout;
    protected  boolean isVisible = false;
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
    public void initRawRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter) {
        mOriginAdapter =adapter;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mOriginAdapter);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            if (getActivity() != null)
                lazyLoad();
        } else {
            isVisible = false;
        }
    }
    @Override
    protected void lazyLoad() {
        if (isVisible && isFirst) {
            getWebDatas();
        }
    }
    private void getWebDatas() {
        if (NetworkUtils.isNetworkAvailable(getActivity())) {
            initData();
        } else {
        }
    }

}
