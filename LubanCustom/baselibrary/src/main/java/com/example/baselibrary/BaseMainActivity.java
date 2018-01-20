package com.example.baselibrary;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.example.baselibrary.widget.ViewPagerSlide;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public abstract class BaseMainActivity extends NotitleBaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    protected ViewPagerSlide viewPager;
    protected BottomNavigationBar bottomNavigationBar;
    protected BadgeItem badgeItem; //添加角标
    protected List<Fragment> mList; //ViewPager的数据源
    //初始化底部导航条
    public void initBottomNavigationBar() {
        bottomNavigationBar.setTabSelectedListener(this);
        badgeItem = new BadgeItem()
                .setHideOnSelect(true) //设置被选中时隐藏角标
                .setBackgroundColor(Color.RED);
//                .setText("99");
        /**
         * 设置模式
         * 1、BottomNavigationBar.MODE_DEFAULT 默认
         * 如果Item的个数<=3就会使用MODE_FIXED模式，否则使用MODE_SHIFTING模式
         *
         * 2、BottomNavigationBar.MODE_FIXED 固定大小
         * 填充模式，未选中的Item会显示文字，没有换挡动画。
         *
         * 3、BottomNavigationBar.MODE_SHIFTING 不固定大小
         * 换挡模式，未选中的Item不会显示文字，选中的会显示文字。在切换的时候会有一个像换挡的动画
         */
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        /**
         * 设置背景的样式
         * 1、BottomNavigationBar.BACKGROUND_STYLE_DEFAULT 默认
         * 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC 。
         * 如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
         *
         * 2、BottomNavigationBar.BACKGROUND_STYLE_STATIC 导航条的背景色是白色，
         * 加上setBarBackgroundColor（）可以设置成你所需要的任何背景颜色
         * 点击的时候没有水波纹效果
         *
         * 3、BottomNavigationBar.BACKGROUND_STYLE_RIPPLE 导航条的背景色是你设置的处于选中状态的
         * Item的颜色（ActiveColor），也就是setActiveColorResource这个设置的颜色
         * 点击的时候有水波纹效果
         */
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //设置导航条背景颜色
        //在BACKGROUND_STYLE_STATIC下，表示整个容器的背景色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示选中Item的图标和文本颜色。默认 Color.WHITE
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        //选中时的颜色,在BACKGROUND_STYLE_STATIC下，表示选中Item的图标和文本颜色。
        // 而在BACKGROUND_STYLE_RIPPLE下，表示整个容器的背景色。默认Theme's Primary Color
        //bottomNavigationBar.setActiveColor(R.color.black);
        //未选中时的颜色，表示未选中Item中的图标和文本颜色。默认为 Color.LTGRAY
        //bottomNavigationBar.setInActiveColor("#FFFFFF");
    }

    @Override
    public void onTabSelected(int position) {
        //tab被选中
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager滑动
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
