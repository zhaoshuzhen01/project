package com.lubandj.master.httpbean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class HttpCheckCous {
    public HttpCheckCous(String amount, String city, List<String> service_ids) {
        this.amount = amount;
        this.city = city;
        this.service_ids = service_ids;
    }

    public String getAmount() {
        return amount;
    }

    public String getCity() {
        return city;
    }

    public List<String> getService_ids() {
        return service_ids;
    }

    private String amount;
    private String city ;
    private List<String>service_ids;
}
