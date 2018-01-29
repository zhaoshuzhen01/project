package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CheckStandActivity extends TitleBaseActivity implements CompoundButton.OnCheckedChangeListener{
    @InjectView(R.id.top_img)
    ImageView topImg;
    @InjectView(R.id.top_lay)
    RelativeLayout topLay;
    @InjectView(R.id.weixinchekcout)
    CheckBox weixinchekcout;
    @InjectView(R.id.zfbcheckout)
    CheckBox zfbcheckout;
    private LinearLayout sure_pay;

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
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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
        switch (compoundButton.getId()){
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