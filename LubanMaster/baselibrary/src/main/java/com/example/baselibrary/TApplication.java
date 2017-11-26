package com.example.baselibrary;

import android.app.Application;
import android.content.Context;

public class TApplication extends Application {

    public static boolean isDebug = true ;
    public static String APP_NAME  ;

    public static Context context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}