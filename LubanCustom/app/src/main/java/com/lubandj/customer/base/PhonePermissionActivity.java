package com.lubandj.customer.base;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author: lj
 * @Time: 2017/12/25 14:53
 * @Description: This is PhonePermissionActivity
 */
@RuntimePermissions
public abstract class PhonePermissionActivity extends TitleBaseActivity {



    public void callToClient(final String phoneNum,String msg) {
        new AlertDialog(this)
                .builder()
                .setMsg(msg)
                .setPositiveButton(getString(R.string.txt_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        call(phoneNum);
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }


    public void call(String num){
        PhonePermissionActivityPermissionsDispatcher.callPhoneWithPermissionCheck(this,num);

    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callPhone(String num) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        startActivity(intent);
    }


    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void showRationaleForCall(final PermissionRequest request) {// 提示用户权限使用的对话框
        new AlertDialog(this)
                .builder()
                .setMsg(getString(R.string.txt_is_open_permission))
                .setPositiveButton(getString(R.string.txt_allow), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request.proceed();
                    }
                })
                .setNegativeButton(getString(R.string.txt_reject), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request.cancel();
                    }
                }).show();
    }

    /**
     * 如果用户拒绝该权限执行的方法
     */
    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    void showDeniedForCall() {
        ToastUtils.showShort(this,R.string.txt_permission_fail);
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void showNeverAskForCall() {
        ToastUtils.showShort(this,R.string.txt_go_setting);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhonePermissionActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    public void copy(String selectedText) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, selectedText));
        ToastUtils.showShort(this, R.string.txt_copy_success);
    }
}
