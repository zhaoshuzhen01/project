package com.lubandj.master.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.customer.my.FeedBackInfoActivity;
import com.lubandj.customer.my.MySettingActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.customview.RoundImageView;
import com.lubandj.master.dialog.DoubleSelectDialog;
import com.lubandj.master.httpbean.UserInfoRequest;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.customer.my.AboutLuBanActivity;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/31.
 */

public class MyFragment extends BaseFragment implements DialogTagin.DialogSure {

    @InjectView(R.id.headicon)
    RoundImageView headicon;
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
    @InjectView(R.id.tv_selfinfo)
    TextView myInfo;
    protected boolean isVisible = false;
    private ImageLoader imageLoader;

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
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());
        refreshInfo();
    }

    public void refreshInfo() {
        if (TApplication.context.mUserInfo == null) {//未登录
            myInfo.setVisibility(View.GONE);
        } else {//已登录
            if (TextUtils.isEmpty(TApplication.context.mUserInfo.nickname)) {
                headtext.setText(TApplication.context.mUserInfo.mobile);
            } else {
                headtext.setText(TApplication.context.mUserInfo.nickname);
            }
            myInfo.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(TApplication.context.mUserInfo.face_url)) {
                loadFace();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {
        isFirst = false;
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
        if (isVisible && isFirst && NetworkUtils.isNetworkAvailable(getActivity()) && CommonUtils.isLogin()) {
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

    @OnClick({R.id.headicon, R.id.my_address, R.id.my_youhuiquan, R.id.my_oingjia, R.id.my_kefu, R.id.my_fankui, R.id.my_guanyu, R.id.tv_selfinfo, R.id.my_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headicon:
                if (CommonUtils.isLogin()) {
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.my_address:
                break;
            case R.id.my_youhuiquan:
//                CouponsActivity.startActivity(getActivity());
                break;
            case R.id.my_oingjia:
                break;
            case R.id.my_kefu:
                DialogTagin.getDialogTagin(getActivity()).showDialog("即将拨打：" + "401-323434").setDialogSure(this);

                break;
            case R.id.my_fankui:
                startActivity(new Intent(getActivity(), FeedBackInfoActivity.class));
                break;
            case R.id.my_guanyu:
                Intent intent = new Intent(getActivity(), AboutLuBanActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_selfinfo://个人信息
                Intent intent2 = new Intent(getActivity(), MySettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.my_share://分享
                DoubleSelectDialog dialog = new DoubleSelectDialog(getActivity(), "微信好友", "微信朋友圈", new DoubleSelectDialog.DoubleClickListenerInterface() {
                    @Override
                    public void doFirstClick() {

                    }

                    @Override
                    public void doSecondClick() {

                    }
                });
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
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
                    refreshInfo();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }

    @Override
    public void dialogCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "401-323434"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loadFace() {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(headicon, R.drawable.unloginpic, R.drawable.unloginpic);
        if (!TextUtils.isEmpty(TApplication.context.mUserInfo.face_url))
            imageLoader.get(TApplication.context.mUserInfo.face_url, imageListener);
    }
}
