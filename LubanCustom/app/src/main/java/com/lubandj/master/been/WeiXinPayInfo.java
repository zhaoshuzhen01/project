package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class WeiXinPayInfo extends BaseEntity {

    /**
     * info : {"return_code":"SUCCESS","return_msg":"OK","appid":"wx2337ad10e3cd3cf1","mch_id":"1499143632","nonce_str":"NR6BDlYhUKkmpSQN","sign":"5AA9970BD17ECD96517954DA47BE9BD0","result_code":"SUCCESS","prepay_id":"wx2018030720102011e713cb850002830684","trade_type":"APP","partnerid":"1499143632","timestamp":1520424620}
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
         * return_code : SUCCESS
         * return_msg : OK
         * appid : wx2337ad10e3cd3cf1
         * mch_id : 1499143632
         * nonce_str : NR6BDlYhUKkmpSQN
         * sign : 5AA9970BD17ECD96517954DA47BE9BD0
         * result_code : SUCCESS
         * prepay_id : wx2018030720102011e713cb850002830684
         * trade_type : APP
         * partnerid : 1499143632
         * timestamp : 1520424620
         */

        private String return_code;
        private String return_msg;
        private String appid;
        private String mch_id;
        private String nonce_str;
        private String sign;
        private String result_code;
        private String prepay_id;
        private String trade_type;
        private String partnerid;
        private int timestamp;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
