package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.BaseFragment;
import com.lubandj.master.R;
import com.lubandj.master.adapter.IntroduceAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.customview.CarView;
import com.lubandj.master.dialog.IntroduceDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceFragment extends BaseFragment{
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.button_text)
    TextView buttonText;
    @InjectView(R.id.main_car_lay)
    RelativeLayout main_car_lay;
    private RelativeLayout carView ;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private IntroduceAdapter introduceAdapter;
    protected boolean isVisible = false;
    private IntroduceDialog introduceDialog;
    public static IntroduceFragment newInstance(int index) {
        IntroduceFragment myFragment = new IntroduceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragmnet_introduce;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.inject(this, view);
        carView = view.findViewById(R.id.carview);
        introduceAdapter = new IntroduceAdapter(msgBeens, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(introduceAdapter);
        introduceDialog = new IntroduceDialog();
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
            initData();
        }
    }

    @Override
    protected void initData() {
        isFirst = false;
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        introduceAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.button_text,R.id.main_car_lay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_text:
                introduceDialog.show(getChildFragmentManager(),"");
                break;
            case R.id.main_car_lay:
                if (carView.getVisibility()==View.VISIBLE){
                    carView.setVisibility(View.GONE);
                }else {
                    carView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}