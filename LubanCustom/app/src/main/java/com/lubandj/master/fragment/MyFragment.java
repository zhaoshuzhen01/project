package com.lubandj.master.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.activity.MainCantainActivity;
import com.lubandj.master.httpbean.UserInfoRequest;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/31.
 */

public class MyFragment extends BaseFragment {

    @InjectView(R.id.headicon)
    ImageView headicon;
    @InjectView(R.id.headtext)
    TextView headtext;
    @InjectView(R.id.my_address)
    LinearLayout myAddress;
    @InjectView(R.id.my_youhuiquan)
    LinearLayout myYouhuiquan;
    @InjectView(R.id.my_oingjia)
    LinearLayout myOingjia;
    @InjectView(R.id.my_kefu)
    RelativeLayout myKefu;
    @InjectView(R.id.my_fankui)
    RelativeLayout myFankui;
    @InjectView(R.id.my_guanyu)
    RelativeLayout myGuanyu;
    @InjectView(R.id.my_share)
    RelativeLayout myShare;
    protected  boolean isVisible = false;

    public static MyFragment newInstance(int index) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.inject(this, view);

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }
    @Override
    protected void initData() {
        isFirst = false ;
        onTokenLogin();
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
        if (isVisible && isFirst&& NetworkUtils.isNetworkAvailable(getActivity())&&CommonUtils.isLogin()) {
            initData();
        }
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

    @OnClick({R.id.headicon, R.id.my_address, R.id.my_youhuiquan, R.id.my_oingjia, R.id.my_kefu, R.id.my_fankui, R.id.my_guanyu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headicon:
                if (CommonUtils.isLogin()){
                }else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }
                break;
            case R.id.my_address:
                break;
            case R.id.my_youhuiquan:
                break;
            case R.id.my_oingjia:
                break;
            case R.id.my_kefu:
                break;
            case R.id.my_fankui:
                break;
            case R.id.my_guanyu:
                break;
        }
    }

    /**
     * 获取用户信息
     */
    public void onTokenLogin() {
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETINFO, new UserInfoRequest(CommonUtils.getUid()), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                UserInfoResponse response = new UserInfoResponse();
                response = (UserInfoResponse) CommonUtils.generateEntityByGson(getActivity(), s, response);
                if (response != null) {
                    TApplication.context.mUserInfo = response.info;
                    TApplication.context.setGetuiTag(response.info.uid);
                  headtext.setText(response.info.mobile);
                  if (!TextUtils.isEmpty(response.info.face_url)){
                      Glide.with(getActivity()).load(response.info.face_url).skipMemoryCache(false).into(headicon);

                  }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }
}
