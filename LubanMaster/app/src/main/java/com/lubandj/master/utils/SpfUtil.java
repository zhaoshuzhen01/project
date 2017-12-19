package com.lubandj.master.utils;

import android.app.TabActivity;
import android.content.Context;
import android.content.SharedPreferences;

import com.lubandj.master.TApplication;

public class SpfUtil {
    public static String USER_STATE = "userstate";//值 0-未登录 1-已登录 2-已认证

    //share数据保存
    private static SpfUtil catche;
    private final String SHARED_PREFERENCE_NAME = "com.lubandj.master";
    private SharedPreferences spf;

    public SpfUtil(Context context) {
        spf = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public static SpfUtil instance() {
        if (catche == null) {
            catche = new SpfUtil(TApplication.context);
        }
        return catche;
    }

    public void putBoolean(String key, boolean value) {
        spf.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return spf.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        spf.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return spf.getString(key, "");
    }

    public void putInt(String key, int value) {
        spf.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        spf.edit().putLong(key, value).commit();
    }

    public int getInt(String key) {
        return spf.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return spf.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return spf.getLong(key, 0);
    }

    public long getLong(String key, long def) {
        return spf.getLong(key, def);
    }

    public void clearData() {
        spf.edit().clear().commit();
    }

    public void remove(String key) {
        spf.edit().remove(key).commit();
    }

    public void commit() {
        spf.edit().commit();
    }

}
