package com.lubandj.master.httpbean;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class NetAddCar {

    private String service_type;

    private String service_id;

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    private String spec_id;

    private String service_name ;

    public NetAddCar(String service_type, String service_id, String spec_id, String service_name, String price, String num) {
        this.service_type = service_type;
        this.service_id = service_id;
        this.spec_id = spec_id;
        this.service_name = service_name;
        this.price = price;
        this.num = num;
    }

    private String price;

    private String num ;
}
