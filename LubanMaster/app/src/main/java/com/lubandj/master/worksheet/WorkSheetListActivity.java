package com.lubandj.master.worksheet;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tablayout.CustomTabLayout;
import com.example.baselibrary.tablayout.MyViewPagerAdapter;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.fragment.WorkSheetFragment;

import java.util.ArrayList;
import java.util.List;

public class WorkSheetListActivity extends TitleBaseActivity {
    private ViewPager viewPager;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    private CustomTabLayout idTablayout;
    // TabLayout中的tab标题
    private String[] Titles;
    private List<String> mTitles=new ArrayList<>();
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_list;
    }

    @Override
    public void initView() {
        setTitleText("工单列表");
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        idTablayout = (CustomTabLayout)findViewById(R.id.id_tablayout);
        View view = mNavigationView.inflateHeaderView(R.layout.header_just_username);
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
    public void onClick(View view) {

    }

    @Override
    public void titleLeftClick() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    //zsz
    protected void onSetupTabData(List<String>titles) {
        Log.e("cc", titles.size() + "      datas.size() ");

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
        if (mViewPagerAdapter==null){
            mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), Titles, mFragments);
        }else {
            mViewPagerAdapter.setDatas(Titles,mFragments);
            mViewPagerAdapter.notifyDataSetChanged();
        }

        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(mViewPagerAdapter);
            idTablayout.setupWithViewPager(viewPager);
        }
        viewPager.setOffscreenPageLimit(5);
    }
}
