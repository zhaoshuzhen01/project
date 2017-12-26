package com.lubandj.master.my;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.GetAddressReponse;
import com.lubandj.master.httpbean.UidParamsRequest;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionRequests(Manifest.permission.CAMERA, new OnBooleanListener() {
                @Override
                public void onClick(boolean bln) {
                    if (bln) {
//                    ToastUtils.showShort(MyAddressActivity.this, "权限通过");
                        Intent intent = new Intent(MyAddressActivity.this, SelectAddressActivity.class);
                        startActivityForResult(intent, 1010);
                    } else {
//                        ToastUtils.showShort(MyAddressActivity.this, "权限拒绝");
                        Intent intent = new Intent(MyAddressActivity.this, SelectAddressActivity.class);
                        startActivityForResult(intent, 1010);
                    }
                }
            });
        } else {
            Intent intent = new Intent(MyAddressActivity.this, SelectAddressActivity.class);
            startActivityForResult(intent, 1010);
        }
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
        UidParamsRequest request = new UidParamsRequest(CommonUtils.getUid());
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETADDRESS, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                GetAddressReponse reponse = new GetAddressReponse();
                reponse = (GetAddressReponse) CommonUtils.generateEntityByGson(MyAddressActivity.this, s, reponse);
                if (reponse != null) {
                    mBean = reponse.info;
                    setAddress();
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

    public void setAddress() {
        StringBuilder sb = new StringBuilder("");
        if (mBean.province != null && mBean.city != null) {
            if (mBean.province.equals(mBean.city)) {
                sb.append(mBean.province);
            } else {
                sb.append(mBean.province + mBean.city);
            }
        }
        if (mBean.areapublic != null) {
            sb.append(mBean.areapublic);
        }
        if (mBean.address != null) {
            sb.append(mBean.address);
        }
//        if (mBean.housing_estate != null) {
//            sb.append(mBean.housing_estate);
//        }
        binding.tvAddressArea.setText(sb.toString());
        if (mBean.house_number != null)
            binding.tvAddressHousenum.setText(mBean.house_number);
    }

    /**
     * 保存地址
     *
     * @param view
     */
    public void onAddressSave(View view) {
        if (mBean == null) {
            ToastUtils.showShort(MyAddressActivity.this, "还未选择地址");
            return;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1010) {
            mBean = (AddressBean) data.getSerializableExtra("address");
            setAddress();
        }
    }
}