package com.lubandj.master.worksheet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.eventbus.RxBus;
import com.example.baselibrary.tablayout.CustomTabLayout;
import com.example.baselibrary.tablayout.MyViewPagerAdapter;
import com.example.baselibrary.tools.NotificationUtil;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.example.baselibrary.util.PhotoUtil;
import com.google.gson.JsonSyntaxException;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.activity.MsgCenterActivity;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.UserInfo;
import com.lubandj.master.customview.RoundImageView;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.fragment.WorkSheetFragment;
import com.lubandj.master.dialog.DoubleSelectDialog;
import com.lubandj.master.httpbean.UploadPhotoReponse;
import com.lubandj.master.httpbean.UploadPhotoRequest;
import com.lubandj.master.login.LoginActivity;
import com.lubandj.master.my.AboutLuBanActivity;
import com.lubandj.master.my.AskForLeaveActivity;
import com.lubandj.master.my.LeaveListActivity;
import com.lubandj.master.my.ModifyPhoneActivity;
import com.lubandj.master.my.MyAddressActivity;
import com.lubandj.master.my.MySettingActivity;
import com.lubandj.master.my.WorkCalendarActivity;
import com.lubandj.master.my.WorkCodeActivity;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.NetworkUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class WorkSheetListActivity extends TitleBaseActivity {
    private ViewPager viewPager;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    private CustomTabLayout idTablayout;
    // TabLayout中的tab标题
    private String[] Titles;
    private List<String> mTitles = new ArrayList<>();
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;


    //menu
    private View view;
    private RoundImageView mIvHeadImg;//头像
    private TextView mTvName;//姓名
    private RatingBar mBar;//评分条
    private TextView mTvRate;//评分
    private String serviceNumber;

    private ImageLoader imageLoader;
    private long exitTime = 0;
    private Observable<MsgCenterBeen> observable;

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_list;
    }

    @Override
    public void initView() {
        setTitleText("工单列表");
        msgCount.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        idTablayout = (CustomTabLayout) findViewById(R.id.id_tablayout);
        view = mNavigationView.inflateHeaderView(R.layout.activity_leftmenu);
        initMenu(view);
        MenuShow();
        initData();
    }

    @Override
    public void initData() {
        mTitles.add("未完成");
        mTitles.add("已完成");
        mTitles.add("已取消");
        onSetupTabData(mTitles);
        onSetViewpager();
        observable = RxBus.getInstance().register(this);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MsgCenterBeen>() {
            @Override
            public void call(MsgCenterBeen userBean) {
                Log.e("deal", "received :" + userBean.toString());
                onResume();
                if (!NotificationUtil.isNotificationEnabled(WorkSheetListActivity.this)){
                notifyMes(WorkSheetListActivity.this);
            }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = CommonUtils.getMsgCount();
        if (count > 0) {
            msgCount.setText(count + "");
            msgCount.setVisibility(View.VISIBLE);
        } else {
            msgCount.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    public void initMenu(View view) {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = getResources().getDisplayMetrics().heightPixels - statusBarHeight1;

        mIvHeadImg = view.findViewById(R.id.iv_menu_headimg);
        mTvName = view.findViewById(R.id.tv_menu_name);
        mBar = view.findViewById(R.id.rb_menu_rate);
        mTvRate = view.findViewById(R.id.tv_menu_rate);
        view.findViewById(R.id.ll_menu_service).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_workcode).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_workcalendar).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_askforleave).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_setting).setOnClickListener(this);

        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());
        UserInfo info = TApplication.context.mUserInfo;
        mTvName.setText(info.nickname + "");
        if (!TextUtils.isEmpty(info.face_url)) {
            loadFace();
        }
        serviceNumber = "4006-388-818";
    }

    public void loadFace() {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(mIvHeadImg, R.drawable.default_header, R.drawable.default_header);
        if (!TextUtils.isEmpty(TApplication.context.mUserInfo.face_url))
            imageLoader.get(TApplication.context.mUserInfo.face_url, imageListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.example.baselibrary.R.id.tv_basetitle_ok:
            case com.example.baselibrary.R.id.ll_basetitle_back1:
                Intent intent = new Intent(this, MsgCenterActivity.class);
                startActivity(intent);
                break;
        }
        leftClick = true;
        leftView = view;
        mDrawerLayout.closeDrawer(Gravity.LEFT,false);
    }

    @Override
    protected void clickMenu() {
        leftClick = false;
        switch (leftView.getId()) {
            case R.id.ll_menu_service:
                TipDialog dialog = new TipDialog(WorkSheetListActivity.this);
                dialog.setNoPomptTitle();
                dialog.setTextDes("将拨打" + serviceNumber);
                dialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
                    @Override
                    public void onClick(View button, TipDialog dialog) {
                        Intent callingIntent = new Intent(Intent.ACTION_CALL,
                                Uri.parse("tel:" + serviceNumber));
                        int hasWriteContactsPermission = ActivityCompat.checkSelfPermission(WorkSheetListActivity.this, Manifest.permission.CALL_PHONE);
                        //权限是否允许
                        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                            //是否弹出提醒窗
                            try {
                                ActivityCompat.requestPermissions(WorkSheetListActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                        REQUEST_PERMISSION_CAMERA_CODE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (!ActivityCompat.shouldShowRequestPermissionRationale(WorkSheetListActivity.this, Manifest.permission.CALL_PHONE)) {
                                    ToastUtils.showShort(WorkSheetListActivity.this, "打电话权限被禁止");
                                    return;
                                }
                            }
                            return;
                        } else {
                            startActivity(callingIntent);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
                    @Override
                    public void onClick(View button, TipDialog dialog) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
            case R.id.ll_menu_workcode:
                startActivity(WorkCodeActivity.class, null);
                break;
            case R.id.ll_menu_workcalendar:
                startActivity(WorkCalendarActivity.class, null);
                break;
            case R.id.ll_menu_askforleave:
                startActivity(LeaveListActivity.class, null);
                break;
            case R.id.ll_menu_setting:
                Intent intent = new Intent(WorkSheetListActivity.this, MySettingActivity.class);
                startActivityForResult(intent, 3030);
                break;
        }
    }

    @Override
    public void titleLeftClick() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    //zsz
    protected void onSetupTabData(List<String> titles) {
        Titles = (String[]) titles.toArray(new String[titles.size()]);
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < Titles.length; i++) {
            WorkSheetFragment mFragment = WorkSheetFragment.newInstance(i);
            mFragments.add(i, mFragment);
            idTablayout.removeAllTabs();
            idTablayout.addTab(idTablayout.newTab().setText(Titles[i]));
        }
    }

    protected void onSetViewpager() {
        // 初始化ViewPager的适配器，并设置给它
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), Titles, mFragments);
        } else {
            mViewPagerAdapter.setDatas(Titles, mFragments);
            mViewPagerAdapter.notifyDataSetChanged();
        }

        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(mViewPagerAdapter);
            idTablayout.setupWithViewPager(viewPager);
        }
        viewPager.setOffscreenPageLimit(5);
    }

    private static final int REQUEST_PERMISSION_CAMERA_CODE = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                Intent callingIntent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + serviceNumber));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callingIntent);
            } else {
                // Permission Denied
                ToastUtils.showShort(WorkSheetListActivity.this, "打电话权限被禁止");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拍照
     */
    private void onTakePic() {
        DoubleSelectDialog dialog = new DoubleSelectDialog(WorkSheetListActivity.this, "拍照", "从手机相册选择", new DoubleSelectDialog.DoubleClickListenerInterface() {
            @Override
            public void doFirstClick() {
                PhotoUtil.getInstance().takePhoto(WorkSheetListActivity.this);
            }

            @Override
            public void doSecondClick() {
                PhotoUtil.getInstance().pickPhoto(WorkSheetListActivity.this);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
//        if (!TApplication.context.isActive) {
//            MyApplication.getApplication().isActive = true;
//        }
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
            // 1.将裁减头像保存到文件
//            File file = FileUtils.saveBitmap(headPhoto, "headPhoto.png");
            // 2.将图片传到网上，并回显
            mIvHeadImg.setImageBitmap(headPhoto);
            if (NetworkUtils.isNetworkAvailable(WorkSheetListActivity.this)) {
                initProgressDialog("正在上传头像...").show();
                UploadPhotoRequest bean = new UploadPhotoRequest(CommonUtils.Bitmap2StrByBase64(headPhoto));
                TaskEngine.getInstance().tokenHttps(Canstance.HTTP_UPLOAD_PHOTO, bean, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        UploadPhotoReponse response = new UploadPhotoReponse();
                        response = (UploadPhotoReponse) CommonUtils.generateEntityByGson(WorkSheetListActivity.this, s, response);
                        if (response != null) {
                            TApplication.context.mUserInfo.face_url = response.info.face_url;
                            loadFace();
                            PhotoUtil.getInstance().deleteCache();
                            ToastUtils.showShort(WorkSheetListActivity.this, "上传成功");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        if (volleyError != null) {
                            if (volleyError.networkResponse != null)
                                ToastUtils.showShort(WorkSheetListActivity.this, "网络连接错误（" + volleyError.networkResponse.statusCode + ")");
                            Log.e("TAG", volleyError.getMessage(), volleyError);
                        }
                    }
                });
            } else if (requestCode == 3030) {
                finish();
            } else {
                ToastUtils.showShort(WorkSheetListActivity.this, "网络未连接");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawers();
        else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                ActUtils.getInstance().exitApp(WorkSheetListActivity.this);
            }
        }
    }
    private void notifyMes(final Context context){
        TipDialog outDialog = new TipDialog(context);
        outDialog.setNoPomptTitle();
        outDialog.setTextDes("打开通知权限");
        outDialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
                NotificationUtil.goToSet(context);
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
}
