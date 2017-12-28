package com.lubandj.master.model.MsgCenterModel;

import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.WorkListBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public interface IMsgCenterModel {
        public void getMsgCenterLists(List<MsgCenterBeen.InfoBean.ListBean> datas);
}
