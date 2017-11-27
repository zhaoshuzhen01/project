package com.lubandj.master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.master.worksheet.WorkSheetListActivity;

public class MainActivity extends BaseActivity {

    private TextView mainText ;
    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainText = findView(R.id.mainText);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case com.example.baselibrary.R.id.tv_basetitle_ok:
                startActivity(WorkSheetListActivity.class,null);
                break;
        }

    }
}
