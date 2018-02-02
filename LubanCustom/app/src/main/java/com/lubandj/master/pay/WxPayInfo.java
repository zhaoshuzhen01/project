package com.lubandj.master.pay;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by xts on 2017/8/7.
 */

public class WxPayInfo implements Serializable{


    /**
     * code : 0
     * desc :
     * result : {"id":183,"wx":{"appid":"wx2281f6dda3069029","noncestr":"JzODukXqLCnH0EObq1Hasf9J1oUqorL2","package":"Sign=WXPay","partnerid":"1462681502","prepayid":"wx20170809140741bac7bf509d0895668376","timestamp":"1502258861","sign":"0F9934200A13C940612FD9BB7B8E21B6"}}
     */

    private int code;
    private String desc;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 183
         * wx : {"appid":"wx2281f6dda3069029","noncestr":"JzODukXqLCnH0EObq1Hasf9J1oUqorL2","package":"Sign=WXPay","partnerid":"1462681502","prepayid":"wx20170809140741bac7bf509d0895668376","timestamp":"1502258861","sign":"0F9934200A13C940612FD9BB7B8E21B6"}
         */

        private int id;
        private WxBean wx;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public WxBean getWx() {
            return wx;
        }

        public void setWx(WxBean wx) {
            this.wx = wx;
        }

        public static class WxBean {
            /**
             * appid : wx2281f6dda3069029
             * noncestr : JzODukXqLCnH0EObq1Hasf9J1oUqorL2
             * package : Sign=WXPay
             * partnerid : 1462681502
             * prepayid : wx20170809140741bac7bf509d0895668376
             * timestamp : 1502258861
             * sign : 0F9934200A13C940612FD9BB7B8E21B6
             */

            private String appid;
            private String noncestr;
            private String packageValue;
            private String partnerid;
            private String prepayid;
            private String timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageValue() {
                return packageValue;
            }

            public void setPackageValue(String packageValue) {
                this.packageValue = packageValue;
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

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            @Override
            public String toString() {
                return "WxBean{" +
                        "appid='" + appid + '\'' +
                        ", noncestr='" + noncestr + '\'' +
                        ", packageValue='" + packageValue + '\'' +
                        ", partnerid='" + partnerid + '\'' +
                        ", prepayid='" + prepayid + '\'' +
                        ", timestamp='" + timestamp + '\'' +
                        ", sign='" + sign + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id=" + id +
                    ", wx=" + wx +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WxPayInfo{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", result=" + result +
                '}';
    }

    public static WxPayInfo createBean(JSONObject obj){
        if (null == obj)
            return null;
        WxPayInfo bean = new WxPayInfo();
        bean.setCode(obj.optInt("code"));
        bean.setDesc(obj.optString("desc"));
        JSONObject jsonObject = obj.optJSONObject("result");
        if (jsonObject != null){
            WxPayInfo.ResultBean resultBean = new WxPayInfo.ResultBean();
            resultBean.setId(jsonObject.optInt("id"));
            JSONObject jsonObject1 = jsonObject.optJSONObject("wx");
            if (jsonObject1 != null){
                ResultBean.WxBean wxBean = new ResultBean.WxBean();
                wxBean.setAppid(jsonObject1.optString("appid"));
                wxBean.setNoncestr(jsonObject1.optString("noncestr"));
                wxBean.setPackageValue(jsonObject1.optString("package"));
                wxBean.setPartnerid(jsonObject1.optString("partnerid"));
                wxBean.setPrepayid(jsonObject1.optString("prepayid"));
                wxBean.setTimestamp(jsonObject1.optString("timestamp"));
                wxBean.setSign(jsonObject1.optString("sign"));
                resultBean.setWx(wxBean);
            }

            bean.setResult(resultBean);
        }
        return bean;
    }
}
