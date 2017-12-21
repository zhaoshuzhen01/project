package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
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

    private BaiduMap mBaiduMap;
    private SuggestionSearch mSuggestionSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selectaddress);

        mAdapter = new SelectAddressAdapter(SelectAddressActivity.this);
        binding.lvAddress.setAdapter(mAdapter);

        mBaiduMap = binding.mbaiduMap.getMap();

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
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


    @Override
    protected void onResume() {
        super.onResume();
        binding.mbaiduMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mbaiduMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
        binding.mbaiduMap.onDestroy();
    }

    public void onSearch(View view) {
        String place = binding.etPalceSelectaddress.getText().toString();
        String city = binding.tvCitySelectaddress.getText().toString();
        if (!TextUtils.isEmpty(place)) {
            mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(place).city(city).citylimit(true));
        } else {
            ToastUtils.showShort(SelectAddressActivity.this, "请先输入地址");
        }
    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                //未找到相关结果
                ToastUtils.showShort(SelectAddressActivity.this, "未找到相关的地址");
                mAdapter.clearData();
                return;
            }
            //获取在线建议检索结果
            mAdapter.setData(res.getAllSuggestions());
        }
    };


}