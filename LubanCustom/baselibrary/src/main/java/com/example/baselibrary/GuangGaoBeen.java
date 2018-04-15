package com.example.baselibrary;

import com.example.baselibrary.BaseEntity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/4/14.
 */

public class GuangGaoBeen extends BaseEntity {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * picture : http://lubandj.oss-cn-beijing.aliyuncs.com/ads/20180413/2018041317333840293.jpg
         * linkurl :
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
