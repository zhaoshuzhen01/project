package com.example.baselibrary.tools;

import android.widget.Toast;

import com.example.baselibrary.TApplication;

/**
 * 弹吐司的工具类
 */
public class ToastUtils {

	public static void showLong(String msg){
		Toast.makeText(TApplication.context, msg, Toast.LENGTH_LONG).show();
	}
	public static void showShort(String msg){
		Toast.makeText(TApplication.context, msg, Toast.LENGTH_SHORT).show();
	}
}
