package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.HomeBeen;
import com.lubandj.master.been.ServiceDetailBeen;
import com.lubandj.master.httpbean.NetHomeBeen;
import com.lubandj.master.httpbean.NetServiceDetail;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class ServiceDetailModel  {
    private String city ;
    private Context context ;
    private DataCall dataCall ;
    public ServiceDetailModel(Context context,DataCall dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void getData(String id) {

        TaskEngine.getInstance().commonHttps(Canstance.HTTP_SERVICE_DETAILS, new NetServiceDetail(Canstance.CITY,id), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                ServiceDetailBeen msgCenterBeen = new Gson().fromJson(s, ServiceDetailBeen.class);
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