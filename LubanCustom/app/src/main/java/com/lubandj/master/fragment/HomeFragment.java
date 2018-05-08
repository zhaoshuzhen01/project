package com.lubandj.master.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.activity.CarActivity;
import com.lubandj.master.activity.ServiceDetailActivity;
import com.lubandj.master.adapter.HomeListAdapter;
import com.example.baselibrary.HomeBeen;
import com.example.baselibrary.GuangGaoBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.customview.HomeTopView;
import com.lubandj.master.model.HomeModel;
import com.lubandj.master.model.workList.GuangGaoModel;
import com.lubandj.master.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class HomeFragment extends BaseRefreshFragment implements IbaseView<HomeBeen.InfoBean>, BaseQuickAdapter.OnItemClickListener, View.OnClickListener ,DataCall<GuangGaoBeen>{
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.car_msgCount)
    TextView car_msgCount;
    private HomeListAdapter homeListAdapter;
    private List<HomeBeen.InfoBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter;
    private HomeTopView homeTopView;
    protected RelativeLayout main_car_lay;
    private int mdex = 0;
    private HomeModel homeModel;
    private GuangGaoModel guangGaoModel;
    private List<GuangGaoBeen.InfoBean> guangGaolists;

    public static HomeFragment newInstance(int index) {
        HomeFragment myFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6) view.findViewById(R.id.prpt);
        pullToRefreshAndPushToLoadView.setCanLoadMore(false);
        homeListAdapter = new HomeListAdapter(msgBeens, getActivity());
        homeListAdapter.setOnItemClickListener(this);
        homeTopView = new HomeTopView(getActivity());
        homeListAdapter.addHeaderView(homeTopView);
//        homeTopView.initViewPager(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2); //spanCount为列数，默认方向vertical
        initRawRecyclerView(recyclerView, manager, homeListAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 20, 0));
        homeModel = new HomeModel(getActivity());
        guangGaoModel = new GuangGaoModel(getActivity(),this);
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(getActivity(), this, homeModel);
        Canstance.CITY = "北京市";
        homeModel.setCity(Canstance.CITY);
        guangGaoModel.getGuangGao();
        main_car_lay = view.findViewById(R.id.main_car_lay);
        main_car_lay.setOnClickListener(this);
        car_msgCount.setVisibility(View.GONE);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("deal", dy + "     dy");
                if ((mdex * dy) < 0 || mdex == 0) {
                    mdex = dy;
                    if (dy > 0)
                        carAnimal();
                    else
                        upAnimal();
                }
            }
        });
    }

    @Override
    protected void initData() {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            pullToRefreshAndPushToLoadView.finishRefreshing();
        } else {
            msgCenterPresenter.getReflushData(0);
        }
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isNetworkAvailable(getActivity())) {
            pullToRefreshAndPushToLoadView.finishLoading();
        } else {
            msgCenterPresenter.getMoreData(0);
        }
    }

    @Override
    public void getDataLists(List<HomeBeen.InfoBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        if (msgBeens.size() == 0 && datas == null) {
            return;
        }
        msgBeens.clear();
        //分类数据为热销和非热销
        List<HomeBeen.InfoBean> hotList = new ArrayList<>();
        List<HomeBeen.InfoBean> unhotList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getType() == 1) {
                hotList.add(datas.get(i));
            } else {
                unhotList.add(datas.get(i));
            }
        }
        homeTopView.initViewPager(getActivity(),datas);
        msgBeens.addAll(hotList);
        homeListAdapter.notifyDataSetChanged();
        if (msgBeens.size() > 0) {
            isFirst = false;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ServiceDetailActivity.startActivity(getActivity(), msgBeens.get(position).getService_id());
    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isLogin()) {
            CarActivity.startActivity(getActivity());
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        }
    }

    private void carAnimal() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(main_car_lay, "translationX", 0f, 120f);
        animator.setDuration(500);
        animator.start();
    }

    private void upAnimal() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(main_car_lay, "translationX", 120f, 0f);
        animator1.setDuration(500);

        animator1.start();
    }

    @Override
    public void getServiceData(GuangGaoBeen data) {
        guangGaolists = data.getInfo();
        homeTopView.initGuangGao(guangGaolists);
    }
}
