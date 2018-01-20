package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public class LoginBeen extends BaseEntity{

    /**
     * code : 0
     * message : 登录成功
     * info : {"userName":"lemsnaa","uid":101048,"id":8723,"token":"539d33c1-f450-bf34-05d5-b1ac1c6b9101","email":"lemsnaa@126.com"}
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
         * nickname : null
         * uid : 11
         * token : VC7D_X6Z2K9Z0wwUsFVn8mndnT6WrXfaynPk9-JH
         * mobile : 13522374928
         * face_url : null
         */

        private Object nickname;
        private int uid;
        private String token;
        private String mobile;
        private Object face_url;

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
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

        public Object getFace_url() {
            return face_url;
        }

        public void setFace_url(Object face_url) {
            this.face_url = face_url;
        }
    }
}
