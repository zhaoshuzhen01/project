package com.lubandj.master.worksheet;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tablayout.CustomTabLayout;
import com.example.baselibrary.tablayout.MyViewPagerAdapter;
import com.lubandj.master.R;
import com.lubandj.master.activity.MsgCenterActivity;
import com.lubandj.master.fragment.WorkSheetFragment;
import com.lubandj.master.my.AboutLuBanActivity;
import com.lubandj.master.my.AskForLeaveActivity;
import com.lubandj.master.my.ModifyPhoneActivity;
import com.lubandj.master.my.MyAddressActivity;
import com.lubandj.master.my.WorkCalendarActivity;
import com.lubandj.master.my.WorkCodeActivity;

import java.util.ArrayList;
import java.util.List;

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
    private ImageView mIvHeadImg;//头像
    private TextView mTvName;//姓名
    private RatingBar mBar;//评分条
    private TextView mTvRate;//评分
    private TextView mTvPhone;//电话

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_list;
    }

    @Override
    public void initView() {
        setTitleText("工单列表");
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
        mTvPhone = view.findViewById(R.id.tv_menu_phone);
        view.findViewById(R.id.ll_menu_phone).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_address).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_service).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_workcode).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_workcalendar).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_askforleave).setOnClickListener(this);
        view.findViewById(R.id.ll_menu_aboutus).setOnClickListener(this);
        view.findViewById(R.id.btn_menu_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_menu_phone:
                startActivity(ModifyPhoneActivity.class, null);
                break;
            case R.id.ll_menu_address:
                startActivity(MyAddressActivity.class, null);
                break;
            case R.id.ll_menu_service:
                break;
            case R.id.ll_menu_workcode:
                startActivity(WorkCodeActivity.class, null);
                break;
            case R.id.ll_menu_workcalendar:
                startActivity(WorkCalendarActivity.class, null);
                break;
            case R.id.ll_menu_askforleave:
                startActivity(AskForLeaveActivity.class, null);
                break;
            case R.id.ll_menu_aboutus:
                startActivity(AboutLuBanActivity.class, null);
                break;
            case R.id.btn_menu_logout:
                break;
            case com.example.baselibrary.R.id.ll_basetitle_back1:
                Intent intent = new Intent(this, MsgCenterActivity.class);
                startActivity(intent);
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
}
