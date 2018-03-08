package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class WeiXinPayInfo extends BaseEntity {


    /**
     * info : {"appid":"wx2337ad10e3cd3cf1","partnerid":"1499143632","prepayid":"wx201803081001226d3318316c0995820882","packages":"Sign=WXPay","noncestr":"BF39ED14C728940180E0E7432F61BBA2","timestamp":1520474482,"sign":"F2D27A3AD7F0B8AFBA9345BBE910A2D4"}
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
         * appid : wx2337ad10e3cd3cf1
         * partnerid : 1499143632
         * prepayid : wx201803081001226d3318316c0995820882
         * packages : Sign=WXPay
         * noncestr : BF39ED14C728940180E0E7432F61BBA2
         * timestamp : 1520474482
         * sign : F2D27A3AD7F0B8AFBA9345BBE910A2D4
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String packages;
        private String noncestr;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackages() {
            return packages;
        }

        public void setPackages(String packages) {
            this.packages = packages;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
