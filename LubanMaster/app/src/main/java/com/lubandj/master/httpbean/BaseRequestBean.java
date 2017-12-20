package com.lubandj.master.httpbean;

import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.SPUtils;

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
    public String token = "";
    public Object params;


    public BaseRequestBean(int method, Object params, boolean hastoken) {
        this.version = Canstance.VERSION;
        this.method = method;
        this.deviceId = CommonUtils.getDeviceid();
        this.timestamp = new Date().getTime();
        if (hastoken) {
            token = CommonUtils.getToken();
        }
        this.params = params;
    }
}
