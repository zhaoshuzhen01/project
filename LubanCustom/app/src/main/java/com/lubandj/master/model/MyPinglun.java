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
import com.lubandj.master.been.MyPingjiaBeen;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class MyPinglun extends BaseModel{
    private Context context ;
    private DataCall dataCall ;
    public MyPinglun(Context context){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void getReflushData(int type,int startIndex,int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_MYPINGJIA_LIST,new NetWorkListBeen(type, startIndex,pageSize), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                MyPingjiaBeen msgCenterBeen = new Gson().fromJson(s, MyPingjiaBeen.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        ibaseModel.getDataLists(msgCenterBeen.getInfo());
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
