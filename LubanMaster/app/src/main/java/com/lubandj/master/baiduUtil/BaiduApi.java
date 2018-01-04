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
    private static BaiduApi baiduApi = null;

    private BaiduApi() {
    }

    public static BaiduApi getBaiduApi() {
        if (baiduApi == null) {
            synchronized (BaiduApi.class) {
                if (baiduApi == null) {
                    baiduApi = new BaiduApi();
                }
            }
        }
        return baiduApi;
    }

    /**
     * 导航
     *
     * @param address
     */
    public void baiduNavigation(final Context context, final String address) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("高德地图",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openMap(context,false, address);
                            }
                        })
                .addSheetItem("百度地图",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openMap(context,true, address);
                            }
                        }).show();
    }

    private void openMap(Context context,boolean isBaiduMap, String address) {
        if (!checkApkExist(context, isBaiduMap ? "com.baidu.BaiduMap" : "com.autonavi.minimap")) {
            ToastUtils.showShort(context, isBaiduMap ? "请安装百度地图" : "请安装高德地图");
            return;
        }
        Intent intent = null;
        String baiDuUri="baidumap://map/marker?location=%1$s,%2$s&title=%3$s&content=%4$s&traffic=on";
        String baiDuUri2="baidumap://map/geocoder?src=openApiDemo&address=北京市海淀区上地信息路9号奎科科技大厦";
        String baiDuUri3="baidumap://map/geocoder?location=40.047669,116.313082";

        String gaoDeUri="androidamap://viewMap?sourceApplication=鹿班&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0";
        String gaoDeUri2="androidamap://keywordNavi?sourceApplication=鹿班&keyword=百度奎科大厦&style=2";
        try {
            intent = isBaiduMap ? Intent.getIntent(String.format(baiDuUri,"40.047669","116.313082",address,address)) :
                    Intent.getIntent(gaoDeUri2);
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
