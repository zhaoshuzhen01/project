package com.lubandj.master;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.example.baselibrary.util.ActUtils;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.lubandj.master.been.UserInfo;
import com.lubandj.master.utils.CommonUtils;

import java.util.List;

import java.util.List;

import master.lubandj.com.loginpaylibrary.LoginUtil;

public class TApplication extends Application implements Thread.UncaughtExceptionHandler {

    public static boolean isDebug = true;
    public static String APP_NAME;
    public boolean isActive = true;
    public UserInfo mUserInfo;
    public String mCurrentCigy = "北京市";

    public static TApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LoginUtil.setWeixinConfig(context);
        if (!TextUtils.equals(getProcessName(this), "com.lubandj.customer")) {
            return;
        }

        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), CustomService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
//        String clientID = PushManager.getInstance().getClientid(getApplicationContext());
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), CusstomIntentService.class);
        String clientID = PushManager.getInstance().getClientid(getApplicationContext());
        //崩溃捕捉
        Thread.setDefaultUncaughtExceptionHandler(this);
        //百度地图
        SDKInitializer.initialize(getApplicationContext());
    }

    public void setGetuiTag(int uid) {
//        String[] tags = new String[]{uid + ""};
//        Tag[] tagParam = new Tag[tags.length];
//
//        for (int i = 0; i < tags.length; i++) {
//            Tag t = new Tag();
//            //name 字段只支持：中文、英文字母（大小写）、数字、除英文逗号以外的其他特殊符号, 具体请看代码示例
//            t.setName(tags[i]);
//            tagParam[i] = t;
//        }
//
//        int i = PushManager.getInstance().setTag(context, tagParam,
//                System.currentTimeMillis() + "");
//        String text = "设置标签失败,未知异常";
//
//        switch (i) {
//            case PushConsts.SETTAG_SUCCESS:
//                text = "设置标签成功";
//                break;
//
//            case PushConsts.SETTAG_ERROR_COUNT:
//                text = "设置标签失败, tag数量过大, 最大不能超过200个";
//                break;
//
//            case PushConsts.SETTAG_ERROR_FREQUENCY:
//                text = "设置标签失败, 频率过快, 两次间隔应大于1s";
//                break;
//
//            case PushConsts.SETTAG_ERROR_REPEAT:
//                text = "设置标签失败, 标签重复";
//                break;
//
//            case PushConsts.SETTAG_ERROR_UNBIND:
//                text = "设置标签失败, 服务未初始化成功";
//                break;
//
//            case PushConsts.SETTAG_ERROR_EXCEPTION:
//                text = "设置标签失败, 未知异常";
//                break;
//
//            case PushConsts.SETTAG_ERROR_NULL:
//                text = "设置标签失败, tag 为空";
//                break;
//
//            case PushConsts.SETTAG_NOTONLINE:
//                text = "还未登陆成功";
//                break;
//
//            case PushConsts.SETTAG_IN_BLACKLIST:
//                text = "该应用已经在黑名单中,请联系售后支持!";
//                break;
//
//            case PushConsts.SETTAG_NUM_EXCEED:
//                text = "已存 tag 超过限制";
//                break;
//
//            default:
//                break;
//        }
        Log.e("deal", "    uid  = " + uid);
        PushManager.getInstance().bindAlias(context, uid + "", CommonUtils.getUid() + "");
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        ActUtils.getInstance().exitApp(this);
    }


    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}