package com.lubandj.master.been;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public class LoginBeen {

    /**
     * code : 0
     * message : 登录成功
     * info : {"userName":"lemsnaa","uid":101048,"id":8723,"token":"539d33c1-f450-bf34-05d5-b1ac1c6b9101","email":"lemsnaa@126.com"}
     */

    private int code;
    private String message;
    private InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * userName : lemsnaa
         * uid : 101048
         * id : 8723
         * token : 539d33c1-f450-bf34-05d5-b1ac1c6b9101
         * email : lemsnaa@126.com
         */

        private String userName;
        private int uid;
        private int id;
        private String token;
        private String email;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        @Override
        public String toString() {
            return "InfoBean{" +
                    "userName='" + userName + '\'' +
                    ", uid=" + uid +
                    ", id=" + id +
                    ", token='" + token + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "LoginBeen{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", info=" + info +
                '}';
    }
}
