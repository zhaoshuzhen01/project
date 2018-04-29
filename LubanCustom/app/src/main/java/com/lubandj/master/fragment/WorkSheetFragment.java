package com.lubandj.master.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.customer.order.OrderDetailsActivity;
import com.lubandj.master.Presenter.SheetListPresenter;
import com.lubandj.master.R;
import com.lubandj.master.activity.CheckStandActivity;
import com.lubandj.master.activity.MainCantainActivity;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.OrderListBeen;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.customview.BackLayout;
import com.lubandj.master.Iview.IworkListView;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.model.workList.WorkListClickModel;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.worksheet.WorkSheetDetailsActivityPhone;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2017/9/5.
 */

public class WorkSheetFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener, IworkListView, WorkSheetAdapter.ClickButton, WorkListClickModel.ClickCallBack {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.work_fragment_contaner)
    RelativeLayout workFragmentContaner;
    private WorkSheetAdapter workSheetAdapter;
    private List<OrderListBeen.InfoBean> worklists = new ArrayList<>();
    private int index;// 0 未完成  1  已完成  2 已取消
    private BackLayout backLayout;
    private SheetListPresenter sheetListPresenter;
    private WorkListClickModel workListClickModel;
    private int currentIndex ;

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
        backLayout = new BackLayout(getActivity());
        backLayout.setOnclick(this);
        backLayout.setNodataText("您还没有登录,请登录后查看订单");
        backLayout.setImg(R.drawable.nologin);
        backLayout.setButtonText("登录/注册");
        workFragmentContaner.addView(backLayout);
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6) view.findViewById(R.id.prpt);
        Bundle bundle = getArguments();
        index = bundle.getInt("index");
        workSheetAdapter = new WorkSheetAdapter(worklists, getActivity(), index, this);
        workSheetAdapter.setOnItemClickListener(this);
        initRecyclerView(recyclerView, new LinearLayoutManager(view.getContext()), workSheetAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 20, 0));
        sheetListPresenter = new SheetListPresenter(getActivity(), this);
        workListClickModel = new WorkListClickModel(getActivity(), this);
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

    protected void lazyLoad() {
        if (!CommonUtils.isLogin()){
            backLayout.setNodataText("您还没有登录,请登录后查看订单");
            backLayout.setImg(R.drawable.nologin);
            backLayout.setButtonText("登录/注册");
            isFirst = true;
            return;
        }
        if (isVisible && isFirst) {
            getWebDatas();
        }
    }

    private void getWebDatas() {
        if (NetworkUtils.isNetworkAvailable(getActivity())&& CommonUtils.isLogin()) {
            initData();
        } else {
            if (worklists != null && worklists.size() > 0) {
                backLayout.setVisibility(View.GONE);
            } else {
                backLayout.setVisibility(View.VISIBLE);
                if (!NetworkUtils.isNetworkAvailable(getActivity())){
                    pullToRefreshAndPushToLoadView.finishRefreshing();
                    pullToRefreshAndPushToLoadView.finishLoading();
                }
            }
        }
    }

    @Override
    protected void initData() {
        isFirst = false;
        sheetListPresenter.getReflushData(index);
    }
    @Override
    public void onRefresh() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())){
            pullToRefreshAndPushToLoadView.finishRefreshing();
        }else {
            sheetListPresenter.getReflushData(index);
        }
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())){
            pullToRefreshAndPushToLoadView.finishLoading();
        }else {
            sheetListPresenter.getMoreData(index);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        /*switch (index) {
            case 0://全部
                Intent intent = new Intent(getActivity(), WorkSheetDetailsActivityPhone.class);
                intent.putExtra(WorkSheetDetailsActivityPhone.KEY_DETAILS_ID, worklists.get(position).getId());
                startActivity(intent);
                break;
            case 1://未完成
                intent = new Intent(getActivity(), WorkSheetDetailsActivityPhone.class);
                intent.putExtra(WorkSheetDetailsActivityPhone.KEY_DETAILS_ID, worklists.get(position).getId());
                startActivity(intent);
                break;
            case 2://评价
                intent = new Intent(getActivity(), WorkSheetDetailsActivityPhone.class);
                intent.putExtra(WorkSheetDetailsActivityPhone.KEY_DETAILS_ID, worklists.get(position).getId());
                startActivity(intent);
                break;
        }*/
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra(OrderDetailsActivity.KEY_DETAILS_ID, worklists.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isLogin()){

           MainCantainActivity.startActivity(getActivity());
        }else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void getWorkLists(List<OrderListBeen.InfoBean> datas) {
        if (dialog != null)
            dialog.dismiss();
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        backLayout.setVisibility(View.GONE);
        worklists.clear();
        worklists.addAll(datas);
        workSheetAdapter.notifyDataSetChanged();
        if (datas!=null&&datas.size()==0){
//            ToastUtils.showShort(getActivity(),"暂无数据");
        }
        if (worklists.size()==0){
            backLayout.setNodataText("您还没有相关订单");
            backLayout.setImg(R.drawable.nodingdan);
            backLayout.setButtonText("立即预约");
            backLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void callClick(OrderListBeen.InfoBean entity,int currentIndex) {
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra(OrderDetailsActivity.KEY_DETAILS_ID, worklists.get(currentIndex).getId());
        startActivity(intent);
    }

    //点击回调
    @Override
    public void clickCallback(int status) {
       if (status!=4){
           worklists.get(currentIndex).setStatus(status+"");
       }else {
           worklists.remove(currentIndex);
       }
        workSheetAdapter.notifyDataSetChanged();

       if (worklists!=null&&worklists.size()==0)
           backLayout.setVisibility(View.VISIBLE);
    }
}
