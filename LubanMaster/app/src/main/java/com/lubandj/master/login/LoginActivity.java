package com.lubandj.master.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.example.baselibrary.util.RegexUtils;
import com.example.baselibrary.widget.EditTextWithDel;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.LoginAppBean;
import com.lubandj.master.httpbean.SendSmsBean;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.master.my.AboutLuBanActivity;
import com.lubandj.master.my.AgreementActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.SPUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends TitleBaseActivity implements EditTextWithDel.EditTextContentListener {


    @InjectView(R.id.et_phone_num)
    EditTextWithDel etPhoneNum;
    @InjectView(R.id.et_auth_code)
    EditTextWithDel etAuthCode;
    @InjectView(R.id.btn_send_code)
    Button btnSendCode;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    private int COUNT_DOWN_TIME = 60;
    private String mPhoneNum;
    private String mAuthCode;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (COUNT_DOWN_TIME > 0) {
                        btnSendCode.setText(String.format(getString(R.string.txt_login_page_send_code_again1), COUNT_DOWN_TIME));
                        COUNT_DOWN_TIME = COUNT_DOWN_TIME - 1;
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        initAuthCodeBtn();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void initAuthCodeBtn() {
        btnSendCode.setText(getString(R.string.txt_login_page_send_code_again));
        btnSendCode.setEnabled(true);
        COUNT_DOWN_TIME = 60;
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setBackImg(R.drawable.ic_login_close);
        setBackImgVisiable(View.GONE);
        setTitleText(R.string.txt_login_page_title);
        setOkVisibity(false);
        setListener();
        initData();

        mPhoneNum = SPUtils.getInstance().getString(Canstance.KEY_SP_PHONE_NUM);
        if (!TextUtils.isEmpty(mPhoneNum)) {
            etPhoneNum.setText(mPhoneNum);
        }

        if (!CommonUtils.isHasRequestUpgrade)
            CommonUtils.upgradeApp(LoginActivity.this, null);
    }

    private void setListener() {
        etPhoneNum.setEditTextContentListener(this);
        etAuthCode.setEditTextContentListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.btn_send_code, R.id.btn_login})
    public void onViewClicked(View view) {
        if (TextUtils.isEmpty(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_not_empty);
            return;
        }

        if (!RegexUtils.isMobileExact(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_error);
            return;
        }
        switch (view.getId()) {
            case R.id.btn_send_code:
                SendSmsBean bean = new SendSmsBean(mPhoneNum, SendSmsBean.KEY_LOGIN_TEMPLATEID);
                initProgressDialog(R.string.txt_is_sending_auth_code).show();
                TaskEngine.getInstance().commonHttps(Canstance.HTTP_SEND_CODE, bean, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                        if (baseEntity != null) {
                            if (baseEntity.getCode() == 0) {
                                btnSendCode.setEnabled(false);
                                mHandler.sendEmptyMessage(0);
                                ToastUtils.showShort(LoginActivity.this, baseEntity.getMessage());
                            } else if (baseEntity.getCode() == 104) {
                                CommonUtils.tokenNullDeal(LoginActivity.this);
                            } else {
                                ToastUtils.showShort(LoginActivity.this, baseEntity.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        CommonUtils.fastShowError(LoginActivity.this, volleyError);
                    }
                });
                break;
            case R.id.btn_login:
                initProgressDialog(R.string.txt_is_login).show();
                TaskEngine.getInstance().commonHttps(Canstance.HTTP_LOGIN, new LoginAppBean(mPhoneNum, mAuthCode), new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        UserInfoResponse response = new UserInfoResponse();
                        response = (UserInfoResponse) CommonUtils.generateEntityByGson(LoginActivity.this, s, response);
                        if (response != null) {
                            CommonUtils.setUid(response.info.uid);
                            CommonUtils.setToken(response.info.token);
                            TApplication.context.mUserInfo = response.info;
                            TApplication.context.setGetuiTag(response.info.uid);
                            startActivity(WorkSheetListActivity.class, null);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        CommonUtils.fastShowError(LoginActivity.this, volleyError);
                    }
                });
                break;
        }
    }

    @Override
    public void afterTextChanged(EditTextWithDel view, String content) {
        switch (view.getId()) {
            case R.id.et_phone_num:
                this.mPhoneNum = content;
                break;
            case R.id.et_auth_code:
                this.mAuthCode = content;
                break;
            default:
                break;
        }
        btnLogin.setEnabled(!TextUtils.isEmpty(mPhoneNum) && !TextUtils.isEmpty(mAuthCode));
    }


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            ActUtils.getInstance().exitApp(LoginActivity.this);
        }
    }

    public void onUserAggrement(View view) {
        Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
        String url = "https://wx.lubandj.com/h5/protocol/bbcr/";
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
