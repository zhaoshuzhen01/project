package com.example.baselibrary.widget;

import android.os.Bundle;

import com.example.baselibrary.BaseActivity;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public abstract class NotitleBaseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(getLayout());
        initView();
    }
}
