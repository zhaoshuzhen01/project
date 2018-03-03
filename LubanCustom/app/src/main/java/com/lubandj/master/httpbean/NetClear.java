package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class NetClear {
    public String getId() {
        return id;
    }

    private String id ;

    public int getNum() {
        return num;
    }

    private int num ;

    public NetClear(String id) {
        this.id = id;
    }
    public NetClear(String id,int num) {
        this.id = id;
        this.num = num ;
    }
}
