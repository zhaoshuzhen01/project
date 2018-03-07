package com.lubandj.master.httpbean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class NetBookBeen {

    /**
     * address_id : 1
     * amount : 100.0
     * coupon_id : 2
     * coupon_amount : 20.0
     * datetime : 2018-03-04 12:00
     * remark : 记得带工具
     * items : [{"id":1,"service_id":1,"spec_id":2,"service_name":"空调保养-柜式","price":"99.00","num":1},{"id":2,"service_id":2,"spec_id":2,"service_name":"空调保养-柜式","price":"99.00","num":1}]
     */

    private String address_id;
    private double amount;
    private int coupon_id;
    private double coupon_amount;
    private String datetime;
    private String remark;
    private List<ItemsBean> items;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 1
         * service_id : 1
         * spec_id : 2
         * service_name : 空调保养-柜式
         * price : 99.00
         * num : 1
         */

        private int id;
        private int service_id;
        private int spec_id;
        private String service_name;
        private String price;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getService_id() {
            return service_id;
        }

        public void setService_id(int service_id) {
            this.service_id = service_id;
        }

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
