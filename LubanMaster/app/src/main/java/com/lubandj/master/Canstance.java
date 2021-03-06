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
    public static final int TYPE_LIST_ALL = 0;//所有消息
    public static final int TYPE_LIST_UNFINISH = 1;//工单列表未完成
    public static final int TYPE_LIST_FINISH = 2;//工单列表已完成
    public static final int TYPE_LIST_CANCELED = 3;//工单列表已取消

    public static final int TYPE_WORKCALENDAR = 5;//工作日历跳转

    public static String VERSION = "v1.0";

    public static final int HTTP_SEND_CODE = 90001;//发短信
    public static final int HTTP_LOGIN = 10002;//登陆
    public static final int HTTP_GETINFO = 10003;//用户信息
    public static final int HTTP_UPLOAD_PHOTO = 10005;//上传图片
    public static final int HTTP_MODIFYPHONE = 10004;//绑定新手机
    public static final int HTTP_GETADDRESS = 10006;//获取住址
    public static final int HTTP_SAVEADDRESS = 10007;//保存住址
    public static final int HTTP_QRCODE = 10008;//二维码
    public static final int HTTP_WORK_SHEET_DETAILS = 20002;//工单详情
    public static final int HTTP_WORK_SHEET_LIST = 20001;//工单列表
    public static final int HTTP_WORK_SHEET_UPDATE_STATUS = 20003;//工单列表更新状态
    public static final int HTTP_SIGN_EXCEPTION = 20004;//标记异常
    public static final int HTTP_EXCEPTION_LIST = 80001;//异常列表
    public static final int HTTP_MSGCENTER_LIST = 40001;//消息列表
    public static final int HTTP_WORKDETAIL = 60001;//工作日历详情
    public static final int HTTP_LEAVERECORD = 50002;//请假列表
    public static final int HTTP_LEAVEREQUEST = 50001;//请假提交
    public static final int HTTP_UPGRADE = 20011;//更新

//  sp constant

    public static final String KEY_SP_PHONE_NUM = "sp_phone_num";
    public static final String KEY_SP_USER_INFO = "sp_user_info";


    //SharePrefrense 参数
    public static final String DEVIED_ID = "DEVICEID";
    public static final String TOKEN = "TOKEN";
    public static final String UID = "UID";
    public static final String MSG = "MSG";


    public static final String KEY_SHEET_STATUS_TO_PERFORM = "1";
    public static final String KEY_SHEET_STATUS_ON_ROAD = "2";
    public static final String KEY_SHEET_STATUS_IN_SERVICE = "3";
    public static final String KEY_SHEET_STATUS_COMPLETED = "4";
    public static final String KEY_SHEET_STATUS_CANCELED = "6";
}
