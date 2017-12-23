package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2017/12/23.
 */

public class NetClickStateBeen {
    /*
    阿良:
默认就是待执行
阿良:
点开始上门，状态改为2
阿良:
点开始服务，状态改为3
阿良:
服务结束。状态改为4
    * */
    private int status ;
    private int id ;

    public NetClickStateBeen(int status, int id) {
        this.status = status;
        this.id = id;



    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
