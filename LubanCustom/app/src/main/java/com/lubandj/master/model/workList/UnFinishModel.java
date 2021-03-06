package com.lubandj.master.model.workList;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.OrderListBeen;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.model.BaseModel;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class UnFinishModel extends BaseModel {
private IWorkModel iWorkModel ;

public UnFinishModel(Context context,IWorkModel iWorkModel){
    this.context = context ;
    this.iWorkModel = iWorkModel;
}

    @Override
    public void getReflushData(int type,int startIndex,int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_UPDATE_STATUS, new NetWorkListBeen(type, startIndex,pageSize), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                OrderListBeen workListBeen = new Gson().fromJson(s, OrderListBeen.class);
                        if (workListBeen != null) {
                            if (workListBeen.getCode() == 0) {
                                List<OrderListBeen.InfoBean> datas = workListBeen.getInfo();
                                if (datas!=null){
                                    iWorkModel.getWorkLists(datas);
                                }
                            }else if (workListBeen.getCode()==104){
                                CommonUtils.tokenNullDeal(context);
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
