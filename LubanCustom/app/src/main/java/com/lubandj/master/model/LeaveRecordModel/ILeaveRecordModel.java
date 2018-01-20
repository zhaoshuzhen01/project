package com.lubandj.master.model.LeaveRecordModel;

import com.lubandj.master.been.LeaveBean;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public interface ILeaveRecordModel {
    public void getLeaveRecords(List<LeaveBean> datas);
}
