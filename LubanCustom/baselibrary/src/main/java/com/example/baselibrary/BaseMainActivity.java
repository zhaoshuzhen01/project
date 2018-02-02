package com.example.baselibrary;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.baselibrary.util.ActUtils;
import com.example.baselibrary.widget.MainBottomView;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.example.baselibrary.widget.ViewPagerSlide;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public abstract class BaseMainActivity extends TitleBaseActivity implements  ViewPager.OnPageChangeListener,MainBottomView.BottomTabSelect {
    protected static ViewPagerSlide viewPager;
    protected MainBottomView bottomNavigationBar;
    protected List<Fragment> mList; //ViewPager的数据源
    private long exitTime = 0;
    //初始化底部导航条
    public void initBottomNavigationBar() {
        bottomNavigationBar.setBottomTabSelect(this);
    }

    @Override
    public void onselectIndex(int position) {
        viewPager.setCurrentItem(position);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
        bottomNavigationBar.setSelectTab(position);
        window.setStatusBarColor(getResources().getColor(R.color.white));
        setTitleColor(getResources().getColor(R.color.white));
        switch (position){
            case 0:
                setTitleText("首页");
                tv_basetitle_back.setVisibility(View.VISIBLE);
                break;
            case 1:
                setTitleText("我的订单");
                tv_basetitle_back.setVisibility(View.GONE);

                break;
            case 2:
                setTitleText("");
                setTitleColor(getResources().getColor(R.color.redm));
                window.setStatusBarColor(getResources().getColor(R.color.redm));
                tv_basetitle_back.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onBackPressed() {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                ActUtils.getInstance().exitApp(BaseMainActivity.this);
            }
    }
}
