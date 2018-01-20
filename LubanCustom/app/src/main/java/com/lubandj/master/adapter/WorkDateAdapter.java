package com.lubandj.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.been.WorkDayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2018-1-1
 * company:九州宏图
 */

public class WorkDateAdapter extends RecyclerView.Adapter<WorkDateAdapter.WorkDateViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<WorkDayBean> dataList;
    private final int SHOW_NUM = 30;
    private int selectPos = 0;
    private View.OnClickListener mListener;

    public WorkDateAdapter(Context context, View.OnClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        this.dataList = new ArrayList<>();
        Calendar mCalendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < SHOW_NUM; i++) {
            WorkDayBean bean = new WorkDayBean();
            if (i == 0) {
                bean.dayOfWeek = "今天";
            } else if (i == 1) {
                bean.dayOfWeek = "明天";
            } else {
                bean.dayOfWeek = getStringOfWeek(mCalendar.get(Calendar.DAY_OF_WEEK));
            }
            bean.fulldate = sdf.format(mCalendar.getTime());
            bean.day = bean.fulldate.substring(5);
            dataList.add(bean);
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    public String getCurrentSelectDay() {
        return dataList.get(selectPos).fulldate;
    }

    @Override
    public WorkDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkDateViewHolder viewHolder = new WorkDateViewHolder(mInflater.inflate(R.layout.item_workdate, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WorkDateViewHolder holder, int position) {
        WorkDayBean bean = dataList.get(position);
        holder.mTvDayOfWeek.setText(bean.dayOfWeek);
        holder.mTvDay.setText(bean.day);
        holder.mLlTitle.setOnClickListener(mListener);
        holder.mLlTitle.setTag(position);
        if (selectPos == position) {
            holder.mTvDayOfWeek.setTextColor(Color.parseColor("#e55c5e"));
            holder.mTvDay.setTextColor(Color.parseColor("#e55c5e"));
        } else {
            holder.mTvDayOfWeek.setTextColor(Color.BLACK);
            holder.mTvDay.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return SHOW_NUM;
    }

    public class WorkDateViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvDayOfWeek;
        public TextView mTvDay;
        public LinearLayout mLlTitle;

        public WorkDateViewHolder(View itemView) {
            super(itemView);
            mTvDayOfWeek = itemView.findViewById(R.id.tv_dayofweek);
            mTvDay = itemView.findViewById(R.id.tv_day);
            mLlTitle = itemView.findViewById(R.id.ll_workday);
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
}
