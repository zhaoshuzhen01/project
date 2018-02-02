package com.lubandj.master.pay;

/**
 * Created by xts on 2017/8/7.
 * 支付接口
 * payType: wechat,微信支付;alipay,支付宝;
 * amount:金额;
 */

public interface IPay {
    void pay(String payType, String amount);
}
