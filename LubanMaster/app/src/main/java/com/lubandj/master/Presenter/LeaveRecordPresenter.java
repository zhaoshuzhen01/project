package com.lubandj.master.Presenter;

import android.content.Context;

import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.ILeaveRecordListview;
import com.lubandj.master.Iview.IMsgCenterListview;
import com.lubandj.master.been.LeaveBean;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.model.LeaveRecordModel.ILeaveRecordModel;
import com.lubandj.master.model.LeaveRecordModel.LeaveRecordModel;
import com.lubandj.master.model.MsgCenterModel.IMsgCenterModel;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class LeaveRecordPresenter implements ISheetlistPrester, ILeaveRecordModel {
    private LeaveRecordModel msgCenterModel;
    private static final int STARTINDEX = 1;//从第一条开始请求
    private static final int PAGESIZE = 10;//一次请求十条数据

    private int mstartIndex = 1;
    private ILeaveRecordListview iMsgCenterListview;

    private List<LeaveBean> mdatas = new ArrayList<>();

    private boolean loadMore = false;

    public LeaveRecordPresenter(Context context, ILeaveRecordListview iMsgCenterListview) {
        msgCenterModel = new LeaveRecordModel(context, this);
        this.iMsgCenterListview = iMsgCenterListview;
    }


    @Override
    public void getReflushData(int type) {
        loadMore = false;
        msgCenterModel.getReflushData(type, STARTINDEX, PAGESIZE);
    }

    @Override
    public void getMoreData(int type) {
        loadMore = true;
        if (mdatas != null) {
            mstartIndex += mdatas.size();
        }
        msgCenterModel.getReflushData(type, mstartIndex, PAGESIZE);
    }


    @Override
    public void getLeaveRecords(List<LeaveBean> datas) {
        if (loadMore) {
            mdatas.addAll(datas);
        } else {
            mdatas = datas;
        }
        iMsgCenterListview.getLeaveRecords(mdatas);
    }
}
