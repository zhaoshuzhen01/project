package com.lubandj.customer.bean;

import java.util.List;

/**
 * function:
 * author:yangjinjian
 * date: 2018-4-29
 * company:九州宏图
 */

public class OrderDetailBean {
    public String id;//265
    public String order_id;//20180302161951555048
    public String contact_name;//2121
    public String contact_mobile;//
    public String address;//
    public String datatime;//2018-03-04 12:00:00
    public String created_time;//2018-03-04 12:00:00
    public String remark;//xxxx
    public String order_sn;//12321222233
    public String pay_way;//微信支付
    public String coupon_amount;//10
    public String amount;//340.00
    public String pay_amount;//0.00
    public String status;//1
    public String statusText;//待指派
    public String pay_status;//1
    public String pay_statusText;//待付款
    public ServiceUserBean service_user_info;
    public List<RefundBean> refund_info;//
    public List<ServiceTotalBean> items;//
    public List<OrderLogBean> order_log;
}
