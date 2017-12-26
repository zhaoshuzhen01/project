package com.lubandj.master.my;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkDetailAdapter;
import com.lubandj.master.adapter.WorkTimeAdapter;
import com.lubandj.master.databinding.ActivityWorkcalendarBinding;
import com.lubandj.master.fragment.WorkCalendarFragment;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class WorkCalendarActivity extends BaseActivity {
    private ActivityWorkcalendarBinding binding;
    private ArrayList<WorkCalendarFragment> mFragments;
    private DayFragmentPager mPagerAdapter;
    private ArrayList<TextView> mTextViews;
    private ArrayList<LinearLayout> mLayouts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workcalendar);

        mTextViews = new ArrayList<>();
        mLayouts = new ArrayList<>();
        mLayouts.add(binding.llWorkcalendarDay1);
        mLayouts.add(binding.llWorkcalendarDay2);
        mLayouts.add(binding.llWorkcalendarDay3);
        mLayouts.add(binding.llWorkcalendarDay4);
        mLayouts.add(binding.llWorkcalendarDay5);

        mTextViews.add(binding.tv1WorkcalendarDay1);
        mTextViews.add(binding.tv2WorkcalendarDay1);
        mTextViews.add(binding.tv1WorkcalendarDay2);
        mTextViews.add(binding.tv2WorkcalendarDay2);
        mTextViews.add(binding.tv1WorkcalendarDay3);
        mTextViews.add(binding.tv2WorkcalendarDay3);
        mTextViews.add(binding.tv1WorkcalendarDay4);
        mTextViews.add(binding.tv2WorkcalendarDay4);
        mTextViews.add(binding.tv1WorkcalendarDay5);
        mTextViews.add(binding.tv2WorkcalendarDay5);

        initTitleData();

        mFragments = new ArrayList<>();
        mFragments.add(new WorkCalendarFragment());
        mFragments.add(new WorkCalendarFragment());
        mFragments.add(new WorkCalendarFragment());
        mFragments.add(new WorkCalendarFragment());
        mFragments.add(new WorkCalendarFragment());


        mPagerAdapter = new DayFragmentPager(getSupportFragmentManager());
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.viewPager.setOffscreenPageLimit(mFragments.size());
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTitleState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initTitleState(0);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }


    public class DayFragmentPager extends FragmentPagerAdapter {
        public DayFragmentPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    private void initTitleData() {
        //获取当前日期
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        binding.tv1WorkcalendarDay1.setText("今天");
        binding.tv2WorkcalendarDay1.setText(formatText(mCalendar.get(Calendar.MONTH) + 1) + "-" + formatText(mCalendar.get(Calendar.DAY_OF_MONTH)));

        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        binding.tv1WorkcalendarDay2.setText("明天");
        binding.tv2WorkcalendarDay2.setText(formatText(mCalendar.get(Calendar.MONTH) + 1) + "-" + formatText(mCalendar.get(Calendar.DAY_OF_MONTH)));

        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        binding.tv1WorkcalendarDay3.setText(getStringOfWeek(mCalendar.get(Calendar.DAY_OF_WEEK)));
        binding.tv2WorkcalendarDay3.setText(formatText(mCalendar.get(Calendar.MONTH) + 1) + "-" + formatText(mCalendar.get(Calendar.DAY_OF_MONTH)));

        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        binding.tv1WorkcalendarDay4.setText(getStringOfWeek(mCalendar.get(Calendar.DAY_OF_WEEK)));
        binding.tv2WorkcalendarDay4.setText(formatText(mCalendar.get(Calendar.MONTH) + 1) + "-" + formatText(mCalendar.get(Calendar.DAY_OF_MONTH)));

        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        binding.tv1WorkcalendarDay5.setText(getStringOfWeek(mCalendar.get(Calendar.DAY_OF_WEEK)));
        binding.tv2WorkcalendarDay5.setText(formatText(mCalendar.get(Calendar.MONTH) + 1) + "-" + formatText(mCalendar.get(Calendar.DAY_OF_MONTH)));

    }

    private void initTitleState(int pos) {
        int count = mFragments.size();
        for (int i = 0; i < count; i++) {
            if (i == pos) {
                mLayouts.get(i).setBackgroundColor(Color.parseColor("#e55c5e"));
                mTextViews.get(i * 2).setTextColor(Color.WHITE);
                mTextViews.get(i * 2 + 1).setTextColor(Color.WHITE);
            } else {
                mLayouts.get(i).setBackgroundColor(Color.WHITE);
                mTextViews.get(i * 2).setTextColor(Color.BLACK);
                mTextViews.get(i * 2 + 1).setTextColor(Color.BLACK);
            }
        }
    }

    private String getStringOfWeek(int dayOfWeek) {
        String week = null;
        switch (dayOfWeek) {
            case 1:
                week = "日";
                break;
            case 2:
                week = "一";
                break;
            case 3:
                week = "二";
                break;
            case 4:
                week = "三";
                break;
            case 5:
                week = "四";
                break;
            case 6:
                week = "五";
                break;
            case 7:
                week = "六";
                break;

        }
        return "周" + week;
    }

    public String formatText(int num) {
        if (num < 9) {
            return "0" + num;
        }
        return num + "";
    }
}
