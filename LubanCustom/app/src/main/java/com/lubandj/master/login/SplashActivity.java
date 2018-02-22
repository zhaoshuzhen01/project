package com.lubandj.master.login;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.lubandj.customer.login.LoginActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.activity.MainCantainActivity;
import com.lubandj.master.databinding.ActivitySplashBinding;
import com.lubandj.master.httpbean.UserInfoRequest;
import com.lubandj.master.httpbean.UserInfoResponse;
import com.lubandj.master.my.PermissionActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.StatusBarUtils;
import com.lubandj.master.utils.TaskEngine;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-18
 * company:九州宏图
 */

public class SplashActivity extends PermissionActivity implements ViewPager.OnPageChangeListener {
    private ActivitySplashBinding mBinding;
    private ViewPager viewPager;
    private int imgs[] = {R.drawable.start1, R.drawable.start2, R.drawable.start3};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActUtils.isFirstIn = true;//设置为入口正常进入
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mBinding.viewpager.setAdapter(new MyAdatper());
        mBinding.viewpager.setOnPageChangeListener(this);
        StatusBarUtils.setWindowStatusBarColor(SplashActivity.this, R.color.splash_status_bar);
        if (CommonUtils.getFirst()) {
            mBinding.viewpager.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StatusBarUtils.setWindowStatusBarColor(SplashActivity.this, R.color.white);
                    mBinding.ivSplash.setVisibility(View.GONE);
                }
            }, 1000);
        }
        onLogin();
    }

    public void onLogin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, "location")) {
                setDialogTipUserGoToAppSettting("权限提醒", "应用需要定位权限，请到应用设置中打开");
                startRequestPermission();
                return;
            }
            if (checkPermission(Manifest.permission.CAMERA, "camera")) {
                setDialogTipUserGoToAppSettting("权限提醒", "应用需要拍照权限，请到应用设置中打开");
                startRequestPermission();
                return;
            }
            if (checkPermission(Manifest.permission.CALL_PHONE, "phone")) {
                setDialogTipUserGoToAppSettting("权限提醒", "应用需要打电话权限，请到应用设置中打开");
                startRequestPermission();
                return;
            }
        }
        if (CommonUtils.getFirst())
            return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainCantainActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mBinding.ivSplash.setImageBitmap(null);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    onTokenLogin();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onTokenLogin() {
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETINFO, new UserInfoRequest(CommonUtils.getUid()), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                UserInfoResponse response = new UserInfoResponse();
                response = (UserInfoResponse) CommonUtils.generateEntityByGson(SplashActivity.this, s, response);
                if (response != null) {
                    TApplication.context.mUserInfo = response.info;
                    TApplication.context.setGetuiTag(response.info.uid);
                    Intent intent = new Intent(SplashActivity.this, MainCantainActivity.class);
                    startActivity(intent);
//                    mBinding.ivSplash.setImageBitmap(null);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CommonUtils.fastShowError(SplashActivity.this, volleyError);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPermissionGranted(String operation) {
        onLogin();
    }

    @Override
    public void onPermissionRefuse(String operation) {
        ToastUtils.showShort(SplashActivity.this, "拒绝权限，将退出程序");
        finish();
    }

    class MyAdatper extends PagerAdapter {

        @Override
        public int getCount() {

            return 3;


        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = null;
            if (imgs.length > 0) {
                imageLayout = LayoutInflater.from(SplashActivity.this).inflate(com.example.baselibrary.R.layout.adver_banner_item, view, false);
                ImageView imageView = (ImageView) imageLayout.findViewById(com.example.baselibrary.R.id.bi_imageView);
                final int pos = position % imgs.length; // 为了避免角标越界，进行取余运算
                imageView.setImageResource(imgs[pos]);
                //                ImageUtils.requestImage(imageView, m_AdvImgs.get(pos).getPic(), 0, 0, null);
                //                ImageUtils.requestImage(imageView, m_AdvImgs.get(pos), 0, 0, null);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//                layoutParams.height =getResources().getDisplayMetrics().widthPixels*2;
//                GlideUtils.loadDefaultGameList(imageView, m_AdvImgs.get(pos).getImage());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            view.addView(imageLayout, 0);
            return imageLayout;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == imgs.length - 1 && CommonUtils.getFirst()) {
            CommonUtils.setFirst();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainCantainActivity.class);
                    startActivity(intent);
                }
            }, 500);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
