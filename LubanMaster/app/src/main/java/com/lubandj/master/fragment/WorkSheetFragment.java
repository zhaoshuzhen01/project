package com.lubandj.master.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.RefreshLayout;
import com.example.baselibrary.refresh.listener.OnItemClickListener;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.TestBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2017/9/5.
 */

public class WorkSheetFragment extends BaseRefreshFragment {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WorkSheetAdapter workSheetAdapter ;
    private List<TestBean> testBeen = new ArrayList<>();
    private boolean isVisible;
    private int index ;

    public static WorkSheetFragment newInstance(int index) {
        WorkSheetFragment myFragment = new WorkSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
//        bundle.putSerializable(DATAS, bean);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_worksheet;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        index = bundle.getInt("index");
        if (index==0){
            for (int i=0;i<20;i++){
                testBeen.add(new TestBean("",""));
            }
        }
        workSheetAdapter = new WorkSheetAdapter(testBeen);
        initRecyclerView(recyclerView, new LinearLayoutManager(view.getContext()), workSheetAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5,5,15,0));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (isFirst) {
//            refreshLayout.startRefresh();
            initData();
        }
    }

    @Override
    protected void initData() {
        if (isVisible && isFirst) {
//            getData(1);
        }
    }

    protected void onInvisible() {
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    protected void onRefresh() {
        ToastUtils.showShort("刷新");
        requestComplete();
    }

    @Override
    protected void onLoadMore() {
        ToastUtils.showShort("加载更多");
        requestComplete();
    }
}
