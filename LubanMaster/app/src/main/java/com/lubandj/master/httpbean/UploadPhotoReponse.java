package com.lubandj.master.httpbean;

import com.lubandj.master.been.AddressBean;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-18
 * company:九州宏图
 */

public class UploadPhotoReponse extends BaseResponseBean {
    public UplooadPhoto info;

    public class UplooadPhoto {
        public String face_url;
    }
}
