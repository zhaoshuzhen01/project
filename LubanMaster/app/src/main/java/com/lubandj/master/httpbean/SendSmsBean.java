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
}
