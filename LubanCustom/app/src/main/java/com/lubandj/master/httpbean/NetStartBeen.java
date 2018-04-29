package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2018/4/29.
 */

public class NetStartBeen {
    int startIndex;
    int pageSize;

    public NetStartBeen( int startIndex, int pageSize) {
        this.startIndex = startIndex;
        this.pageSize = pageSize;
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
