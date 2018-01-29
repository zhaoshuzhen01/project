package com.lubandj.master.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.baselibrary.BaseRefreshFragment;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.activity.CarActivity;
import com.lubandj.master.activity.ServiceDetailActivity;
import com.lubandj.master.adapter.HomeListAdapter;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.customview.HomeTopView;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class HomeFragment extends BaseRefreshFragment implements IbaseView<MsgCenterBeen.InfoBean.ListBean>, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HomeListAdapter homeListAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private BaseReflushPresenter msgCenterPresenter;
    private HomeTopView homeTopView;
    protected RelativeLayout main_car_lay;
    private boolean mY = false;

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

        homeListAdapter = new HomeListAdapter(msgBeens, getActivity());
        homeListAdapter.setOnItemClickListener(this);
        homeTopView = new HomeTopView(getActivity());
        homeListAdapter.addHeaderView(homeTopView);
        homeTopView.initViewPager(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2); //spanCount为列数，默认方向vertical
        initRawRecyclerView(recyclerView, manager, homeListAdapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0,0,20,0));
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(getActivity(), this, new MsgCenterModel(getActivity()));
        main_car_lay = view.findViewById(R.id.main_car_lay);
        main_car_lay.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("deal", dy + "     dy");
                if (!mY){
                    synchronized (this){
                       if (!mY){
                           mY = true;
                           carAnimal();
                       }
                    }

                }
            }
        });
    }

    @Override
    protected void initData() {
        isFirst = false;
        msgCenterPresenter.getReflushData(0);

    }

    @Override
    public void onRefresh() {
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onLoadMore() {
        msgCenterPresenter.getMoreData(0);
    }

    @Override
    public void getDataLists(List<MsgCenterBeen.InfoBean.ListBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        msgBeens.clear();
        msgBeens.addAll(datas);
        homeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ServiceDetailActivity.startActivity(getActivity());
    }

    @Override
    public void onClick(View view) {
        CarActivity.startActivity(getActivity());
    }

    private void carAnimal() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(main_car_lay, "translationX", 0f,150f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                    mY = false;
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(main_car_lay, "translationX", 150f,0f);
                animator1.setDuration(500);

                animator1.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(500);
        animator.start();
    }
}
