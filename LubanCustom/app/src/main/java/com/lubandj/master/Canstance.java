package com.lubandj.master;

/**
 * Created by ${zhaoshuzhen} on 2017/12/3.
 */

public class Canstance {
    public static String CITY = "";
    public static final int TYPE_LIST_ALL = 0;//所有消息
    public static final int TYPE_LIST_UNFINISH = 0;//工单列表未完成
    public static final int TYPE_LIST_FINISH = 1;//工单列表已完成
    public static final int TYPE_LIST_CANCELED = 2;//工单列表已取消

    public static final int TYPE_WORKCALENDAR = 5;//工作日历跳转

    public static String VERSION = "v1.0";

    public static final int HTTP_SEND_CODE = 90001;//发短信
    public static final int HTTP_LOGIN = 10002;//登陆
    public static final int HTTP_GETINFO = 10003;//用户信息
    public static final int HTTP_UPLOAD_PHOTO = 10005;//上传图片
    public static final int HTTP_MODIFYPHONE = 10004;//绑定新手机
    public static final int HTTP_GETADDRESS = 10006;//获取住址
    public static final int HTTP_SAVEADDRESS = 10007;//保存住址
    public static final int HTTP_DELETEADDRESS = 10008;//删除住址
    public static final int HTTP_QRCODE = 10008;//二维码
    public static final int HTTP_WORK_SHEET_DETAILS = 40003;//订单详情
    public static final int HTTP_SERVICE_DETAILS = 20002;//服务详情

    public static final int HTTP_WORK_SHEET_LIST = 20001;//服务列表
    public static final int HTTP_ADD_CAR = 30001;//加入购物车
    public static final int HTTP_UPDATA_CAR = 30002;//更新购物车
    public static final int HTTP_LIST_CAR = 30003;//购物车列表
    public static final int HTTP_DELET_CAR = 30004;//清空购物车
    public static final int HTTP_CLEAR_CAR = 30005;//清空购物车
    public static final int HTTP_ADD_ADDRESS = 30001;//加入购物车
    public static final int HTTP_CITY_LIST = 20003;//城市列表

    public static final int HTTP_WEIXIN_LOGIN = 70001;//微信登录
    public static final int Http_WEIXIN_BINDING = 70002;//微信绑定手机号
    public static final int HTTP_WORK_SHEET_UPDATE_STATUS = 40002;//工单列表
    public static final int HTTP_SIGN_EXCEPTION = 20004;//标记异常
    public static final int HTTP_EXCEPTION_LIST = 80001;//异常列表
    public static final int HTTP_GUANGAO_LIST = 80003;//广告列表
    public static final int HTTP_GUANGAO_START = 80002;//启动页广告

    public static final int HTTP_BOOK_ORDER = 40001;//预约下单
    public static final int HTTP_PAY_ORDER = 40005;//支付订单
    public static final int HTTP_MSGCENTER_LIST = 40001;//消息列表
    public static final int HTTP_COUSPS_LIST = 50004;//我的优惠券列表

    public static final int HTTP_PINGJIA_LIST = 60003;//评价列表

    public static final int HTTP_WORKDETAIL = 60001;//工作日历详情
    public static final int HTTP_LEAVERECORD = 50002;//请假列表
    public static final int HTTP_LEAVEREQUEST = 50001;//请假提交

    public static final int HTTP_MODIFY_INFO=10009;//修改用户信息
    public static final int HTTP_UPGRADE = 20011;//更新

    public static final int HTTP_CANCELORDERINFO = 40007;//取消订单详情

//  sp constant

    public static final String KEY_SP_PHONE_NUM = "sp_phone_num";
    public static final String KEY_SP_USER_INFO = "sp_user_info";


    //SharePrefrense 参数
    public static final String DEVIED_ID = "DEVICEID";
    public static final String TOKEN = "TOKEN";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_ID = "address_id";
    public static final String UID = "UID";
    public static final String MSG = "MSG";
    public static final String FIRSTSTART = "first_start";

    public static final String KEY_SHEET_STATUS_TO_PERFORM = "1";
    public static final String KEY_SHEET_STATUS_ON_ROAD = "2";
    public static final String KEY_SHEET_STATUS_IN_SERVICE = "3";
    public static final String KEY_SHEET_STATUS_COMPLETED = "4";
    public static final String KEY_SHEET_STATUS_CANCELED = "6";


    public static final int TYPE_ORDER_DETAILS_CANCELED = 0; //订单未知
    public static final int TYPE_ORDER_DETAILS_IN_THE_SINGLE = 1;//待指派
    public static final int TYPE_ORDER_DETAILS_COMPLETED = 2;//待服务
    public static final int TYPE_ORDER_DETAILS_PAY_OVERTIME = 3;//正在上门
    public static final int TYPE_ORDER_DETAILS_NO_PAYMENT = 4;//服务中
    public static final int TYPE_ORDER_DETAILS_WAIT_SERVICE = 5;//已完成
    public static final int TYPE_ORDER_DETAILS_IN_SERVICE = 7;//取消订单
    public static final String TYPE_ORDER_DETAILS_IN_PAY = "8";//确认支付





}
