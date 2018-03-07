package com.lubandj.master.httpbean;

/**
 * @author: lj
 * @Time: 2017/12/18 11:28
 * @Description: This is LoginAppBean
 */

public class LoginAppBean {


    private String mobile;
    private String verifyCode;
    public String openid ;


    public LoginAppBean(String mobile, String verifyCode) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
