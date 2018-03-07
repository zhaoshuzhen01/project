package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.R;
import com.lubandj.master.been.BookOrderBeen;
import com.lubandj.master.been.WeiXinPayInfo;
import com.lubandj.master.model.PayModel;
import com.lubandj.master.pay.Pay;
import com.lubandj.master.pay.PayHelper;
import com.lubandj.master.pay.PayResultCallbackImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CheckStandActivity extends TitleBaseActivity implements CompoundButton.OnCheckedChangeListener,DataCall {
    @InjectView(R.id.top_img)
    ImageView topImg;
    @InjectView(R.id.top_lay)
    RelativeLayout topLay;
    @InjectView(R.id.weixinchekcout)
    CheckBox weixinchekcout;
    @InjectView(R.id.zfbcheckout)
    CheckBox zfbcheckout;
    @InjectView(R.id.text1)
    TextView text1 ;
    @InjectView(R.id.xiadanprice)
    TextView xiandanprice ;
    @InjectView(R.id.text4)
    TextView text4 ;
    private MyCountDownTimer timer;
    private final long TIME = 15*60 * 1000L;
    private final long INTERVAL = 1000L;
    private LinearLayout sure_pay;
    public Pay pay;
    private BookOrderBeen bookOrderBeen ;
    private PayModel payModel ;
    /**
     * 充值结果,0为未充值状态;1为成功;2为失败
     */
    public static int mRechargeResult = 0;
    public static void startActivity(Context context,BookOrderBeen data) {
        Intent intent = new Intent(context, CheckStandActivity.class);
        intent.putExtra("data",data);
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
        text1.setText("¥" +LocalleCarData.newInstance().getTotalPrice());
        xiandanprice.setText("¥" +LocalleCarData.newInstance().getTotalPrice());
        payModel = new PayModel(this,this);
        startTimer();
        bookOrderBeen = (BookOrderBeen) getIntent().getSerializableExtra("data");
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
                if (weixinchekcout.isChecked()) {
                    payModel.bookOrder(bookOrderBeen.getInfo().getOrder_id(),"1");
                } else {
                    payModel.bookOrder(bookOrderBeen.getInfo().getOrder_id(),"2");
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

    @Override
    public void getServiceData(Object data) {
        if (weixinchekcout.isChecked()) {
//            pay.payOrder(PayHelper.WXPAY, data);
            pay.pay(PayHelper.WXPAY, "");
        } else {
            pay.pay(PayHelper.ALIPAY, "10");
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long time = millisUntilFinished / 1000;

            if (time <= 59) {
                text4.setText(String.format("00:%02d", time));
            } else {
                text4.setText(String.format("%02d:%02d", time / 60, time % 60));
            }
        }

        @Override
        public void onFinish() {
            text4.setText("  00:00");
            cancelTimer();
            ToastUtils.showShort(CheckStandActivity.this,"支付超时");
            finish();
        }
    }
    /**
     * 开始倒计时
     */
    private void startTimer() {
        if (timer == null) {
            timer = new MyCountDownTimer(TIME, INTERVAL);
        }
        timer.start();
    }

    /**
     * 取消倒计时
     */
    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}