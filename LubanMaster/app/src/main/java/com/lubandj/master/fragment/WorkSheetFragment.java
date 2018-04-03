package com.lubandj.master.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lubandj.master.Canstance;
import com.lubandj.master.Presenter.SheetListPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkSheetAdapter;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.been.WorkSheetDetailBean;
import com.lubandj.master.customview.BackLayout;
import com.lubandj.master.Iview.IworkListView;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.model.workList.WorkListClickModel;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.NetworkUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.lubandj.master.TApplication.context;

/**
 * Created by ${zhaoshuzhen} on 2017/9/5.
 */

public class WorkSheetFragment extends BaseRefreshFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener, IworkListView, WorkSheetAdapter.ClickButton, WorkListClickModel.ClickCallBack {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.work_fragment_contaner)
    RelativeLayout workFragmentContaner;
    private WorkSheetAdapter workSheetAdapter;
    private List<WorkListBeen.InfoBean> worklists = new ArrayList<>();
    private boolean isVisible = false;
    private int index;// 0 未完成  1  已完成  2 已取消
    private BackLayout backLayout;
    private SheetListPresenter sheetListPresenter;
    private WorkListClickModel workListClickModel;
    private int currentIndex;

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
        if (isVisible && isFirst) {
            getWebDatas();
        }
    }

    private void getWebDatas() {
        if (NetworkUtils.isNetworkAvailable(getActivity())) {
            initData();
        } else {
            if (worklists != null && worklists.size() > 0) {
                backLayout.setVisibility(View.GONE);
            } else {
                backLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void initData() {
        isFirst = false;
        sheetListPresenter.getReflushData(index);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onRefresh() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            pullToRefreshAndPushToLoadView.finishRefreshing();
            pullToRefreshAndPushToLoadView.finishLoading();
            return;
        }
        sheetListPresenter.getReflushData(index);
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            pullToRefreshAndPushToLoadView.finishRefreshing();
            pullToRefreshAndPushToLoadView.finishLoading();
            return;
        }
        sheetListPresenter.getMoreData(index);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (index) {
            case 0://工单未完成
                Intent intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, worklists.get(position).getId());
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAIL_LAT, worklists.get(position).getLat());
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAIL_LNG, worklists.get(position).getLng());
                queryStatus(position, worklists.get(position).getId());
                break;
            case 1://工单已完成
                intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, worklists.get(position).getId());
                startActivity(intent);
                break;
            case 2://工单已取消
                intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, worklists.get(position).getId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        getWebDatas();
    }

    @Override
    public void getWorkLists(List<WorkListBeen.InfoBean> datas) {
        if (dialog != null)
            dialog.dismiss();
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        backLayout.setVisibility(View.GONE);
        worklists.clear();
        worklists.addAll(datas);
        workSheetAdapter.notifyDataSetChanged();
        if (datas != null && datas.size() == 0) {
            ToastUtils.showShort(getActivity(), "暂无数据");
        }
        if (worklists.size() == 0) {
            backLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void callClick(WorkListBeen.InfoBean entity, int currentIndex) {
        this.currentIndex = currentIndex;
        int status = Integer.parseInt(entity.getStatus());
        ++status;
        workListClickModel.getClickState(status, Integer.parseInt(entity.getId()));
    }

    //点击回调
    @Override
    public void clickCallback(int status) {
        if (status != 4) {
            worklists.get(currentIndex).setStatus(status + "");
        } else {
            worklists.remove(currentIndex);
        }
        workSheetAdapter.notifyDataSetChanged();

        if (worklists != null && worklists.size() == 0)
            backLayout.setVisibility(View.VISIBLE);
    }

    public void queryStatus(final int position, String id) {
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_DETAILS, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                BaseResponseBean bean;
                try {
                    bean = new Gson().fromJson(s, BaseResponseBean.class);
                    if (bean != null) {//解析未错
                        if (bean.code != 0) {
                            if (bean.code == 104) {
                                CommonUtils.tokenNullDeal(getActivity());
                            } else if (bean.code == 1001) {
                                Toast.makeText(getActivity(), bean.message, Toast.LENGTH_SHORT).show();
                                workSheetAdapter.remove(position);
                            }
                        } else {
                            Intent intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
                            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, worklists.get(position).getId());
                            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAIL_LAT, worklists.get(position).getLat());
                            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAIL_LNG, worklists.get(position).getLng());
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(context, "返回数据解析出错:" + s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                CommonUtils.fastShowError(getActivity(), volleyError);
            }
        });
    }
}
