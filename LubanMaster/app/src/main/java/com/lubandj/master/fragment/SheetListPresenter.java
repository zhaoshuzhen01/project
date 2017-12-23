package com.lubandj.master.fragment;

import android.content.Context;

import com.lubandj.master.Canstance;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.fragment.model.BaseModel;
import com.lubandj.master.fragment.model.IWorkModel;
import com.lubandj.master.fragment.model.IworkListView;
import com.lubandj.master.fragment.model.UnFinishModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class SheetListPresenter implements ISheetlistPrester , IWorkModel {
    private UnFinishModel unFinishModel;
    private static final int STARTINDEX = 1;//从第一条开始请求
    private static final int PAGESIZE = 10;//一次请求十条数据

    private int mstartIndex = 1;
    private IworkListView iworkListView;

    private List<WorkListBeen.InfoBean> mdatas = new ArrayList<>();

    private boolean loadMore = false ;

    public SheetListPresenter(Context context,IworkListView iworkListView) {
        unFinishModel = new UnFinishModel(context,this);
        this.iworkListView = iworkListView ;
    }


    @Override
    public void getReflushData(int type) {
        ++type;
        loadMore = false ;
        switch (type) {
            case Canstance.TYPE_LIST_UNFINISH:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_UNFINISH,STARTINDEX,PAGESIZE);
                break;
            case Canstance.TYPE_LIST_FINISH:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_FINISH,STARTINDEX,PAGESIZE);
                break;
            case Canstance.TYPE_LIST_CANCELED:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_CANCELED,STARTINDEX,PAGESIZE);
                break;
        }
    }

    @Override
    public void getMoreData(int type) {
        ++type;
        loadMore = true ;
        if (mdatas!=null){
            mstartIndex+=mdatas.size();
        }
        switch (type) {
            case Canstance.TYPE_LIST_UNFINISH:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_UNFINISH,mstartIndex,PAGESIZE);
                break;
            case Canstance.TYPE_LIST_FINISH:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_FINISH,mstartIndex,PAGESIZE);
                break;
            case Canstance.TYPE_LIST_CANCELED:
                unFinishModel.getReflushData(Canstance.TYPE_LIST_CANCELED,mstartIndex,PAGESIZE);
                break;
        }
    }

    @Override
    public void getWorkLists(List<WorkListBeen.InfoBean> datas) {
        if (loadMore){
            mdatas.addAll(datas);
        }else {
            mdatas = datas ;
        }
        iworkListView.getWorkLists(mdatas);
    }
}
