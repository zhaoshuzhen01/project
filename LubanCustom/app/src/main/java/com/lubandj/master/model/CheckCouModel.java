package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.Iview.Keyong;
import com.lubandj.master.R;
import com.lubandj.master.been.CityListBeen;
import com.lubandj.master.been.MyCons;
import com.lubandj.master.httpbean.HttpCheckCous;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class CheckCouModel {
    private Context context ;
    private Keyong dataCall ;
    public CheckCouModel(Context context,Keyong dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void getCheckCoun(String amount, String city, List<String>service_ids) {
        HttpCheckCous httpCheckCous = new HttpCheckCous(amount,city,service_ids);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_CHECKCOUSPS,httpCheckCous, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                MyCons msgCenterBeen = new Gson().fromJson(s, MyCons.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        dataCall.getKeData(msgCenterBeen);
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

