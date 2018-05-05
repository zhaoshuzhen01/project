package com.lubandj.master.been;

import com.example.baselibrary.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class MyCons extends BaseEntity {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * service_item : 不限
         * expiry_info : 有效期1970-01-01至1970-01-01
         * name : null
         * amount : null
         * city : 不限
         * reduction : 抵扣券
         * id : null
         */

        private String service_item;
        private String expiry_info;
        private String name;
        private String amount;
        private String city;
        private String reduction;
        private String id;

        public String getService_item() {
            return service_item;
        }

        public void setService_item(String service_item) {
            this.service_item = service_item;
        }

        public String getExpiry_info() {
            return expiry_info;
        }

        public void setExpiry_info(String expiry_info) {
            this.expiry_info = expiry_info;
        }

        public Object getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getReduction() {
            return reduction;
        }

        public void setReduction(String reduction) {
            this.reduction = reduction;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
