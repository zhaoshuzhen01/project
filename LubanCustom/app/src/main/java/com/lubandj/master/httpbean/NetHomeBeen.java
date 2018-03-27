package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class NetHomeBeen {

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;

    public String getReType() {
        return reType;
    }

    public void setReType(String reType) {
        this.reType = reType;
    }

    private String reType;


    public NetHomeBeen(String city, String reType) {
        this.city = city;
        this.reType = reType;

    }
}