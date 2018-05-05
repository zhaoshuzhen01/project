package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.CityListBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.httpbean.NetStartBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class GetCoupModel {
    private Context context ;
    private DataCall dataCall ;
    public GetCoupModel(Context context,DataCall dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void getCoupous(String id) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETCOUSPS_LIST,id, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                CityListBeen msgCenterBeen = new Gson().fromJson(s, CityListBeen.class);
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


