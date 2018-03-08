package com.lubandj.master.been;

import com.lubandj.master.httpbean.BaseEntity;

/**
 * Created by ${zhaoshuzhen} on 2018/3/8.
 */

public class AlipayBeen extends BaseEntity {

    /**
     * info : alipay_sdk=alipay-sdk-php-20161101&amp;app_id=2018012302042230&amp;biz_content=%7B%22body%22%3A%22%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22subject%22%3A+%22%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A+%2220180308103727555410%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2210.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&amp;charset=UTF-8&amp;format=json&amp;method=alipay.trade.app.pay&amp;notify_url=https%3A%2F%2Fapi.luban.eme.net.cn%2Falipay_notify&amp;sign_type=RSA2&amp;timestamp=2018-03-08+10%3A47%3A01&amp;version=1.0&amp;sign=aFalny9YUq%2FyCpigWzwgXTo%2FJGuQA6dz9k6cCvjreftiUb%2Bwmd3mEQBdeRA6R5esx9rKkRwwHBkQ75OoytjxMYQj9SqiGt9gFzs%2FdIu55MdB0kYwy4OWOTF7zVgwyyn9p%2BrAaClYDv%2FxObDx1FmV8Xv2eM89o0f1xGS9oKtPK112h0Qjl6PubyV5izf8u%2FkJ5c9B0AbFD1cNQDmeRQPXmp8hjaPY4VfMil6Pn%2FMWPpes8DR%2Fn0NTjRcrDxfqeNNXyRgt4dubJ70t96rryXKRFsstJkQjGZeMD%2B88zxidUcg9%2FwGb46aIw53jE0t03uWZfk%2FY8ykK0STTWc6h9iNFaQ%3D%3D
     */

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
