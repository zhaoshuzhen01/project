package com.lubandj.master.pay;

import android.text.TextUtils;


import java.util.Map;

/**
 * Created by xts on 2017/8/7.
 */

public abstract class PayResultCallbackImpl implements IPayResultCallback {
    @Override
    public void payResult(Map<String, String> result, String payType) {
        switch (payType) {
            case PayHelper.ALIPAY:
                aliPayResult(result, payType);
                break;
            case PayHelper.WXPAY:
                wxPayResult(result, payType);
                break;
        }

    }

    private void wxPayResult(Map<String, String> result, String payType) {
    }


    /**
     * 支付宝支付结果
     *
     * @param result
     * @param payType
     */
    private void aliPayResult(Map<String, String> result, String payType) {
        //判断支付结果
        /**
         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
         * docType=1) 建议商户依赖异步通知
         */
        PayResult payResult = new PayResult(result);
        /**
         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
         */
        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
        String resultStatus = payResult.getResultStatus();
        // 判断resultStatus 为9000则代表支付成功
        if (TextUtils.equals(resultStatus, "9000")) {
            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
            onPaySuccess(resultInfo,payType);
        } else {
            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
            //8000 -> 正在处理中
            //4000 -> 订单支付失败
            //6001 -> 用户中途取消
            //6002 ->网络连接出错
            if (TextUtils.equals(resultStatus, "4000")){
                onPayFail("支付失败",payType);
            }else if (TextUtils.equals(resultStatus, "6001")){
                onPayFail("支付已被取消",payType);
            }else if (TextUtils.equals(resultStatus, "6002")){
                onPayFail("网络连接出错",payType);
            }else {
                onPayFail("支付失败",payType);
            }
        }
    }

    /**
     * 支付成功
     *
     * @param result
     * @param payType
     */
    public abstract void onPaySuccess(String result, String payType);

    /**
     * 支付失败
     *
     * @param result
     * @param payType
     */
    public abstract void onPayFail(String result, String payType);
}


