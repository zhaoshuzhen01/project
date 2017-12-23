package com.lubandj.master.fragment.model;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public abstract class BaseModel {

    public abstract void getReflushData(int type,int startIndex,int pageSize);

    public abstract void getMoreData(int type,int startIndex,int pageSize);
}
