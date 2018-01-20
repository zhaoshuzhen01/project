package com.lubandj.master.httpbean;

/**
 * @author: lj
 * @Time: 2017/12/18 11:55
 * @Description: This is UserInfoEntity
 */

public class UserInfoEntity {


    /**
     * uid : 101048
     * mobile : 18813003698
     * token : mBXkLOfQuwWAsmfJG+q+6JN6Pq8OV4HvowNg
     * face_url : http://url
     */

    private int uid;
    private String mobile;
    private String token;
    private String face_url;

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "uid=" + uid +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                ", face_url='" + face_url + '\'' +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFace_url() {
        return face_url;
    }

    public void setFace_url(String face_url) {
        this.face_url = face_url;
    }
}
