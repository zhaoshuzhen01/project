package com.lubandj.master.fragment.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.NetClickStateBeen;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class WorkListClickModel {
    private Context context;
private ClickCallBack clickCallBack;
    public WorkListClickModel(Context context,ClickCallBack clickCallBack) {
        this.context = context;
        this.clickCallBack = clickCallBack;
    }

    public void getClickState(final int status, int id) {
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_UPDATE_STATUS, new NetClickStateBeen(status, id), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                if (baseEntity != null) {
                    if (baseEntity.getCode() == 0) {
                      clickCallBack.clickCallback(status);
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

    public interface ClickCallBack{
        void clickCallback(int status);
    }
}
