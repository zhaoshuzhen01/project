package com.lubandj.master.httpbean;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-14
 * company:九州宏图
 */

public class SendSmsBean {
    public String mobile;
    public String templateId;

    public SendSmsBean(String mobile, String templateId) {
        this.mobile = mobile;
        this.templateId = templateId;
    }

    public static final String KEY_REGISTER_TEMPLATEID = "801";
    public static final String KEY_LOGIN_TEMPLATEID = "802";
    public static final String KEY_CHANGE_PASSWORD_TEMPLATEID = "803";
    public static final String KEY_BINGING_PHONE_TEMPLATEID = "804";
}
