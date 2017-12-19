package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.GetAddressReponse;
import com.lubandj.master.httpbean.GetAddressRequest;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class MyAddressActivity extends BaseActivity {
    private ActivityMyaddressBinding binding;
    private AddressBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_myaddress);
        getAddress();
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


    public void getAddress() {
        initProgressDialog("获取地址中...").show();
        GetAddressRequest request = new GetAddressRequest(0);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETADDRESS, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                GetAddressReponse reponse = new GetAddressReponse();
                reponse = (GetAddressReponse) CommonUtils.generateEntityByGson(MyAddressActivity.this, s, reponse);
                if (reponse != null) {
                    mBean = reponse.info;
                    binding.tvAddressArea.setText(mBean.address + mBean.housing_estate);
                    binding.tvAddressHousenum.setText(mBean.house_number);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(MyAddressActivity.this, format);
                    }
                    Logger.e(volleyError.getMessage());
                }
            }
        });
    }

    /**
     * 保存地址
     *
     * @param view
     */
    public void onAddressSave(View view) {
        initProgressDialog("保存地址中...").show();
        mBean.house_number = binding.tvAddressHousenum.getText().toString();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_SAVEADDRESS, mBean, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                BaseResponseBean bean = new BaseResponseBean();
                bean = CommonUtils.generateEntityByGson(MyAddressActivity.this, s, bean);
                if (bean != null) {
                    ToastUtils.showShort(MyAddressActivity.this, "保存成功");
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(MyAddressActivity.this, format);
                    }
                    Logger.e(volleyError.getMessage());
                }
            }
        });
    }
}