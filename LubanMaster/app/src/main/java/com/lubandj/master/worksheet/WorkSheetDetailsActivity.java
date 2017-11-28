package com.lubandj.master.worksheet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.R;

import java.net.URISyntaxException;

public class WorkSheetDetailsActivity extends TitleBaseActivity {


    public static final String KEY_DETAILS_TYPE = "details_type";
    private static final String TAG = "WorkSheetDetailsActivit";
    private static final int TYPE_TO_PERFORM = 0;
    private static final int TYPE_ON_ROAD = 1;
    private static final int TYPE_IN_SERVICE = 2;
    private static final int TYPE_COMPLETED = 3;
    private static final int TYPE_CANCELED = 4;


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_details;
    }

    @Override
    public void initView() {
        setTitleText(R.string.txt_work_sheet_details_title);
        setOKText("客服");
        int currentType = getIntent().getIntExtra(KEY_DETAILS_TYPE, TYPE_TO_PERFORM);
        Log.e(TAG, "initView: " + currentType);
        switch (currentType) {
            case TYPE_TO_PERFORM:
                break;
            case TYPE_ON_ROAD:
                break;
            case TYPE_IN_SERVICE:
                break;
            case TYPE_COMPLETED:
                break;
            case TYPE_CANCELED:
                break;
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
//                new ActionSheetDialog(this)
//                        .builder()
//                        .setCancelable(true)
//                        .setCanceledOnTouchOutside(true)
//                        .addSheetItem("发送给好友",
//                                ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//                                        //填写事件
//                                    }
//                                })
//                        .addSheetItem("转载到空间相册",
//                                ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//                                        //填写事件
//                                    }
//                                })
//                        .addSheetItem("上传到群相册",
//                                ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//                                        //填写事件
//                                    }
//                                })
//                        .addSheetItem("保存到手机",
//                                ActionSheetDialog.SheetItemColor.Blue,
//                                new ActionSheetDialog.OnSheetItemClickListener() {
//                                    @Override
//                                    public void onClick(int which) {
//                                        //填写事件
//                                    }
//                                }).show();

                new AlertDialog(this)
                        .builder()
                        .setTitle("退出当前帐号")
                        .setMsg("再连续登陆天，就可变身为QQ达人。退出QQ可能会使你现有记录归零，确定退出？")
                        .setPositiveButton("确认退出", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //填写事件
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //填写事件
                            }
                        }).show();
                break;
            default:
                break;
        }

    }


    private void openMap(boolean isBaiduMap) {
        if (!checkApkExist(this, isBaiduMap ? "com.baidu.BaiduMap" : "com.autonavi.minimap")) {
            Log.e(TAG, "isBaiduMap:" + isBaiduMap);
            return;
        }
        Intent intent = null;
        try {
            intent = isBaiduMap ? Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content =百度奎科大厦&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end") :
                    Intent.getIntent("androidamap://viewMap?sourceApplication=厦门通&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        startActivity(intent); //启动调用

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


    @SuppressLint("MissingPermission")
    private void callPhone(String num) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        startActivity(intent);
    }

    public void onClickCopy(String selectedText) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, selectedText));
        toast("复制成功");
    }
}
