package com.lubandj.master.baiduUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.ActionSheetDialog;
import com.lubandj.master.utils.OpenLocalMapUtil;

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
        try {
            intent = isBaiduMap ? Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content =&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end") :
                    Intent.getIntent("androidamap://viewMap?sourceApplication=鹿班&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
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


    /**
     * 打开百度地图
     */
    private void openBaiduMap(Context context,double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled()) {
            try {
                String uri = OpenLocalMapUtil.getBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                        String.valueOf(dlat), String.valueOf(dlon), dname, city, "");
                Intent intent = Intent.parseUri(uri, 0);
                context.startActivity(intent); //启动调用
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort(context, "请安装百度地图");
        }
    }


    /**
     * 打开高德地图
     */
    private void openGaoDeMap(Context context,double slat, double slon, String sname, double dlat, double dlon, String dname) {
        if (OpenLocalMapUtil.isGdMapInstalled()) {
            try {
                String uri = OpenLocalMapUtil.getGdMapUri("鹿班", String.valueOf(slat), String.valueOf(slon),
                        sname, String.valueOf(dlat), String.valueOf(dlon), dname);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.autonavi.minimap");
                intent.setData(Uri.parse(uri));
                context.startActivity(intent); //启动调用
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort(context, "请安装高德地图");
        }
    }
}
