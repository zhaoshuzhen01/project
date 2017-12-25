package com.lubandj.master.httpbean;

import com.lubandj.master.been.AddressBean;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-18
 * company:九州宏图
 */

public class GetQrcodeReponse extends BaseResponseBean {
    public Qrcode info;

    public class Qrcode {
        public String uuid;
        public String link;
        public String qrcode;
    }
}
