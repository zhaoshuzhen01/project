package com.lubandj.master.Presenter;

import android.content.Context;

import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.model.BaseModel;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/28.
 */

public class BaseReflushPresenter<T> extends BasePresenter<T> {
    public BaseReflushPresenter(Context context, IbaseView ibaseView, BaseModel baseModel) {
        mcontext = context ;
        workModel = baseModel;
        workModel.setIBaseMode(this);
        this.ibaseView = ibaseView ;
    }
    @Override
    public void getReflushData(int type) {
        super.getReflushData(type);
        if (type!=0){
            workModel.getReflushData(type,STARTINDEX,PAGESIZE);
        }else {
            workModel.getReflushData(Canstance.TYPE_LIST_ALL,STARTINDEX,PAGESIZE);
        }
    }
    @Override
    public void getMoreData(int type) {
        super.getMoreData(type);
        if (type!=0){
            workModel.getReflushData(type,mstartIndex,PAGESIZE);
        }else {
            workModel.getReflushData(Canstance.TYPE_LIST_ALL,mstartIndex,PAGESIZE);
        }
    }
    @Override
    public void getDataLists(List<T> datas) {
        super.getDataLists(datas);
        ibaseView.getDataLists(mdatas);
    }
}
