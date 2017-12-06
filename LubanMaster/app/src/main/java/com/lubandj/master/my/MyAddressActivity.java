package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.databinding.ActivityWorkcodeBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class MyAddressActivity extends BaseActivity {
    private ActivityMyaddressBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myaddress);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 点击选择地址
     *
     * @param view
     */
    public void onSelectAddress(View view) {
        startActivity(SelectAddressActivity.class, null);
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }
}
