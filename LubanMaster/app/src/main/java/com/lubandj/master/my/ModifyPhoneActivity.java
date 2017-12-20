package com.lubandj.master.my;

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
import com.example.baselibrary.util.RegexUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.LoginBeen;
import com.lubandj.master.databinding.ActivityAboutlubanBinding;
import com.lubandj.master.databinding.ActivityModifyphoneBinding;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.LoginAppBean;
import com.lubandj.master.httpbean.ModifyPhoneRequest;
import com.lubandj.master.httpbean.SendSmsBean;
import com.lubandj.master.login.LoginActivity;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.SPUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import java.util.Timer;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class ModifyPhoneActivity extends BaseActivity {
    private ActivityModifyphoneBinding binding;
    private int COUNT_DOWN_TIME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modifyphone);
        binding.btnLogin.setEnabled(false);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }


    /**
     * 发送验证码
     *
     * @param view
     */
    public void onSendCode(View view) {
        String mPhoneNum = binding.etPhoneNum.getText().toString();
        if (TextUtils.isEmpty(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_not_empty);
            return;
        }

        if (!RegexUtils.isMobileExact(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_error);
            return;
        }
        SendSmsBean bean = new SendSmsBean(mPhoneNum, SendSmsBean.KEY_BINGING_PHONE_TEMPLATEID);
        initProgressDialog(R.string.txt_is_sending_auth_code).show();
        TaskEngine.getInstance().commonHttps(Canstance.HTTP_SEND_CODE, bean, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                BaseResponseBean response = new BaseResponseBean();
                response = CommonUtils.generateEntityByGson(ModifyPhoneActivity.this, s, response);
                if (response != null) {
                    ToastUtils.showShort(ModifyPhoneActivity.this, response.message);
                    COUNT_DOWN_TIME = 60;
                    binding.btnSendCode.setEnabled(false);
                    mHandler.sendEmptyMessage(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(ModifyPhoneActivity.this, format);

                    }
                    Logger.e(volleyError.getMessage());
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (COUNT_DOWN_TIME > 0) {
                        binding.btnSendCode.setText(String.format(getString(R.string.txt_login_page_send_code_again1), COUNT_DOWN_TIME));
                        COUNT_DOWN_TIME = COUNT_DOWN_TIME - 1;
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        binding.btnSendCode.setText("重新发送验证码");
                        binding.btnSendCode.setEnabled(true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 修改手机号
     *
     * @param view
     */
    public void onModifyPhone(View view) {
        initProgressDialog("正在保存修改...").show();
        final String mPhoneNum = binding.etPhoneNum.getText().toString();
        String code = binding.etAuthCode.getText().toString();
        ModifyPhoneRequest request = new ModifyPhoneRequest();
        request.mobile = mPhoneNum;
        request.verifyCode = code;
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_MODIFYPHONE, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                BaseResponseBean response = new BaseResponseBean();
                response = CommonUtils.generateEntityByGson(ModifyPhoneActivity.this, s, response);
                if (response != null) {
                    ToastUtils.showShort(ModifyPhoneActivity.this, response.message);
                    TApplication.context.mUserInfo.mobile = mPhoneNum;
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(ModifyPhoneActivity.this, format);
                    }
                    Logger.e(volleyError.getMessage());
                }
            }
        });
    }
}
