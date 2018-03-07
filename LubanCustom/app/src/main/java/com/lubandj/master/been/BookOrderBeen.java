package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

import java.io.Serializable;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class BookOrderBeen extends BaseEntity {

    /**
     * info : {"id":"266","order_id":"20180302162044999954","pay_amount":340}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 266
         * order_id : 20180302162044999954
         * pay_amount : 340
         */

        private String id;
        private String order_id;
        private String pay_amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }
    }
}
