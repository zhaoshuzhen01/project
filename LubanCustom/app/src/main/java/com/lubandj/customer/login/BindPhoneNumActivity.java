package com.lubandj.customer.login;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.lubandj.master.activity.MainCantainActivity;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.LoginAppBean;
import com.lubandj.master.httpbean.SendSmsBean;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.SPUtils;
import com.lubandj.master.utils.TaskEngine;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BindPhoneNumActivity extends TitleBaseActivity implements EditTextWithDel.EditTextContentListener{

    @InjectView(R.id.et_phone_num)
    EditTextWithDel etPhoneNum;
    @InjectView(R.id.et_auth_code)
    EditTextWithDel etAuthCode;
    @InjectView(R.id.btn_send_code)
    Button btnSendCode;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.ll_agreement)
    LinearLayout llAgreement;
    private int COUNT_DOWN_TIME = 60;
    private String mPhoneNum;
    private String mAuthCode;
    private String openid ;


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
        return R.layout.activity_bind_phone_num;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setBackImg(R.drawable.ic_login_close);
        setBackImgVisiable(View.VISIBLE);
        setTitleText(R.string.txt_bind_phone_num);
        setOkVisibity(false);
        setListener();
        initData();
        btnSendCode.setEnabled(false);
        mPhoneNum = SPUtils.getInstance().getString(Canstance.KEY_SP_PHONE_NUM);
        if (!TextUtils.isEmpty(mPhoneNum)) {
            etPhoneNum.setText(mPhoneNum);
        }
        openid= getIntent().getStringExtra("openid");
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

    @OnClick({R.id.btn_send_code, R.id.btn_login, R.id.ll_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                if (checkPhoneNum()) return;
                SendSmsBean bean = new SendSmsBean(mPhoneNum, TextUtils.isEmpty(openid)?SendSmsBean.KEY_BINGING_PHONE_TEMPLATEID:SendSmsBean.KEY_LOGIN_TEMPLATEID);
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
                                ToastUtils.showShort(BindPhoneNumActivity.this, baseEntity.getMessage());
                            } else if (baseEntity.getCode() == 104) {
                                CommonUtils.tokenNullDeal(BindPhoneNumActivity.this);
                            } else {
                                ToastUtils.showShort(BindPhoneNumActivity.this, baseEntity.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        CommonUtils.fastShowError(BindPhoneNumActivity.this, volleyError);
                    }
                });
                break;
            case R.id.btn_login:
                if (checkPhoneNum()) return;
                initProgressDialog(R.string.txt_is_login).show();
                int url = TextUtils.isEmpty(openid)?Canstance.HTTP_MODIFYPHONE:Canstance.Http_WEIXIN_BINDING;
                LoginAppBean loginAppBean = new LoginAppBean(mPhoneNum,mAuthCode);
                if (!TextUtils.isEmpty(openid))
                    loginAppBean.openid = openid;
                TaskEngine.getInstance().commonHttps(url, loginAppBean, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        UserInfoResponse response = new UserInfoResponse();
                        response = (UserInfoResponse) CommonUtils.generateEntityByGson(BindPhoneNumActivity.this, s, response);
                        if (response != null) {
                            CommonUtils.setUid(response.info.uid);
                            CommonUtils.setToken(response.info.token);
                            TApplication.context.mUserInfo = response.info;
                            TApplication.context.setGetuiTag(response.info.uid);
                            startActivity(MainCantainActivity.class, null);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        CommonUtils.fastShowError(BindPhoneNumActivity.this, volleyError);
                    }
                });
                break;
            case R.id.ll_agreement:
                break;
        }
    }

    private boolean checkPhoneNum() {
        if (TextUtils.isEmpty(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_not_empty);
            return true;
        }

        if (!RegexUtils.isMobileExact(mPhoneNum)) {
            ToastUtils.showShort(this, R.string.txt_phone_num_is_error);
            return true;
        }
        return false;
    }

    @Override
    public void afterTextChanged(EditTextWithDel view, String content) {
        switch (view.getId()) {
            case R.id.et_phone_num:
                this.mPhoneNum = content;
                btnSendCode.setEnabled(!TextUtils.isEmpty(content));
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
            ActUtils.getInstance().exitApp(BindPhoneNumActivity.this);
        }
    }
}
