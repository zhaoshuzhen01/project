package com.lubandj.master.been;

import com.example.baselibrary.photomanager.Photo;
import com.lubandj.master.httpbean.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/17.
 */

public class OrderListBeen extends BaseEntity {


    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean implements Serializable{
        /**
         * id : 265
         * order_id : 20180302161951555048
         * contact_name : 2121
         * contact_mobile :
         * address :
         * datatime : 2018-03-04 12:00:00
         * amount : 340.00
         * pay_amount : 0.00
         * status : 1
         * statusText : 待指派
         * pay_status : 1
         * pay_statusText : 待付款
         * items : [{"service_name":"空调保养-挂式","num":"2"},{"service_name":"空调保养-挂式","num":"1"}]
         */

        private String id;
        private String order_id;
        private String contact_name;
        private String contact_mobile;
        private String address;
        private String datatime;
        private String amount;
        private String pay_amount;
        private String status;
        private String statusText;
        private String pay_status;
        private String pay_statusText;
        private List<ItemsBean> items;

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

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_mobile() {
            return contact_mobile;
        }

        public void setContact_mobile(String contact_mobile) {
            this.contact_mobile = contact_mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDatatime() {
            return datatime;
        }

        public void setDatatime(String datatime) {
            this.datatime = datatime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_statusText() {
            return pay_statusText;
        }

        public void setPay_statusText(String pay_statusText) {
            this.pay_statusText = pay_statusText;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable{
            /**
             * service_name : 空调保养-挂式
             * num : 2
             */

            private String service_name;
            private String num;

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
            }

            private String star;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            private String content;

            public String getService_id() {
                return service_id;
            }

            public void setService_id(String service_id) {
                this.service_id = service_id;
            }

            private String service_id ;

            public String getService_name() {
                return service_name;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getService_icon() {
                return service_icon;
            }

            public void setService_icon(String service_icon) {
                this.service_icon = service_icon;
            }

            public String service_icon ;
            public List<Photo> getPhotos() {
                return photos;
            }

            public void setPhotos(List<Photo> photos) {
                this.photos = photos;
            }

            public List<Photo> photos=new ArrayList<>();

        }
    }
}
