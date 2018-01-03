package com.example.baselibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * UI相关工具类 此类包含所有与ui相关的公共方法，包括activity退出等操作
 */
public class ActUtils {
    private List<WeakReference<Activity>> mALiveActivity = null;
    private static ActUtils instance;

    public static ActUtils getInstance() {
        if (instance == null)
            instance = new ActUtils();
        return instance;
    }

    /**
     * 将activity加入集合 每一个activity启动时都需要加入集合进行统一管理
     *
     * @param activity
     * @author liaoww
     * @since 1.0
     */
    public void createActivity(Activity activity) {
        if (mALiveActivity == null) {
            mALiveActivity = new ArrayList<>(5);
        }
        if (isContains(activity) == -1) {
            mALiveActivity.add(new WeakReference<>(activity));
        }
    }

    public int isContains(Activity activity) {
        int pos = -1;
        for (int i = 0; i < mALiveActivity.size(); i++) {
            if (mALiveActivity.get(i).get() != null) {
                if (activity.getLocalClassName().equals(mALiveActivity.get(i).get().getLocalClassName())) {
                    pos = i;
                    break;
                }
            }
        }
        return pos;
    }

    /**
     * 销毁指定的activity
     *
     * @param activity
     * @sine 1.0
     * @author liaoww
     */
    public boolean destroyActivity(Activity activity) {
        if (mALiveActivity == null) {
            return false;
        }
        int i = isContains(activity);
        if (i == -1)
            return false;
        else {
            mALiveActivity.remove(i);
            return true;
        }
    }

    /**
     * 退出所有activity
     *
     * @author liaoww
     * @since 1.0
     */
    public void finishAllALiveAcitity() {
        if (mALiveActivity == null || mALiveActivity.size() < 1) {
            return;
        }
        for (WeakReference<Activity> activity : mALiveActivity) {
            if (activity.get() != null)
                activity.get().finish();
        }
        mALiveActivity = null;
    }

    /**
     * 退出App
     *
     * @author user
     * @since 1.0
     */
    public void exitApp(Context context) {
        finishAllALiveAcitity();
        System.exit(0);
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
