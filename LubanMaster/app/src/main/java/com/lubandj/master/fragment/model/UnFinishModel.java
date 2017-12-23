package com.lubandj.master.fragment.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class UnFinishModel extends BaseModel{
private IWorkModel iWorkModel ;

public UnFinishModel(Context context,IWorkModel iWorkModel){
    this.context = context ;
    this.iWorkModel = iWorkModel;
}

    @Override
    public void getReflushData(int type,int startIndex,int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_LIST, new NetWorkListBeen(type, startIndex,pageSize), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                WorkListBeen workListBeen = new Gson().fromJson(s, WorkListBeen.class);
                        if (workListBeen != null) {
                            if (workListBeen.getCode() == 0) {
                                List<WorkListBeen.InfoBean> datas = workListBeen.getInfo();
                                if (datas!=null&&datas.size()>0)
                                iWorkModel.getWorkLists(datas);
                                else ToastUtils.showShort(context,"暂无数据");
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

    @Override
    public void getMoreData(int type,int startIndex,int pageSize) {

    }
}
