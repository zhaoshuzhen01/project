package com.lubandj.master.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.my.DayShowView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class WorkCalendarAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private static final int MONTHS = 3;

    private Calendar mCalendar;
//    private int currentMonth = 0;//当前月
//    private int currentYear = 0;//当前年
//    private int currentDay = 0;//当前日
//    private int firstDayOfMonth = 0;//本月第一天的星期 sunday=1
//    private int firstDayOfNextMonth;

    private ArrayList<Integer> monthPosList = new ArrayList<>();
    private ArrayList<Integer> monthList = new ArrayList<>();
    private ArrayList<Integer> yearOfMmonthList = new ArrayList<>();
    private ArrayList<Integer> firstWeekOfMonthList = new ArrayList<>();
    private ArrayList<Integer> daysOfMonthList = new ArrayList<>();
    private ArrayList<Integer> WeeksOfMonthList = new ArrayList<>();

    private int firstMonthPos = 0;
    private int secondMonthPos = 0;
    private int thirdMonthPos = 0;

    public WorkCalendarAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        //获取当前日期
        mCalendar = Calendar.getInstance(Locale.CHINA);
        int currentMonth = mCalendar.get(Calendar.MONTH) + 1;
        int currentYear = mCalendar.get(Calendar.YEAR);
        //设置本月一号
        int currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mCalendar.add(Calendar.DAY_OF_MONTH, 1 - currentDay);
        int firstDayOfMonth = mCalendar.get(Calendar.DAY_OF_WEEK);
        generateMonthData(currentYear, currentMonth, firstDayOfMonth);
    }

    @Override
    public int getCount() {
        return monthPosList.get(MONTHS);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_workcalendar, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {//第一个月
            viewHolder.mLlDays.setVisibility(View.INVISIBLE);
            viewHolder.mTvMonth.setText(monthList.get(0) + "月");
            viewHolder.mTvMonth.setTextColor(Color.parseColor("#e94b4e"));
        } else if (monthPosList.contains(position)) {
            for (int i = 0; i < monthPosList.size(); i++) {
                if (monthPosList.get(i) == position) {
                    viewHolder.mLlDays.setVisibility(View.INVISIBLE);
                    viewHolder.mTvMonth.setText(monthList.get(i) + "月");
                    viewHolder.mTvMonth.setTextColor(Color.parseColor("#000000"));
                    break;
                }
            }
        } else {
            viewHolder.mLlDays.setVisibility(View.VISIBLE);
            int pos = 0;//当前位置
            for (int i = 0; i < monthPosList.size(); i++) {
                if (monthPosList.get(i) > position) {
                    pos = i - 1;
                    break;
                }
            }
            int weeksOfMonth = WeeksOfMonthList.get(pos);
            int serial = position - monthPosList.get(pos);
            int firstDayOfMonth = firstWeekOfMonthList.get(pos);
            for (int i = 0; i < viewHolder.dsvs.size(); i++) {
                if (serial == 1 && i < firstDayOfMonth - 1) {
                    viewHolder.dsvs.get(i).setTvTextClickable("", false);
                } else {
                    int day = (i + 1) - (firstDayOfMonth - 1) + 7 * (serial - 1);
                    if (serial == weeksOfMonth && day > daysOfMonthList.get(pos)) {
                        viewHolder.dsvs.get(i).setTvTextClickable("", false);
                    } else {
                        if (i == 3) {
                            viewHolder.dsvs.get(i).setTvTextClickable(day + "", true);
                        } else if (i == 5) {
                            viewHolder.dsvs.get(i).setTvSelect(day + "");
                        } else {
                            viewHolder.dsvs.get(i).setTvTextClickable(day + "", false);
                        }
                    }
                }
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView mTvMonth;
        public LinearLayout mLlDays;
        public ArrayList<DayShowView> dsvs = new ArrayList<>();

        public ViewHolder(View view) {
            mTvMonth = view.findViewById(R.id.tv_week_month);
            mLlDays = view.findViewById(R.id.ll_week_days);
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_first));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_second));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_third));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_forth));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_fifth));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_sixth));
            dsvs.add((DayShowView) view.findViewById(R.id.dsv_seventh));
        }
    }

    public int getDaysOfMoth(int year, int month) {
        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                    days = 29;
                else
                    days = 28;
                break;
        }
        return days;
    }

    public int getWeeksOfMonth(int days, int firstDayOfMonth) {
        return (int) Math.ceil((days + firstDayOfMonth - 1) / 7.0);
    }

    public void generateMonthData(int year, int month, int firstWeekOfMonth) {
        int currentYear = year;
        int currentMonth = month;
        int firstDayInWeek = firstWeekOfMonth;
        int days = getDaysOfMoth(currentYear, currentMonth);//获取当前月天数
        int weeksOfMonth = getWeeksOfMonth(days, firstDayInWeek);
        monthPosList.add(0);
        for (int i = 0; i < MONTHS; i++) {
            monthList.add(currentMonth);
            yearOfMmonthList.add(currentYear);
            firstWeekOfMonthList.add(firstDayInWeek);
            daysOfMonthList.add(days);
            WeeksOfMonthList.add(weeksOfMonth);
            int secondMonthPos = monthPosList.get(i) + weeksOfMonth + 1;
            monthPosList.add(secondMonthPos);

            currentMonth++;
            if (currentMonth > 12) {
                currentYear++;
                currentMonth -= 12;
            }
            days = getDaysOfMoth(currentYear, currentMonth);
            firstDayInWeek = (days + firstDayInWeek - 1) % 7 + 1;
            weeksOfMonth = getWeeksOfMonth(days, firstDayInWeek);
        }
    }

}
