package com.lubandj.master.Presenter;

import android.content.Context;

import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.model.BaseModel;
import com.lubandj.master.model.IbaseModel;
import com.example.baselibrary.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/18.
 */

public  class BasePresenter <T> implements IRefleshLoadMorePrester,IbaseModel<T> {

    protected static final int STARTINDEX = 1;//从第一条开始请求
    protected static final int PAGESIZE = 10;//一次请求十条数据

    protected int mstartIndex = 1;
    protected IbaseView ibaseView;

    protected List<T> mdatas = new ArrayList<>();

    protected boolean loadMore = false ;
    protected BaseModel workModel ;
    protected Context mcontext ;

    @Override
    public void getReflushData(int type) {
        if (!NetworkUtils.isNetworkAvailable(mcontext)){
            ibaseView.getDataLists(mdatas);
            return;
        }
        loadMore = false ;
        mstartIndex = 1 ;
    }

    @Override
    public void getMoreData(int type) {
        if (!NetworkUtils.isNetworkAvailable(mcontext)){
            ibaseView.getDataLists(mdatas);
            return;
        }
        loadMore = true ;
        if (mdatas!=null){
            mstartIndex=mstartIndex+1;
        }
    }

    @Override
    public void getDataLists(List<T> datas) {
        if (loadMore){
            mdatas.addAll(datas);
        }else {
            mdatas = datas ;
        }
    }
}
