package com.lubandj.master.pay;

import android.app.Activity;

import com.lubandj.master.been.WeiXinPayInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xts on 2017/8/7.
 */

public class Pay implements IPay {
    private IPayResultCallback mCallBack;
    private Activity mContext;
    private String mPayType;
    private String mAmount;
    private final PayHelper mPayHelper;
    private String jsonObject = "{\"code\":0,\"desc\":\"\",\"result\":{\"id\":716,\"wx\":{\"appid\":\"wx2281f6dda3069029\",\"noncestr\":\"cqikbJsoenPWjTbG1F2YJ28Dru5a2PyR\",\"package\":\"Sign=WXPay\",\"partnerid\":\"1462681502\",\"prepayid\":\"wx201801301600228ea5ca50d70858144114\",\"timestamp\":\"1517299222\",\"sign\":\"5BE024DE0814CE75F2B40533EE765CC6\"}}}";
    private String info = "app_id=2017071807800448&method=alipay.trade.app.pay&version=1.0&format=JSON&charset=utf8&sign_type=RSA2&timestamp=2018-01-3015: 13: 31&notify_url=https: //api.banmi.com/api/app/v3/payments/alibaba&biz_content={\n" +
            "  \"subject\": \"ä¼´ç±³æ\u0097\u0085è¡\u008C\",\n" +
            "  \"out_trade_no\": \"APP1517296411012ALIPAY951585\",\n" +
            "  \"total_amount\": \"30.00\",\n" +
            "  \"timeout_express\": \"15m\"\n" +
            "}&sign=ZLXiepymKQbZQDWMI54rZyiVICF1IkMfDnHPFMgCR4Wa9OGrtkLrMxUNlWpk5pItUvT0ZPjpHzhOpZuMMUa436k3S31JJOqFsx2//UDRq7OBSFjZjt4f6bZJ1EeWon12UcS1E5aKNiPTiTZIIzo/3BhAxJ99M6/loNNaNA5G7dxfsYfFX18zYlbstWG/XvaFSB5I9LezuutHea5Ay1CjJ74j7wjDLEmX+asQzhXqJ+aAXJmlviY8weKR3iW30A5HVn4z+JnZsSfZRsfhT6CTCBo0gjv81fq2pCE04NyISg0e1bBtzt1iBBNgRHRzNl/m4KQocKOIxX4dr1j5oQVUVA==";

    public Pay(Activity context, IPayResultCallback callback) {
        this.mContext = context;
        this.mCallBack = callback;
        mPayHelper = new PayHelper();
    }

    @Override
    public void pay(final String payType, String amount) {
        mPayType = payType;
        mAmount = amount;
        if (PayHelper.ALIPAY.equalsIgnoreCase(payType)) {

            callAlipay(info);
        } else if (PayHelper.WXPAY.equalsIgnoreCase(payType)) {
            try {
                WxPayInfo info = WxPayInfo.createBean(new JSONObject(jsonObject));
                callWxPay(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void payOrder(final String payType,Object data) {
        if (PayHelper.ALIPAY.equalsIgnoreCase(payType)) {

            callAlipay(info);
        } else if (PayHelper.WXPAY.equalsIgnoreCase(payType)) {
            try {
                WxPayInfo info=new WxPayInfo();
                WxPayInfo.ResultBean resultBean = new WxPayInfo.ResultBean();
                WxPayInfo.ResultBean.WxBean wxBean = new WxPayInfo.ResultBean.WxBean();
                resultBean.setWx(wxBean);
                info.setResult(resultBean);
                WeiXinPayInfo weiXinPayInfo = (WeiXinPayInfo) data;
                info.getResult().getWx().setAppid(weiXinPayInfo.getInfo().getAppid());
                info.getResult().getWx().setPartnerid(weiXinPayInfo.getInfo().getPartnerid());
                info.getResult().getWx().setPrepayid(weiXinPayInfo.getInfo().getPrepay_id());
                info.getResult().getWx().setNoncestr(weiXinPayInfo.getInfo().getNonce_str());
                info.getResult().getWx().setTimestamp(weiXinPayInfo.getInfo().getTimestamp()+"");
                info.getResult().getWx().setPackageValue("Sign=WXPay");
                info.getResult().getWx().setSign(weiXinPayInfo.getInfo().getSign());
                callWxPay(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //微信支付
    private void callWxPay(WxPayInfo sign) {
        mPayHelper.wxPay(mContext, sign, mCallBack);
    }

    //支付宝支付
    private void callAlipay(String sign) {
        mPayHelper.aliPay(mContext, sign, mCallBack);
    }
}
