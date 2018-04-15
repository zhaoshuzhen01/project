package com.lubandj.master.been;

import com.example.baselibrary.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/4/14.
 */

public class StartGuagnBeen extends BaseEntity {

    /**
     * info : {"picture":"http://lubandj.oss-cn-beijing.aliyuncs.com/ads/20171210/2017121000112789793.jpg","linkurl":"http://www.baidu.com"}
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
         * picture : http://lubandj.oss-cn-beijing.aliyuncs.com/ads/20171210/2017121000112789793.jpg
         * linkurl : http://www.baidu.com
         */

        private String picture;
        private String linkurl;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }
    }
}
