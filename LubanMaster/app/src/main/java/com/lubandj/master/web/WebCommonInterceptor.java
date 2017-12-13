package com.lubandj.master.web;

import android.content.Context;
import android.util.Log;

import com.example.baselibrary.net.CommonInterceptor;
import com.example.baselibrary.tools.Tools;

/**
 * Created by ${zhaoshuzhen} on 2017/8/29.
 */

public class WebCommonInterceptor extends CommonInterceptor {

    public WebCommonInterceptor(Context context){
       token = "U5FAFquVTM9oF1yxWB8OpU5FAFquVTM9oF1yxWB8Op";
//        token = ToolsPreferences.getPreferences(ToolsPreferences.TOKEN);
        Log.e("upload","token  111="+token);
        version = Tools.getVersionName(context);

        deviceId = "7878-uiui-1231-21312";
    }
}
