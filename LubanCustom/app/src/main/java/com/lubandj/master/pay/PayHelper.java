package com.lubandj.master.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.Map;

/**
 * Created by xts on 2017/8/7.
 */

public class PayHelper {
    public static final String WXPAY = "wxpay";
    public static final String ALIPAY = "alipay";
    public void aliPay(Activity activity, String sign, IPayResultCallback payResultCallback) {
        new AlipayTask(activity, sign, payResultCallback).execute();
    }

    public void wxPay(Activity context, WxPayInfo info, IPayResultCallback callBack) {
        PayReq req = new PayReq();
        String appid = info.getResult().getWx().getAppid();
        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp(appid);

        req.appId = appid;
        req.partnerId = info.getResult().getWx().getPartnerid();
        req.prepayId = info.getResult().getWx().getPrepayid();//预支付订单号：核心参数，该参数是微信服务器生成，相当于订单号
        req.nonceStr = info.getResult().getWx().getNoncestr();
        req.timeStamp = info.getResult().getWx().getTimestamp();
        req.packageValue = info.getResult().getWx().getPackageValue();
        req.sign = info.getResult().getWx().getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
        boolean chek = req.checkArgs();
       boolean  payResult =  api.sendReq(req);

        //4.处理支付结果：WXPayEntryActivity类的onResp处理
    }

    private class AlipayTask extends AsyncTask<String, Integer, Map<String, String>> {
        private Activity mContext;
        private String mPayInfo;
        private IPayResultCallback mPayResultCallback;

        public AlipayTask(Activity context, String payInfo, IPayResultCallback payResultCallback) {
            super();
            mContext = context;
            mPayInfo = payInfo;
            mPayResultCallback = payResultCallback;
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(mContext);
            // 调用支付接口，获取支付结果
            Map<String, String> map = alipay.payV2(mPayInfo, true);
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            mPayResultCallback.payResult(result, PayHelper.ALIPAY);
        }
    }
}
