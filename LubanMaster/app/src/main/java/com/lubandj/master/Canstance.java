package com.lubandj.master;

/**
 * Created by ${zhaoshuzhen} on 2017/12/3.
 */

public class Canstance {
    public static final int TYPE_TO_PERFORM = 0;//执行中
    public static final int TYPE_ON_ROAD = 1;//正在上门
    public static final int TYPE_IN_SERVICE = 2;//服务中
    public static final int TYPE_COMPLETED = 3;//完成
    public static final int TYPE_CANCELED = 4;//取消

    public static final int TYPE_WORKCALENDAR = 5;//工作日历跳转


    public static final int HTTP_SEND_CODE = 90001;//发短信
    public static final int HTTP_LOGIN = 10002;//登陆
    public static final int HTTP_UPLOAD_PHOTO = 10005;//上传图片



//  sp constant

    public static final String KEY_SP_PHONE_NUM = "sp_phone_num";
    public static final String KEY_SP_USER_INFO = "sp_user_info";

}
