package com.lubandj.master.model;

import android.content.Context;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public abstract class BaseModel {
    protected Context context ;
    protected IbaseModel ibaseModel ;
    public void setIBaseMode(IbaseModel ibaseModel){
        this.ibaseModel = ibaseModel;
    }
    public abstract void getReflushData(int type,int startIndex,int pageSize);
}
