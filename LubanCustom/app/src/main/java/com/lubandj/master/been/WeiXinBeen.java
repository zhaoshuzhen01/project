package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class WeiXinBeen extends BaseEntity {

    /**
     * info : {"name":null,"uid":75,"token":"OF-k2eXhr8FLJqosZNM3Hqogn4jsCvPPdH_zWMmq","tel":"18661985898","face":null,"reg_time":"2018-01-15 20:40:41","sex":"男","wechat":null,"openid":null,"realname":null,"idcard":"***************","birthday":null,"marry":"未设置","school":null,"education":null,"province":"","city":null,"area":null,"address":null,"housing_estate":null,"house_number":null,"phone":null,"login_times":5,"last_login_ip":"127.0.0.1","last_login_time":"2018-03-05 13:34:14"}
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
         * name : null
         * uid : 75
         * token : OF-k2eXhr8FLJqosZNM3Hqogn4jsCvPPdH_zWMmq
         * tel : 18661985898
         * face : null
         * reg_time : 2018-01-15 20:40:41
         * sex : 男
         * wechat : null
         * openid : null
         * realname : null
         * idcard : ***************
         * birthday : null
         * marry : 未设置
         * school : null
         * education : null
         * province :
         * city : null
         * area : null
         * address : null
         * housing_estate : null
         * house_number : null
         * phone : null
         * login_times : 5
         * last_login_ip : 127.0.0.1
         * last_login_time : 2018-03-05 13:34:14
         */

        private String nickname;
        private int uid;
        private String token;
        private String mobile;
        private String face_url;
        private String reg_time;
        private String sex;
        private String wechat;
        private String openid;
        private String realname;
        private String idcard;
        private String birthday;
        private String marry;
        private String school;
        private String education;
        private String province;
        private String city;
        private String area;
        private String address;
        private String housing_estate;
        private String house_number;
        private String phone;
        private int login_times;
        private String last_login_ip;
        private String last_login_time;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFace_url() {
            return face_url;
        }

        public void setFace_url(String face_url) {
            this.face_url = face_url;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMarry() {
            return marry;
        }

        public void setMarry(String marry) {
            this.marry = marry;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHousing_estate() {
            return housing_estate;
        }

        public void setHousing_estate(String housing_estate) {
            this.housing_estate = housing_estate;
        }

        public String getHouse_number() {
            return house_number;
        }

        public void setHouse_number(String house_number) {
            this.house_number = house_number;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getLogin_times() {
            return login_times;
        }

        public void setLogin_times(int login_times) {
            this.login_times = login_times;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }
    }
}
