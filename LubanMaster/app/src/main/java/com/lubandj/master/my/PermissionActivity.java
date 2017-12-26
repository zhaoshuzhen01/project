package com.lubandj.master.my;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.baselibrary.BaseActivity;

/**
 * 界面中有关于权限申请的抽象类
 * Created by yangjinjian on 2017/4/1.
 */
public abstract class PermissionActivity extends BaseActivity {
    private String permission;
    private String operation;//导致申请权限的操作
    private final int RequestCode = 1628;
    public Dialog mDialog;

    public boolean checkPermission(String permissions, String operation) {
        permission = permissions;
        this.operation = operation;
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permission);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                return true;
            } else {
                return false;
            }
        } else {

        }
        return false;
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showDialogTipUserGoToAppSettting();
                } else {
                    onPermissionGranted(operation);
                }
            }
        }
    }

    public void showDialogTipUserGoToAppSettting() {
        if (mDialog != null && !mDialog.isShowing())
            mDialog.show();
    }

    // 开始提交请求权限
    public void startRequestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{permission}, RequestCode);
    }

    /**
     * 提示用户去应用设置界面手动开启权限
     *
     * @param title
     * @param content
     */
    public void setDialogTipUserGoToAppSettting(String title, String content) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialog, int which) {
                        onPermissionRefuse(operation);
                    }
                }).setCancelable(false).create();
    }

    // 跳转到当前应用的设置界面
    public void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    onPermissionGranted(operation);
                }
            }
        }
    }

    /**
     * 权限获取后的回调方法
     *
     * @param operation
     */
    public abstract void onPermissionGranted(String operation);

    /**
     * 权限获取后的回调方法
     *
     * @param operation
     */
    public abstract void onPermissionRefuse(String operation);
}
