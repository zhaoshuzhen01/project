package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class NetWorkListBeen {
    int OrderStatus;
    int startIndex;
    int pageSize;

    public NetWorkListBeen(int type, int startIndex, int pageSize) {
        this.OrderStatus = type;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }

    public int getType() {
        return OrderStatus;
    }

    public void setType(int type) {
        this.OrderStatus = type;
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
