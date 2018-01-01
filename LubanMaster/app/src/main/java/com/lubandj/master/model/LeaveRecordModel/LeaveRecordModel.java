package com.lubandj.master.model.LeaveRecordModel;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Presenter.LeaveRecordPresenter;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.httpbean.LeaveRecordResponse;
import com.lubandj.master.httpbean.NetWorkListBeen;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.model.BaseModel;
import com.lubandj.master.my.UserInfoResponse;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class LeaveRecordModel extends BaseModel {
    private ILeaveRecordModel mILeaveRecordModel;

    public LeaveRecordModel(Context context, ILeaveRecordModel mILeaveRecordModel) {
        this.context = context;
        this.mILeaveRecordModel = mILeaveRecordModel;
    }

    @Override
    public void getReflushData(int type, int startIndex, int pageSize) {

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_LEAVERECORD, new NetWorkListBeen(type, startIndex, pageSize), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                LeaveRecordResponse response = new LeaveRecordResponse();
                response = (LeaveRecordResponse) CommonUtils.generateEntityByGson(context, s, response);
                if (response != null) {
                    mILeaveRecordModel.getLeaveRecords(response.info);
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
