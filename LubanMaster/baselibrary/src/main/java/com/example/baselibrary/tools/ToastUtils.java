package com.example.baselibrary.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * 弹吐司的工具类
 */
public class ToastUtils {

    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
