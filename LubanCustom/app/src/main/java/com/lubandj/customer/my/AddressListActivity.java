package com.lubandj.customer.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityAddresslistBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2018-2-22
 * company:九州宏图
 */

public class AddressListActivity extends BaseActivity {
    private ActivityAddresslistBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addresslist);
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
     * 新增地址
     *
     * @param view
     */
    public void onAddNewAddress(View view) {

    }
}
