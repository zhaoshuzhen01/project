package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;

import com.lubandj.master.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * 滑动选择弹窗
 *
 * @创建时间：2015/12/22 09:38
 */
public class ScrollSelectDialog extends Dialog implements View.OnClickListener {
    private Button mBtnSelect;
    private Button mBtnCancel;
    private Context context;
    private ClickListenerInterface clickListenerInterface;
    private WheelView mWvYear;
    private WheelView mWvMonth;
    private WheelView mWvDay;
    private WheelView mWvTime;
    private WheelView.WheelViewStyle style;

    private ArrayList<String> yearList;
    private ArrayList<String> monthList;
    private ArrayList<String> dayList;
    private ArrayList<String> timeList;

    private int currentDay;
    private int currentSelectDay;

    public ScrollSelectDialog(Context context, ClickListenerInterface clickListenerInterface) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.clickListenerInterface = clickListenerInterface;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_scrollselect, null);
        setContentView(view);
        mBtnSelect = (Button) view.findViewById(R.id.btn_cancel_scrollselect);
        mBtnCancel = (Button) view.findViewById(R.id.btn_select_scrollselect);
        mBtnSelect.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mWvYear = (WheelView) view.findViewById(R.id.wv_year);
        mWvMonth = (WheelView) view.findViewById(R.id.wv_month);
        mWvDay = (WheelView) view.findViewById(R.id.wv_day);
        mWvTime = (WheelView) view.findViewById(R.id.wv_time);

        style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        style.selectedTextColor = Color.parseColor("#ff8533");
        style.textColor = Color.parseColor("#999999");
        style.holoBorderColor = Color.parseColor("#dddddd");
        setStyle(mWvYear);
        setStyle(mWvMonth);
        setStyle(mWvDay);
        setStyle(mWvTime);

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        yearList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            yearList.add((year + i) + "");
        }
        mWvYear.setWheelData(yearList);
        mWvYear.setSelection(0);

        monthList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            monthList.add(i + "");
        }
        mWvMonth.setWheelData(monthList);
        mWvMonth.setSelection(calendar.get(Calendar.MONTH));

        setDayList();
        mWvDay.setWheelData(dayList);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        currentSelectDay = day - 1;
        mWvDay.setSelection(currentSelectDay);

        timeList = new ArrayList<>();
        timeList.add("上午");
        timeList.add("下午");
        mWvTime.setWheelData(timeList);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels;
        dialogWindow.setAttributes(lp);

        dialogWindow.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        dialogWindow.setWindowAnimations(R.style.progressinsertstyle);  //添加动画

        mWvYear.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                if (monthList.get(mWvMonth.getCurrentPosition()).equals("2")) {
                    int year = Integer.parseInt(yearList.get(position));
                    refreshDay(year, 2);
                }
            }
        });


        mWvMonth.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int year = Integer.parseInt(yearList.get(mWvYear.getCurrentPosition()));
                int month = Integer.parseInt(monthList.get(position));
                refreshDay(year, month);
            }
        });

        mWvDay.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                currentSelectDay = position;
            }
        });
    }

    public void refreshDay(int year, int month) {
        int day = getDaysOfMoth(year, month);
        if (day != currentDay) {
            currentDay = day;
            dayList = new ArrayList<>();
            for (int i = 1; i <= currentDay; i++) {
                dayList.add(i + "");
            }
            mWvDay.resetDataFromTop(dayList);
            if (currentSelectDay > currentDay - 1) {
                currentSelectDay = currentDay - 1;
            }
            mWvDay.setSelection(currentSelectDay);
        }
    }

    public void setStyle(WheelView wv) {
        wv.setWheelAdapter(new ArrayWheelAdapter(context));
        wv.setLoop(false);
        wv.setSkin(WheelView.Skin.Holo);
        wv.setStyle(style);
        wv.setWheelSize(5);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public void setDayList() {
        dayList = new ArrayList<>();
        int year = Integer.parseInt(yearList.get(mWvYear.getSelection()));
        int month = Integer.parseInt(monthList.get(mWvMonth.getSelection()));
        currentDay = getDaysOfMoth(year, month);
        for (int i = 1; i <= currentDay; i++) {
            dayList.add(i + "");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_select_scrollselect:
                ScrollSelectDialog.this.dismiss();
                StringBuilder sb = new StringBuilder("");
                String month = (String) mWvMonth.getSelectionItem();
                if (month.length() == 1) {
                    month = "0" + month;
                }
                String day = (String) mWvDay.getSelectionItem();
                if (day.length() == 1) {
                    day = "0" + day;
                }
                sb.append(mWvYear.getSelectionItem() + "-" + month + "-" + day);
                sb.append(" " + mWvTime.getSelectionItem());
                clickListenerInterface.doConfirm(sb.toString());
                break;
            case R.id.btn_cancel_scrollselect:
                ScrollSelectDialog.this.dismiss();
                break;
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
}
