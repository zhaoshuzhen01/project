package com.lubandj.master.my;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.DensityUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.ILeaveRecordListview;
import com.lubandj.master.Presenter.LeaveRecordPresenter;
import com.lubandj.master.Presenter.MsgCenterPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.LeaveRecordAdapter;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.adapter.RecycleViewDivider;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.LeaveBean;
import com.lubandj.master.databinding.ActivityLeavelistBinding;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class LeaveListActivity extends BaseActivity implements ILeaveRecordListview, PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener {
    private ActivityLeavelistBinding binding;
    private LeaveRecordAdapter mAdapter;
    private LeaveRecordPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leavelist);
        initRecyclerRefreshLayout();
        mAdapter = new LeaveRecordAdapter(this);
        initRecyclerView(binding.rvLeaverecord, new LinearLayoutManager(this), mAdapter);
        mPresenter = new LeaveRecordPresenter(this, this);
        mPresenter.getReflushData(CommonUtils.getUid());
        binding.rvLeaverecord.addItemDecoration(new RecycleViewDivider(
                LeaveListActivity.this, LinearLayoutManager.VERTICAL, DensityUtils.dip2px(LeaveListActivity.this, 10f), getResources().getColor(R.color.color_eeeeee)));
    }

    private void initRecyclerRefreshLayout() {

        if (allowPullToRefresh()) {
            binding.prptLeaverecord.setOnRefreshAndLoadMoreListener(this);
            binding.prptLeaverecord.setCanRefresh(true);
            binding.prptLeaverecord.setCanAutoLoadMore(true);
        } else {
            binding.prptLeaverecord.setCanRefresh(false);
        }
    }

    public boolean allowPullToRefresh() {
        return true;
    }


    @Override
    public int getLayout() {
        return R.layout.activity_leavelist;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getLeaveRecords(List<LeaveBean> datas) {
        binding.prptLeaverecord.finishRefreshing();
        binding.prptLeaverecord.finishLoading();
        mAdapter.setData(datas);
        if (datas.size() == 0) {
            binding.llNoData.setVisibility(View.VISIBLE);
        } else {
            binding.llNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getReflushData(CommonUtils.getUid());
    }

    @Override
    public void onLoadMore() {
        mPresenter.getMoreData(CommonUtils.getUid());
    }

    public void initRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void onBack(View view) {
        finish();
    }

    public void onAskForLeave(View view) {
        Intent intent = new Intent(LeaveListActivity.this, AskForLeaveActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                onRefresh();
            }
        }
    }
}