package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class HomeBeen extends BaseEntity{


    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * service_id : 22
         * name : 空调保养
         * service_icon : http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511550527933.jpg
         * service_pic : http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511551341592.jpg
         * price : 110.00
         */

        private String service_id;
        private String name;
        private String service_icon;
        private String service_pic;
        private String price;
        private int type;

        public String getService_id() {
            return service_id;
        }

        public void setService_id(String service_id) {
            this.service_id = service_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getService_icon() {
            return service_icon;
        }

        public void setService_icon(String service_icon) {
            this.service_icon = service_icon;
        }

        public String getService_pic() {
            return service_pic;
        }

        public void setService_pic(String service_pic) {
            this.service_pic = service_pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
