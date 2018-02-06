package com.lubandj.customer.my;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.baselibrary.eventbus.BusEvent;
import com.example.baselibrary.eventbus.RxBus;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.NetworkUtils;
import com.example.baselibrary.util.PhotoUtil;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.UserInfo;
import com.lubandj.master.databinding.ActivityMysettingBinding;
import com.lubandj.master.dialog.DoubleSelectDialog;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.httpbean.UploadPhotoReponse;
import com.lubandj.master.httpbean.UploadPhotoRequest;
import com.lubandj.master.my.PermissionActivity;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

/**
 * function:设置界面
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class MySettingActivity extends PermissionActivity {
    private ActivityMysettingBinding binding;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mysetting);
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());

        refershInfo();
        setResult(RESULT_CANCELED);
    }

    private void refershInfo() {
        UserInfo info = TApplication.context.mUserInfo;
        if (info != null) {
            if (TextUtils.isEmpty(info.nickname)) {
                binding.tvSettingNickname.setText(info.nickname);
            } else {
                binding.tvSettingNickname.setText(info.mobile);
            }
            binding.tvSettingSex.setText("选择");
            binding.tvSettingWxaccount.setText("未绑定");
            loadFace();
            setPhone();
        }
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


        DoubleSelectDialog dialog = new DoubleSelectDialog(MySettingActivity.this, "拍照", "从相册选择", new DoubleSelectDialog.DoubleClickListenerInterface() {
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
     * 退出登录
     *
     * @param view
     */
    public void onLogout(View view) {
        TipDialog outDialog = new TipDialog(MySettingActivity.this);
        outDialog.setNoPomptTitle();
        outDialog.setTextDes("您确认要退出登录吗?");
        outDialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
//                CommonUtils.logOut(MySettingActivity.this);
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
            Bundle bundle = data.getExtras();
            Bitmap headPhoto = (Bitmap) bundle.get("data");
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
        } else if (requestCode == 1001) {
            setPhone();
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

    /**
     * 更改微信账户
     *
     * @param view
     */
    public void onChangeWxAccount(View view) {
        TipDialog outDialog = new TipDialog(MySettingActivity.this);
        outDialog.setNoPomptTitle();
        if ("未绑定".equals(binding.tvSettingWxaccount.getText().toString())) {
            outDialog.setTextDes("\"鹿班\"想打开\"微信\"");
            outDialog.setButton1("打开", new TipDialog.DialogButtonOnClickListener() {
                @Override
                public void onClick(View button, TipDialog dialog) {
                    binding.tvSettingWxaccount.setText("luili");
                    dialog.dismiss();

                }
            });
            outDialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
                @Override
                public void onClick(View button, TipDialog dialog) {
                    dialog.dismiss();
                }
            });
        } else {
            outDialog.setTextDes("确认要解除绑定微信吗？");
            outDialog.setButton1("确认", new TipDialog.DialogButtonOnClickListener() {
                @Override
                public void onClick(View button, TipDialog dialog) {
                    binding.tvSettingWxaccount.setText("未绑定");
                    dialog.dismiss();

                }
            });
            outDialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
                @Override
                public void onClick(View button, TipDialog dialog) {
                    dialog.dismiss();
                }
            });
        }
        outDialog.setCancelable(false);
        outDialog.setCanceledOnTouchOutside(false);
        outDialog.show();
    }

    /**
     * 修改性别
     *
     * @param view
     */
    public void onModifySex(View view) {
        DoubleSelectDialog dialog = new DoubleSelectDialog(MySettingActivity.this, "男", "女", new DoubleSelectDialog.DoubleClickListenerInterface() {
            @Override
            public void doFirstClick() {
                updateSex("男");
            }

            @Override
            public void doSecondClick() {
                updateSex("女");
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 修改昵称
     *
     * @param view
     */
    public void onModifyNickName(View view) {
        Intent intent = new Intent(MySettingActivity.this, ModifyNickNameActivity.class);
        startActivity(intent, null);
    }

    public void updateSex(String sex) {
//        initProgressDialog("正在更新性别...").show();
//        UploadPhotoRequest bean = new UploadPhotoRequest(CommonUtils.Bitmap2StrByBase64(headPhoto));
//        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_UPLOAD_PHOTO, bean, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String s) {
//                dialog.dismiss();
//                UploadPhotoReponse response = new UploadPhotoReponse();
//                response = (UploadPhotoReponse) CommonUtils.generateEntityByGson(MySettingActivity.this, s, response);
//                if (response != null) {
//                    RxBus.getInstance().post(new BusEvent(BusEvent.IMG_CODE));
//                    TApplication.context.mUserInfo.face_url = response.info.face_url;
//                    loadFace();
//                    PhotoUtil.getInstance().deleteCache();
//
//                    setResult(RESULT_OK);
//                    ToastUtils.showShort(MySettingActivity.this, "上传成功");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                dialog.dismiss();
//                CommonUtils.fastShowError(MySettingActivity.this, volleyError);
//            }
//        });
        binding.tvSettingSex.setText(sex);
    }
}
