package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.HomeBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.httpbean.NetHomeBeen;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class HomeModel extends BaseModel {
    private String city ;
    public HomeModel(Context context){
        this.context = context ;
    }
    public void setCity(String city){
        this.city = city ;
    }
    @Override
    public void getReflushData(int type,int startIndex,int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_LIST, new NetHomeBeen(""), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                HomeBeen msgCenterBeen = new Gson().fromJson(s, HomeBeen.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        List<HomeBeen.InfoBean> datas = msgCenterBeen.getInfo();
                        if (datas!=null)
                            ibaseModel.getDataLists(datas);
                        if (datas.size()==0){
//                            ToastUtils.showShort(context,"暂无数据");
                        }
                    }else if (msgCenterBeen.getCode()==104){
                        CommonUtils.tokenNullDeal(context);
                    }else if (msgCenterBeen.getCode()==103){
                        ibaseModel.getDataLists(null);
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
