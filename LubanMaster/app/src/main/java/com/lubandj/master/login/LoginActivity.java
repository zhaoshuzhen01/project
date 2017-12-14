package com.lubandj.master.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.EditTextWithDel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.igexin.sdk.PushManager;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.SendSmsBean;
import com.lubandj.master.model.LoginModel;
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
                        btnSendCode.setText(getString(R.string.txt_login_page_send_code_again));
                        btnSendCode.setEnabled(true);
                        COUNT_DOWN_TIME = 60;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        // REFACTOR: 2017/12/7 待重构 页面背景颜色
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setBackImg(R.drawable.ic_login_close);
        setTitleText(R.string.txt_login_page_title);
        setOkVisibity(false);
        setListener();
        initData();
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
        switch (view.getId()) {
            case R.id.btn_send_code:
                btnSendCode.setEnabled(false);
                mHandler.sendEmptyMessage(0);
                break;
            case R.id.btn_login:
//                LoginModel loginModel = new LoginModel();
//                loginModel.getLogin(this);

                SendSmsBean bean = new SendSmsBean("18813003698", "802");
                initProgressDialog(LoginActivity.this, "正在发送验证码...");
                dialog.show();
                TaskEngine.getInstance().commonHttps(Canstance.HTTP_SEND_CODE, bean, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        ToastUtils.showShort(LoginActivity.this, s);
                        startActivity(WorkSheetListActivity.class, null);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        if (volleyError != null) {
                            if (volleyError.networkResponse != null)
                                ToastUtils.showShort(LoginActivity.this, "网络连接错误（" + volleyError.networkResponse.statusCode + ")");
                            Log.e("TAG", volleyError.getMessage(), volleyError);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void afterTextChanged(EditTextWithDel view, String content) {
        switch (view.getId()) {
            case R.id.et_phone_num:
                break;
            case R.id.et_auth_code:
                break;
            default:
                break;
        }
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
}
