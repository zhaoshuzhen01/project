package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.activity.BookOrderActivity;
import com.lubandj.master.adapter.IntroduceAdapter;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ServiceDetailBeen;
import com.lubandj.master.customview.CarView;
import com.lubandj.master.dialog.IntroduceDialog;
import com.lubandj.master.model.ServiceDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceFragment extends BaseFragment implements DataCall<ServiceDetailBeen> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.button_text)
    TextView buttonText;
    @InjectView(R.id.main_car_lay)
    RelativeLayout main_car_lay;
    @InjectView(R.id.main_car)
    ImageView main_car ;
    @InjectView(R.id.tv_settlement)
    TextView tv_settlement;
    @InjectView(R.id.detail_top_pic)
    ImageView topPic ;
    @InjectView(R.id.top_name)
    TextView topName ;
    @InjectView(R.id.car_msgCount)
    TextView car_msgCount ;
    @InjectView(R.id.cus_carlay)
    CarView carLayout ;
    private RelativeLayout carView ;
    private List<ServiceDetailBeen.InfoBean.ItemsBean> msgBeens = new ArrayList<>();
    private IntroduceAdapter introduceAdapter;
    protected boolean isVisible = false;
    private IntroduceDialog introduceDialog;
    private String service_id ;
    private ServiceDetailModel serviceDetailModel ;
    public static IntroduceFragment newInstance(String index) {
        IntroduceFragment myFragment = new IntroduceFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("serviceId", index);
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
        carView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });
        introduceAdapter = new IntroduceAdapter(msgBeens, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(introduceAdapter);
        introduceDialog = new IntroduceDialog();
        service_id = (String) getArguments().get("serviceId");
        serviceDetailModel = new ServiceDetailModel(getActivity(),this);
        car_msgCount.setVisibility(View.GONE);
        carLayout.setCar_msgCount(carView,car_msgCount,main_car);
        main_car.setTag(R.drawable.car);
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
       serviceDetailModel.getData(service_id);
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


    @OnClick({R.id.button_text,R.id.main_car_lay,R.id.tv_settlement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_text:
                introduceDialog.clickShow(getChildFragmentManager(),"",getActivity());
                break;
            case R.id.main_car_lay:
                if (((int)main_car.getTag())==R.drawable.nocar){
                    ToastUtils.showShort(getActivity(),"请选择服务");
                    return;
                }
                if (carView.getVisibility()==View.VISIBLE){
                    carView.setVisibility(View.GONE);
                }else {
                    carView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_settlement:
                BookOrderActivity.startActivity(getActivity());
                break;
        }
    }
    @Override
    public void getServiceData(ServiceDetailBeen data) {
        introduceDialog.setData(data,main_car);
        Glide.with(getActivity()).load(data.getInfo().getService_pic()).skipMemoryCache(false).into(topPic);
        topName.setText(data.getInfo().getName());
            msgBeens.addAll(data.getInfo().getItems());
        introduceAdapter.notifyDataSetChanged();

    }
}