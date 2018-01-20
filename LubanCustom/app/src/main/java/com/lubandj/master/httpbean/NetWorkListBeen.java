package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class NetWorkListBeen {
    int type;
    int startIndex;
    int pageSize;

    public NetWorkListBeen(int type, int startIndex, int pageSize) {
        this.type = type;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
