package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.ServiceDetailBeen;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.NetAddCar;
import com.lubandj.master.httpbean.NetServiceDetail;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class AddCarModel {
    private Context context ;
    private DataCall dataCall ;
    public AddCarModel(Context context,DataCall dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void addCar(String service_type,String service_id,String spec_id,String service_name,String price,String num) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_ADD_CAR, new NetAddCar(service_type,service_id,spec_id,service_name,price,num), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                BaseEntity msgCenterBeen = new Gson().fromJson(s, BaseEntity.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        dataCall.getServiceData(msgCenterBeen);
                    }else if (msgCenterBeen.getCode()==104){
                        CommonUtils.tokenNullDeal(context);
                    }else if (msgCenterBeen.getCode()==103){
//                        ibaseModel.getDataLists(null);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(context.getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(context, format);
                    }
                }
            }
        });
    }

}