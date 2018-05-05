package com.lubandj.master.httpbean;

/**
 * function:
 * author:yangjinjian
 * date: 2018-5-2
 * company:九州宏图
 */

public class UpgradeAppResponse extends BaseResponseBean {
    public UpgradeApp info;

    public class UpgradeApp {
        public String version;
        public String des;
        public String url;
    }
}
