package com.lubandj.master.pay;

import java.util.Map;

/**
 * Created by xts on 2017/8/7.
 */

public interface IPayResultCallback {
    void payResult(Map<String, String> result, String payType);
}
