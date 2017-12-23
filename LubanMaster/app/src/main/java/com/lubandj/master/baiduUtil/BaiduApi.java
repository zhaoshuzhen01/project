package com.lubandj.master.baiduUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.ActionSheetDialog;

import java.net.URISyntaxException;

/**
 * Created by ${zhaoshuzhen} on 2017/12/3.
 */

public class BaiduApi {
    private static BaiduApi baiduApi = null ;
    private Context context ;
    private BaiduApi(Context context){
        this.context = context ;
    }
    public static BaiduApi getBaiduApi(Context context){
        if (baiduApi==null){
            synchronized (BaiduApi.class){
                if (baiduApi==null){
                    baiduApi = new BaiduApi(context);
                }
            }
        }
        return baiduApi;
    }

    /**
     * 导航
     * @param address
     */
    public void baiduNavigation(final String address){
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("高德地图",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openMap(false,address);
                            }
                        })
                .addSheetItem("百度地图",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openMap(true,address);
                            }
                        }).show();
    }
    private void openMap(boolean isBaiduMap,String address) {
        if (!checkApkExist(context, isBaiduMap ? "com.baidu.BaiduMap" : "com.autonavi.minimap")) {
            ToastUtils.showShort(context,isBaiduMap ? "请安装百度地图" : "请安装高德地图");
            return;
        }
        Intent intent = null;
        try {
            intent = isBaiduMap ? Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content =&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end") :
                    Intent.getIntent("androidamap://viewMap?sourceApplication=厦门通&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        context.startActivity(intent); //启动调用

    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
