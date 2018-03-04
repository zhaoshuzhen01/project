package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class ServiceDetailBeen extends BaseEntity {

    /**
     * info : {"service_id":"22","name":"空调保养","service_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511551341592.jpg","content":"急急急","items":[{"spec_id":"44","service_id":"22","item_name":"空调保养-挂式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511554573163.jpg","price":"110.00"},{"spec_id":"45","service_id":"22","item_name":"空调保养-挂式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511554573163.jpg","price":"110.00"},{"spec_id":"47","service_id":"22","item_name":"空调保养-柜式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511555281443.jpg","price":"110.00"},{"spec_id":"46","service_id":"22","item_name":"空调保养-柜式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511555281443.jpg","price":"220.00"}]}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * service_id : 22
         * name : 空调保养
         * service_pic : http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511551341592.jpg
         * content : 急急急
         * items : [{"spec_id":"44","service_id":"22","item_name":"空调保养-挂式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511554573163.jpg","price":"110.00"},{"spec_id":"45","service_id":"22","item_name":"空调保养-挂式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511554573163.jpg","price":"110.00"},{"spec_id":"47","service_id":"22","item_name":"空调保养-柜式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511555281443.jpg","price":"110.00"},{"spec_id":"46","service_id":"22","item_name":"空调保养-柜式","item_pic":"http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511555281443.jpg","price":"220.00"}]
         */

        private String service_id;
        private String name;
        private String service_pic;
        private String content;
        private List<ItemsBean> items;

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

        public String getService_pic() {
            return service_pic;
        }

        public void setService_pic(String service_pic) {
            this.service_pic = service_pic;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * spec_id : 44
             * service_id : 22
             * item_name : 空调保养-挂式
             * item_pic : http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180115/2018011511554573163.jpg
             * price : 110.00
             */

            private String spec_id;
            private String service_id;
            private String item_name;
            private String item_pic;
            private String price;

            public String getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(String spec_id) {
                this.spec_id = spec_id;
            }

            public String getService_id() {
                return service_id;
            }

            public void setService_id(String service_id) {
                this.service_id = service_id;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_pic() {
                return item_pic;
            }

            public void setItem_pic(String item_pic) {
                this.item_pic = item_pic;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}