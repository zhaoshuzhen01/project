package com.lubandj.master.been;

import com.example.baselibrary.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/5.
 */

public class MyPingjiaBeen extends BaseEntity {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * name : 洗衣机保养
         * service_icon : http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180417/2018041711591858480.jpg
         * content : 不太好n
         * img : ["http://lubandj.oss-cn-beijing.aliyuncs.com/comment_img/20180503/2018050314260745471.jpg","http://lubandj.oss-cn-beijing.aliyuncs.com/comment_img/20180503/2018050314260785935.jpg"]
         * create_time : 2018-05-03 14:26:07
         * star : 3
         */

        private String name;
        private String service_icon;
        private String content;
        private String create_time;
        private String star;
        private List<String> img;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
