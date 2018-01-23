package com.lubandj.master.activity;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.BaseMainActivity;
import com.example.baselibrary.adapter.MainAdapter;
import com.example.baselibrary.widget.BottomNavigationViewHelper;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.example.baselibrary.widget.ViewPagerSlide;
import com.lubandj.master.R;
import com.lubandj.master.fragment.HomeFragment;
import com.lubandj.master.fragment.OrderFragment;
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
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setTitleText("首页");
        msgCount.setText(1 + "");
        msgCount.setVisibility(View.VISIBLE);
        tv_basetitle_back.setText("  北京 ");
        Drawable drawableLeft = getResources().getDrawable(
                R.drawable.selectaddress_gps_down);

        tv_basetitle_back.setCompoundDrawablesWithIntrinsicBounds(null,
                null, drawableLeft, null);
        ivBaseTitleBack.setVisibility(View.GONE);
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
        mList.add(OrderFragment.newInstance(1));
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
        bottomNavigationBar.setBarBackgroundColor(R.color.color_827C7D);//背景颜色
        bottomNavigationBar.setInActiveColor(R.color.white);//未选中时的颜色
        bottomNavigationBar.setActiveColor(R.color.white);//选中时的颜色

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.home_button2, "首页").setInactiveIcon(getResources().getDrawable(R.drawable.home_button1)))
                .addItem(new BottomNavigationItem(R.drawable.order_button2, "订单").setInactiveIcon(getResources().getDrawable(R.drawable.order_button1)))
                .addItem(new BottomNavigationItem(R.drawable.my_button2, "我的").setInactiveIcon(getResources().getDrawable(R.drawable.my_button1)))
//                .addItem(new BottomNavigationItem(R.drawable.icon, "我的").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
    }

    @Override
    public void titleLeftClick() {
toast(this,"选择城市");
    }

    @Override
    protected void clickMenu() {

    }
}
