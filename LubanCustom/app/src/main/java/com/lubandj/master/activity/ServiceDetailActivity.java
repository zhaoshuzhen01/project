package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.tablayout.CustomTabLayout;
import com.example.baselibrary.tablayout.MyViewPagerAdapter;
import com.example.baselibrary.widget.NotitleBaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.fragment.IntroduceFragment;
import com.lubandj.master.fragment.WorkSheetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ServiceDetailActivity extends NotitleBaseActivity {
    @InjectView(R.id.ll_basetitle_back)
    LinearLayout llBasetitleBack;
    @InjectView(R.id.msgCount)
    TextView msgCount;
    @InjectView(R.id.ll_basetitle_back1)
    RelativeLayout llBasetitleBack1;
    private ViewPager viewPager;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;
    private CustomTabLayout idTablayout;
    // TabLayout中的tab标题
    private String[] Titles;
    private List<String> mTitles = new ArrayList<>();
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_service_detail;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        idTablayout = (CustomTabLayout) findViewById(R.id.id_tablayout);
        initData();
    }

    @Override
    public void initData() {
        mTitles.add("介绍");
        mTitles.add("评价");
        onSetupTabData(mTitles);
        onSetViewpager();
    }

    protected void onSetupTabData(List<String> titles) {
        Titles = (String[]) titles.toArray(new String[titles.size()]);
        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < Titles.length; i++) {
            IntroduceFragment mFragment = IntroduceFragment.newInstance(i);
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



    @OnClick({R.id.ll_basetitle_back, R.id.ll_basetitle_back1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_basetitle_back:
                finish();
                break;
            case R.id.ll_basetitle_back1:
                Intent intent = new Intent(this, MsgCenterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
