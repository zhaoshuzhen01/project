package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.customview.BackLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CarActivity extends TitleBaseActivity {
    private BackLayout backLayout;
    @InjectView(R.id.car_contaner)
    LinearLayout workFragmentContaner;
    public  static  void startActivity(Context context){
        Intent intent = new Intent(context, CarActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_car;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText(R.string.car_center);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        backLayout = new BackLayout(this);
        backLayout.setOnclick(this);
        workFragmentContaner.addView(backLayout);
        backLayout.setOnclick(this);
        backLayout.setNodataText("购物车空空如也,快去逛逛吧");
        backLayout.setButtonText("进入首页");
        setRightText("编辑");

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_basetitle_right:
                break;
                default:
                    MainCantainActivity.startActivity(this);
                    break;
        }
    }
}
