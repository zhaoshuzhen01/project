package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.adapter.SelectAddressAdapter;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.databinding.ActivitySelectaddressBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class SelectAddressActivity extends BaseActivity {
    private ActivitySelectaddressBinding binding;
    private SelectAddressAdapter mAdapter;

//    private TextureMapView mMapView;
//    private BaiduMap mBaiduMap;
//    mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
//    mBaiduMap = mMapView.getMap();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selectaddress);

        mAdapter = new SelectAddressAdapter(SelectAddressActivity.this);
        binding.lvAddress.setAdapter(mAdapter);
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

}
