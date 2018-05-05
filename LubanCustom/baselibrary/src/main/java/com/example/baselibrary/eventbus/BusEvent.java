package com.example.baselibrary.eventbus;

/**
 * Created by ${zhaoshuzhen} on 2018/1/11.
 */

public class BusEvent {
    public final static int IMG_CODE = 1 ;//上传图片
    public final static int NOTIFY_CODE = 2;//通知
    public final static int LOGIN_OUT = 3;//退出登陆
    public final static int PINGLUN_CHENEGG = 4;//评论成功

    private int code ;

    public BusEvent(int code){
        this.code = code;
    }

    public int getCode(){
        return code ;
    }
}
