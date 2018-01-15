package com.lubandj.master.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.databinding.ActivitySplashBinding;
import com.lubandj.master.httpbean.UserInfoRequest;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.StatusBarUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-18
 * company:九州宏图
 */

public class SplashActivity extends Activity {
    private ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        StatusBarUtils.setWindowStatusBarColor(SplashActivity.this, R.color.splash_status_bar);
        ActUtils.isFirstIn = true;//设置为入口正常进入
        if (TextUtils.isEmpty(CommonUtils.getToken()))//无登录信息
            mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 500);
        else
            mHandler.sendMessageDelayed(mHandler.obtainMessage(1), 1000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mBinding.ivSplash.setImageBitmap(null);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    onTokenLogin();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTokenLogin() {
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETINFO, new UserInfoRequest(CommonUtils.getUid()), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                UserInfoResponse response = new UserInfoResponse();
                response = (UserInfoResponse) CommonUtils.generateEntityByGson(SplashActivity.this, s, response);
                if (response != null) {
                    TApplication.context.mUserInfo = response.info;
                    TApplication.context.setGetuiTag(response.info.uid);
                    Intent intent = new Intent(SplashActivity.this, WorkSheetListActivity.class);
                    startActivity(intent);
//                    mBinding.ivSplash.setImageBitmap(null);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CommonUtils.fastShowError(SplashActivity.this, volleyError);
            }
        });
    }
}
