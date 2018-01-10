package com.lubandj.master.model.MsgCenterModel;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.model.BaseModel;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class MsgCenterModel extends BaseModel {
    private IMsgCenterModel iMsgCenterModel ;

    public MsgCenterModel(Context context,IMsgCenterModel iMsgCenterModel){
        this.context = context ;
        this.iMsgCenterModel = iMsgCenterModel;
    }

    @Override
    public void getReflushData(int type,int startIndex,int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_MSGCENTER_LIST, new NetWorkListBeen(type, startIndex,pageSize), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                MsgCenterBeen msgCenterBeen = new Gson().fromJson(s, MsgCenterBeen.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        List<MsgCenterBeen.InfoBean.ListBean> datas = msgCenterBeen.getInfo().getList();
                        if (datas!=null)
                            iMsgCenterModel.getMsgCenterLists(datas);
                        if (datas.size()==0){
//                            ToastUtils.showShort(context,"暂无数据");
                        }
                    }else if (msgCenterBeen.getCode()==104){
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
