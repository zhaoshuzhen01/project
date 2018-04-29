package com.lubandj.master.my;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.baselibrary.eventbus.BusEvent;
import com.example.baselibrary.eventbus.RxBus;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.example.baselibrary.util.PhotoUtil;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.UserInfo;
import com.lubandj.master.databinding.ActivityMysettingBinding;
import com.lubandj.master.dialog.DoubleSelectDialog;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.httpbean.GetAddressReponse;
import com.lubandj.master.httpbean.UidParamsRequest;
import com.lubandj.master.httpbean.UploadPhotoReponse;
import com.lubandj.master.httpbean.UploadPhotoRequest;
import com.lubandj.master.login.LoginActivity;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.NetworkUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import java.io.File;

/**
 * function:设置界面
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class MySettingActivity extends PermissionActivity {
    private ActivityMysettingBinding binding;
    private ImageLoader imageLoader;
    private AddressBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mysetting);
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());

        loadFace();
        UserInfo info = TApplication.context.mUserInfo;
        binding.tvSettingName.setText(info.nickname);
        binding.tvSettingNum.setText(info.uuid);
        setPhone();
        getAddress();

        setResult(RESULT_CANCELED);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }


    /**
     * 头像
     *
     * @param view
     */
    public void onHeadPhoto(View view) {
        DoubleSelectDialog dialog = new DoubleSelectDialog(MySettingActivity.this, "拍照", "从手机相册选择", new DoubleSelectDialog.DoubleClickListenerInterface() {
            @Override
            public void doFirstClick() {
                PhotoUtil.getInstance().takePhoto(MySettingActivity.this);
            }

            @Override
            public void doSecondClick() {
                PhotoUtil.getInstance().pickPhoto(MySettingActivity.this);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 修改电话
     *
     * @param view
     */
    public void onModifyPhone(View view) {
        startActivityForResult(new Intent(MySettingActivity.this, ModifyPhoneActivity.class), 1001);
    }

    /**
     * 修改地址
     *
     * @param view
     */
    public void onMyAddress(View view) {
        Intent intent = new Intent(MySettingActivity.this, MyAddressActivity.class);
        intent.putExtra("address", mBean);
        startActivityForResult(intent, 100);
    }

    /**
     * 退出登录
     *
     * @param view
     */
    public void onLogout(View view) {
        TipDialog outDialog = new TipDialog(MySettingActivity.this);
        outDialog.setNoPomptTitle();
        outDialog.setTextDes("退出登录");
        outDialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                CommonUtils.logOut(MySettingActivity.this);
                dialog.dismiss();

            }
        });
        outDialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
            }
        });
        outDialog.setCancelable(false);
        outDialog.setCanceledOnTouchOutside(false);
        outDialog.show();
    }

    public void onAbout(View view) {
        startActivity(AboutLuBanActivity.class, null);
    }

    @Override
    public void onPermissionGranted(String operation) {
        if (operation.equals("camera")) {
            onHeadPhoto(null);
        }
    }

    @Override
    public void onPermissionRefuse(String operation) {
        if (operation.equals("camera")) {
            ToastUtils.showShort(MySettingActivity.this, "获取拍照权限失败！");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == PhotoUtil.TAKE_PICTURE) {
            PhotoUtil.getInstance().dealPhoto(this, data, true);
        } else if (requestCode == PhotoUtil.SELECT_PIC_BY_PICK_PHOTO) {
            PhotoUtil.getInstance().dealPhoto(this, data, false);
        } else if (requestCode == 200) {
            final File tempFile = new File(PhotoUtil.getSdcardPath(), "lubancrop.jpg");
            if (tempFile.exists()) {
                Bitmap headPhoto = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                binding.ivSetHeadimg.setImageBitmap(headPhoto);
                if (NetworkUtils.isNetworkAvailable(MySettingActivity.this)) {
                    initProgressDialog("正在上传头像...").show();
                    UploadPhotoRequest bean = new UploadPhotoRequest(CommonUtils.Bitmap2StrByBase64(headPhoto));
                    TaskEngine.getInstance().tokenHttps(Canstance.HTTP_UPLOAD_PHOTO, bean, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String s) {
                            dialog.dismiss();
                            UploadPhotoReponse response = new UploadPhotoReponse();
                            response = (UploadPhotoReponse) CommonUtils.generateEntityByGson(MySettingActivity.this, s, response);
                            if (response != null) {
                                RxBus.getInstance().post(new BusEvent(BusEvent.IMG_CODE));
                                TApplication.context.mUserInfo.face_url = response.info.face_url;
                                loadFace();
                                PhotoUtil.getInstance().deleteCache();
                                tempFile.delete();
                                
                                setResult(RESULT_OK);
                                ToastUtils.showShort(MySettingActivity.this, "上传成功");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            dialog.dismiss();
                            CommonUtils.fastShowError(MySettingActivity.this, volleyError);
                        }
                    });
                }
            } else {
                ToastUtils.showShort(MySettingActivity.this, "剪切图片出错");
            }
        } else if (requestCode == 1001) {
            setPhone();
        } else if (requestCode == 100) {
            mBean = (AddressBean) data.getSerializableExtra("data");
            if (mBean.housing_estate != null)
                binding.tvSettingAddress.setText(mBean.housing_estate + "");
        }
    }

    public void setPhone() {
        binding.tvSettingPhone.setText(TApplication.context.mUserInfo.mobile);
    }

    public void loadFace() {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(binding.ivSetHeadimg, R.drawable.default_header, R.drawable.default_header);
        if (!TextUtils.isEmpty(TApplication.context.mUserInfo.face_url))
            imageLoader.get(TApplication.context.mUserInfo.face_url, imageListener);
    }

    public void getAddress() {
        initProgressDialog("获取地址中...").show();
        UidParamsRequest request = new UidParamsRequest(CommonUtils.getUid());
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETADDRESS, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                GetAddressReponse reponse = new GetAddressReponse();
                reponse = (GetAddressReponse) CommonUtils.generateEntityByGson(MySettingActivity.this, s, reponse);
                if (reponse != null) {
                    mBean = reponse.info;
                    if (mBean.housing_estate != null)
                        binding.tvSettingAddress.setText(mBean.housing_estate + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(MySettingActivity.this, volleyError);
            }
        });
    }
}