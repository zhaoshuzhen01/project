package com.lubandj.master.activity;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.BaseMainActivity;
import com.example.baselibrary.adapter.MainAdapter;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.example.baselibrary.widget.ViewPagerSlide;
import com.lubandj.master.R;
import com.lubandj.master.fragment.HomeFragment;
import com.lubandj.master.fragment.WorkSheetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * //Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class MainCantainActivity extends BaseMainActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_main_containt;
    }

    @Override
    public void initView() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(com.example.baselibrary.R.id.bottom_navigation_bar);
        initBottomNavigationBar();
        initViewPager();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    //初始化ViewPager
    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(HomeFragment.newInstance(0));
        mList.add(HomeFragment.newInstance(1));
        mList.add(HomeFragment.newInstance(2));
        viewPager = (ViewPagerSlide) findViewById(R.id.viewPager);
        viewPager.setSlide(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(this);
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }

    //初始化底部导航条
    public void initBottomNavigationBar() {
        super.initBottomNavigationBar();
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon, "首页").setActiveColorResource(R.color.black).setInActiveColorResource(R.color.white).setInactiveIcon(getResources().getDrawable(R.drawable.icon)))
                .addItem(new BottomNavigationItem(R.drawable.icon, "订单").setActiveColorResource(R.color.black).setInActiveColorResource(R.color.white).setInactiveIcon(getResources().getDrawable(R.drawable.icon)))
                .addItem(new BottomNavigationItem(R.drawable.icon, "我的").setActiveColorResource(R.color.black).setInActiveColorResource(R.color.white).setInactiveIcon(getResources().getDrawable(R.drawable.icon)))
//                .addItem(new BottomNavigationItem(R.drawable.icon, "我的").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
    }
}
