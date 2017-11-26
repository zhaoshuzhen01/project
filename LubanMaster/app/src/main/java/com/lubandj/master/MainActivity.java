package com.lubandj.master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.TitleBaseActivity;

public class MainActivity extends TitleBaseActivity {

    private TextView mainText ;
    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainText = findView(R.id.mainText);
        setTitleText("主页");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
