package com.lubandj.master;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.lubandj.master.been.UserInfo;

public class TApplication extends Application {

    public static boolean isDebug = true;
    public static String APP_NAME;
    public boolean isActive = true;
    public UserInfo mUserInfo;

    public static TApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), CustomService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), CusstomIntentService.class);
        String clientID = PushManager.getInstance().getClientid(getApplicationContext());
        String[] tags = new String[]{"45"};
        Tag[] tagParam = new Tag[tags.length];

        for (int i = 0; i < tags.length; i++) {
            Tag t = new Tag();
            //name 字段只支持：中文、英文字母（大小写）、数字、除英文逗号以外的其他特殊符号, 具体请看代码示例
            t.setName(tags[i]);
            tagParam[i] = t;
        }

        int i = PushManager.getInstance().setTag(context, tagParam,
                System.currentTimeMillis() + "");
        String text = "设置标签失败,未知异常";

        switch (i) {
            case PushConsts.SETTAG_SUCCESS:
                text = "设置标签成功";
                break;

            case PushConsts.SETTAG_ERROR_COUNT:
                text = "设置标签失败, tag数量过大, 最大不能超过200个";
                break;

            case PushConsts.SETTAG_ERROR_FREQUENCY:
                text = "设置标签失败, 频率过快, 两次间隔应大于1s";
                break;

            case PushConsts.SETTAG_ERROR_REPEAT:
                text = "设置标签失败, 标签重复";
                break;

            case PushConsts.SETTAG_ERROR_UNBIND:
                text = "设置标签失败, 服务未初始化成功";
                break;

            case PushConsts.SETTAG_ERROR_EXCEPTION:
                text = "设置标签失败, 未知异常";
                break;

            case PushConsts.SETTAG_ERROR_NULL:
                text = "设置标签失败, tag 为空";
                break;

            case PushConsts.SETTAG_NOTONLINE:
                text = "还未登陆成功";
                break;

            case PushConsts.SETTAG_IN_BLACKLIST:
                text = "该应用已经在黑名单中,请联系售后支持!";
                break;

            case PushConsts.SETTAG_NUM_EXCEED:
                text = "已存 tag 超过限制";
                break;

            default:
                break;
        }
        Log.e("deal", text + "");
        PushManager.getInstance().bindAlias(context, clientID);
    }
}