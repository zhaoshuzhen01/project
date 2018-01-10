package com.lubandj.master.InstanceUtil;

import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/10.
 */

public class NotifyMsgInstance {
   private List<MsgCenterBeen.InfoBean.ListBean>beens= new ArrayList<MsgCenterBeen.InfoBean.ListBean>();
    private static class SingletonHolder {
        private static NotifyMsgInstance instance = new NotifyMsgInstance();
    }

    /**
     * 私有的构造函数
     */
    private NotifyMsgInstance() {

    }

    public static NotifyMsgInstance getInstance() {
        return SingletonHolder.instance;
    }

    public void addNotifyBeens(MsgCenterBeen.InfoBean.ListBean been) {
        beens.add(been);
    }
    public void clearNotifyBeens(){
        beens.clear();
    }
    public List<MsgCenterBeen.InfoBean.ListBean> getNotifyBeens(){
        return beens ;
    }
}
