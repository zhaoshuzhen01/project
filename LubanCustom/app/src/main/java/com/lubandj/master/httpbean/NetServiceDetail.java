package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class NetServiceDetail {

    public NetServiceDetail(String city,String service_id){
        this.city = city ;
        this.service_id = service_id ;
    }
    private String city ;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    private String service_id;
}
