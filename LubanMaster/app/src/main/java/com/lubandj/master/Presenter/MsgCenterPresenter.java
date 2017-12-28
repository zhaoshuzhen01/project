package com.lubandj.master.Presenter;

import android.content.Context;

import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.IMsgCenterListview;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.model.MsgCenterModel.IMsgCenterModel;
import com.lubandj.master.model.MsgCenterModel.MsgCenterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class MsgCenterPresenter implements ISheetlistPrester, IMsgCenterModel {
    private MsgCenterModel msgCenterModel;
    private static final int STARTINDEX = 1;//从第一条开始请求
    private static final int PAGESIZE = 10;//一次请求十条数据

    private int mstartIndex = 1;
    private IMsgCenterListview iMsgCenterListview;

    private List<MsgCenterBeen.InfoBean.ListBean> mdatas = new ArrayList<>();

    private boolean loadMore = false ;

    public MsgCenterPresenter(Context context, IMsgCenterListview iMsgCenterListview) {
        msgCenterModel = new MsgCenterModel(context,this);
        this.iMsgCenterListview = iMsgCenterListview ;
    }


    @Override
    public void getReflushData(int type) {
        loadMore = false ;
        msgCenterModel.getReflushData(Canstance.TYPE_LIST_ALL,STARTINDEX,PAGESIZE);
    }

    @Override
    public void getMoreData(int type) {
        loadMore = true ;
        if (mdatas!=null){
            mstartIndex+=mdatas.size();
        }
        msgCenterModel.getReflushData(Canstance.TYPE_LIST_ALL,mstartIndex,PAGESIZE);
    }



    @Override
    public void getMsgCenterLists(List<MsgCenterBeen.InfoBean.ListBean> datas) {
        if (loadMore){
            mdatas.addAll(datas);
        }else {
            mdatas = datas ;
        }
        iMsgCenterListview.getMsgCenterLists(mdatas);
    }
}
