package com.lubandj.master.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.baselibrary.BaseFragment;
import com.example.baselibrary.tablayout.CustomTabLayout;
import com.example.baselibrary.tablayout.MyViewPagerAdapter;
import com.lubandj.master.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/21.
 */

public class OrderFragment extends BaseFragment{
    private ViewPager viewPager;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    private CustomTabLayout idTablayout;
    // TabLayout中的tab标题
    private String[] Titles;
    private List<String> mTitles = new ArrayList<>();
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    public static OrderFragment newInstance(int index) {
        OrderFragment myFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Override
    protected void initData() {
        mTitles.add("未完成");
        mTitles.add("待评价");
        mTitles.add("全部订单");
        onSetupTabData(mTitles);
        onSetViewpager();
    }

    @Override
    protected void initView(View view) {
        viewPager = (ViewPager)view. findViewById(R.id.view_pager);
        idTablayout = (CustomTabLayout) view.findViewById(R.id.id_tablayout);
        initData();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_list;
    }
    //zsz
    protected void onSetupTabData(List<String> titles) {
        Titles = (String[]) titles.toArray(new String[titles.size()]);
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < Titles.length; i++) {
            WorkSheetFragment mFragment = null;
            switch (i){
                case 0:
                    mFragment = WorkSheetFragment.newInstance(1);
                    break;
                case 1:
                    mFragment = WorkSheetFragment.newInstance(2);
                    break;
                case 2:
                    mFragment = WorkSheetFragment.newInstance(0);
                    break;
            }
            mFragments.add(i, mFragment);
            idTablayout.removeAllTabs();
            idTablayout.addTab(idTablayout.newTab().setText(Titles[i]));
        }
    }

    protected void onSetViewpager() {
        // 初始化ViewPager的适配器，并设置给它
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager(), Titles, mFragments);
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
