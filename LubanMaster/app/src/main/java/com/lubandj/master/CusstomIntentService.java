package com.lubandj.master;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.baselibrary.eventbus.RxBus;
import com.example.baselibrary.tools.NotificationUtil;
import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.lubandj.master.InstanceUtil.NotifyMsgInstance;
import com.lubandj.master.activity.MsgCenterActivity;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import org.json.JSONObject;

/**
 * Created by ${zhaoshuzhen} on 2017/12/3.
 */

public class CusstomIntentService extends GTIntentService {
    public static final String TAG = "deal";
    /**
     * 为了观察透传数据变化.
     */
    private static int cnt;
    public CusstomIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);


        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            shownotification(data,context);
//            NotificationUtil.initNotification(this);
        }
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e("deal",cmdMessage.getAppid()+"");
    }
    // 推送通知栏设置
    public void shownotification(String msg,Context context) {
        CommonUtils.setMsgCount(1);
        MsgCenterBeen.InfoBean.ListBean listBean = new Gson().fromJson(msg,MsgCenterBeen.InfoBean.ListBean.class);
        NotifyMsgInstance.getInstance().addNotifyBeens(listBean);
        RxBus.getInstance().post(new MsgCenterBeen());
        // 消息存储
//        MeBill meBill = new Gson().fromJson(msg, MeBill.class);
       /* SQLiteDatabase w_db = FaceDataBase.getInstance()
                .getFaceWritableDatabase(context,
                        FaceDataBaseHelper.MESSAGE_TABLE_NAME);
        meBill.setIsRead("0");
        meBill.setUpdate_time(System.currentTimeMillis() + "");
        meBill.setPhoneNum(UserMsg.getInstance().getD_id());
        FaceDataBase.getInstance().insertMsg(w_db, meBill);

        BusProvider.getInstance().post(
                new DataChangedEvent(BusAction.CHANGED_MSG_DATABASE));*/
        // 点击事件设置
        Intent appIntent = new Intent(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setClass(context, MsgCenterActivity.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

       /* Bundle bundle = new Bundle();
        bundle.putSerializable(Intents.MSG_OBJECT, meBill);
        appIntent.putExtras(bundle);*/

        // 通知生成
        NotificationManager barmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.icon;

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(icon).setTicker("您有新的消息要查看");

        String title = listBean.getTitle();
        String text = listBean.getContent();
        builder.setContentTitle(title).setContentText(text)
                .setContentIntent(contentIntent);

        Notification notice = builder.getNotification();
        notice.flags = Notification.FLAG_AUTO_CANCEL;
        notice.defaults |= Notification.DEFAULT_SOUND;// 声音
        // messageNotification.defaults |= Notification.DEFAULT_LIGHTS;//灯
        notice.defaults |= Notification.DEFAULT_VIBRATE;// 震动

        barmanager.notify(System.currentTimeMillis() + "", 2008, notice);

    }


}