package com.lubandj.customer.httpbean;

import com.lubandj.master.httpbean.BaseResponseBean;

import java.util.List;

/**
 * function:
 * author:yangjinjian
 * date: 2018-5-2
 * company:九州宏图
 */

public class CancelOrderInfoResponse extends BaseResponseBean{
    public CancelOrderInfo info;

    public class CancelOrderInfo {
        public List<CancelOrderService> service;
        public String pay_amount;
    }

    public class CancelOrderService {
        public String name;
        public String service_icon;
        public String service_id;
        public String amount;
        public String total;
    }
}
