package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.BookOrderBeen;
import com.lubandj.master.httpbean.NetOrderBeen;
import com.lubandj.master.utils.TaskEngine;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class PayModel {
    private Context context ;
    private DataCall dataCall ;
    public PayModel(Context context,DataCall dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void bookOrder(String order_id,String payType) {
        NetOrderBeen netOrderBeen = new NetOrderBeen();
        netOrderBeen.order_id = order_id;
        netOrderBeen.payType = payType ;
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_PAY_ORDER,netOrderBeen, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                BookOrderBeen msgCenterBeen = new Gson().fromJson(s, BookOrderBeen.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        dataCall.getServiceData(msgCenterBeen);
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

