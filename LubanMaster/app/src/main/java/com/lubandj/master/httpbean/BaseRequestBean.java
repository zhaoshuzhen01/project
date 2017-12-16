package com.lubandj.master.httpbean;

import java.util.Date;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-14
 * company:九州宏图
 */

public class BaseRequestBean {
    public String version;
    public int method;
    public String deviceId;
    public long timestamp;
    public String token;
    public Object params;


    public BaseRequestBean(int method, Object params, boolean hastoken) {
        this.version = "v1.0";
        this.method = method;
        this.deviceId = "7878-uiui-1231-21312";
        this.timestamp = new Date().getTime();
        if (hastoken)
            this.token = "U5FAFquVTM9oF1yxWB8OpU5FAFquVTM9oF1yxWB8Op";
        else
            this.token = "";
        this.params = params;
    }
}
