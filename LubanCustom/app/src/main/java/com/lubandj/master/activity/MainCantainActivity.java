package com.lubandj.master.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.BaseMainActivity;
import com.example.baselibrary.adapter.MainAdapter;
import com.example.baselibrary.widget.BottomNavigationViewHelper;
import com.example.baselibrary.widget.MainBottomTab;
import com.example.baselibrary.widget.MainBottomView;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.example.baselibrary.widget.ViewPagerSlide;
import com.lubandj.master.R;
import com.lubandj.master.fragment.HomeFragment;
import com.lubandj.master.fragment.MyFragment;
import com.lubandj.master.fragment.OrderFragment;
import com.lubandj.master.fragment.WorkSheetFragment;
import com.lubandj.master.utils.CommonUtils;

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

    public  static  void startActivity(Context context){
        Intent intent = new Intent(context, MainCantainActivity.class);
        context.startActivity(intent);
        viewPager.setCurrentItem(0);
    }
    @Override
    public void initView() {
        bottomNavigationBar = (MainBottomView) findViewById(com.example.baselibrary.R.id.bottom_navigation_bar);
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
    //初始化ViewPager
    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(HomeFragment.newInstance(0));
        mList.add(OrderFragment.newInstance(1));
        mList.add(MyFragment.newInstance(2));
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
        bottomNavigationBar.setBackgroundResource(R.drawable.main_back_bottom);
        bottomNavigationBar.addItem(new MainBottomTab(this, "首页").setActiviIcon(R.drawable.home_button2).setInActiviIcon(R.drawable.home_button1).setActiviColor(R.color.white).setInActiviColor(R.color.white))
                .addItem(new MainBottomTab(this, "订单").setActiviIcon(R.drawable.order_button2).setInActiviIcon(R.drawable.order_button1).setActiviColor(R.color.white).setInActiviColor(R.color.white))
                .addItem(new MainBottomTab(this, "我的").setActiviIcon(R.drawable.my_button2).setInActiviIcon(R.drawable.my_button1).setActiviColor(R.color.white).setInActiviColor(R.color.white))
                .setSelectTab(0);
    }

    @Override
    public void titleLeftClick() {
        ChooseCityActivity.startActivity(this);
    }

    @Override
    protected void clickMenu() {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.example.baselibrary.R.id.tv_basetitle_ok:
            case com.example.baselibrary.R.id.ll_basetitle_back1:
                Intent intent = new Intent(this, MsgCenterActivity.class);
                startActivity(intent);
                int count = CommonUtils.getMsgCount();
                if (count > 0) {
//                    DbInstance.getInstance().insertDatas();
                }
                break;
        }
    }

}
