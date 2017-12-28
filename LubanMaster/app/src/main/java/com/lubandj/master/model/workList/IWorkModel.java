package com.lubandj.master.model.workList;

import com.lubandj.master.been.WorkListBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public interface IWorkModel {
    public void getWorkLists(List<WorkListBeen.InfoBean> datas);
}
