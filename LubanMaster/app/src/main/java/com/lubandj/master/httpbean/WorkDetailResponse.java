package com.lubandj.master.httpbean;

import com.lubandj.master.been.WorkListBeen;

import java.util.ArrayList;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-18
 * company:九州宏图
 */

public class WorkDetailResponse extends BaseResponseBean {
    public WorkDetailBean info;

    public class WorkDetailBean {
        public String isLeave;
        public LeaveTime leaveTime;
        public ArrayList<WorkListBeen.InfoBean> list;

    }

    public class LeaveTime {
        public String begin;
        public String end;
    }
}
