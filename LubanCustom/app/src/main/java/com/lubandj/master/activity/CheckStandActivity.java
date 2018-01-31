package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.master.R;
import com.lubandj.master.pay.Pay;
import com.lubandj.master.pay.PayHelper;
import com.lubandj.master.pay.PayResultCallbackImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CheckStandActivity extends TitleBaseActivity implements CompoundButton.OnCheckedChangeListener {
    @InjectView(R.id.top_img)
    ImageView topImg;
    @InjectView(R.id.top_lay)
    RelativeLayout topLay;
    @InjectView(R.id.weixinchekcout)
    CheckBox weixinchekcout;
    @InjectView(R.id.zfbcheckout)
    CheckBox zfbcheckout;
    private LinearLayout sure_pay;
    public Pay pay;
    /**
     * 充值结果,0为未充值状态;1为成功;2为失败
     */
    public static int mRechargeResult = 0;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CheckStandActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_check_stand;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleColor(getResources().getColor(R.color.weixin));
        setTitleText("收银台");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        sure_pay = findView(R.id.sure_pay);
        sure_pay.setOnClickListener(this);
        weixinchekcout.setChecked(true);
        weixinchekcout.setOnCheckedChangeListener(this);
        zfbcheckout.setOnCheckedChangeListener(this);
        window.setStatusBarColor(getResources().getColor(R.color.weixin));
        pay = new Pay(this, new PayResultCallbackImpl() {
            @Override
            public void onPaySuccess(String result, String payType) {

            }

            @Override
            public void onPayFail(String result, String payType) {
                //ToastUtils.showShort(result);
                //支付失败弹窗
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure_pay:
                toast(this, "确认支付");
                if (weixinchekcout.isChecked()) {
                    pay.pay(PayHelper.WXPAY, "10");
                } else {
                    pay.pay(PayHelper.ALIPAY, "10");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        switch (compoundButton.getId()) {
            case R.id.zfbcheckout:
                if (check)
                    weixinchekcout.setChecked(false);
                setTitleColor(getResources().getColor(R.color.zfb));
                topLay.setBackgroundColor(getResources().getColor(R.color.zfb));
                topImg.setImageResource(R.drawable.zfbback);
                sure_pay.setBackgroundResource(R.drawable.zfblay);
                window.setStatusBarColor(getResources().getColor(R.color.zfb));
                break;
            case R.id.weixinchekcout:
                if (check)
                    zfbcheckout.setChecked(false);
                setTitleColor(getResources().getColor(R.color.weixin));
                topLay.setBackgroundColor(getResources().getColor(R.color.weixin));
                topImg.setImageResource(R.drawable.weixinbac);
                sure_pay.setBackgroundResource(R.drawable.fulayback);
                window.setStatusBarColor(getResources().getColor(R.color.weixin));
                break;
        }
    }
}