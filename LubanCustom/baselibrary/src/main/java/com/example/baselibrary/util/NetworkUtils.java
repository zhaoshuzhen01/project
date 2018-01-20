package com.example.baselibrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.example.baselibrary.tools.ToastUtils;

/**
 * @创建人：zhaoshuzhen
 * @创建时间：2015/12/8 10:10
 */
public class NetworkUtils {
    /**
     * 判断网络是否存在
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            ToastUtils.showShort(context, "网络未连接");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == State.CONNECTED
                            || info[i].getState() == State.CONNECTING) {
                        return true;
                    }
                }
            }
        }
        ToastUtils.showShort(context, "网络未连接");
        return false;
    }

    public static boolean iswify(Context context) {
        //获取系统服务
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取状态
        State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //判断wifi已连接的条件
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return true;
        }
        return false;
    }
}
