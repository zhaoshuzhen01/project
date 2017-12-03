package com.lubandj.master.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.listener.OnItemClickListener;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.TestBean;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2017/9/5.
 */

public class WorkSheetFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener{
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WorkSheetAdapter workSheetAdapter;
    private List<TestBean> testBeen = new ArrayList<>();
    private boolean isVisible = false;
    private int index;

    public static WorkSheetFragment newInstance(int index) {
        WorkSheetFragment myFragment = new WorkSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_worksheet;
    }

    @Override
    protected void initView(View view) {
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6)view.findViewById(R.id.prpt);
        Bundle bundle = getArguments();
        index = bundle.getInt("index");
            for (int i = 0; i < 20; i++) {
                testBeen.add(new TestBean("", ""));
            }
        workSheetAdapter = new WorkSheetAdapter(testBeen,getActivity());
        workSheetAdapter.setOnItemClickListener(this);
        initRecyclerView(recyclerView, new LinearLayoutManager(view.getContext()), workSheetAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15, 15, 15, 0));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false ;
        }
    }
    protected void lazyLoad() {
        if (isVisible && isFirst) {
            initData();
        }
    }

    @Override
    protected void initData() {
       isFirst = false ;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onRefresh() {
        pullToRefreshAndPushToLoadView.finishRefreshing();
    }

    @Override
    public void onLoadMore() {
        pullToRefreshAndPushToLoadView.finishLoading();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
        intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_TYPE, position % 5);
        startActivity(intent);
    }
}
