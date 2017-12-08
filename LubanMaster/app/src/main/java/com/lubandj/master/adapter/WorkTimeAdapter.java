package com.lubandj.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.my.DayShowView;

import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class WorkTimeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public WorkTimeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 24;
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
            convertView = mInflater.inflate(R.layout.item_worktime, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int hour = 8 + position / 2;
        int min = position % 2;
        viewHolder.mTvTime.setText((hour > 9 ? hour + "" : "0" + hour) + ":" + (min == 0 ? "00" : "30"));
        if (position % 4 == 0) {
            viewHolder.mTvTime.setTextColor(Color.parseColor("#999999"));
            viewHolder.mTvState.setVisibility(View.VISIBLE);
            viewHolder.mTvState.setText("(开始时间)");
            viewHolder.mTvState.setTextColor(Color.parseColor("#47a80d"));
            viewHolder.mLl.setBackgroundColor(Color.parseColor("#e2e2e2"));
        } else if (position % 4 == 1) {
            viewHolder.mTvTime.setTextColor(Color.parseColor("#333333"));
            viewHolder.mTvState.setVisibility(View.GONE);
            viewHolder.mLl.setBackgroundColor(Color.parseColor("#e2e2e2"));
        } else if (position % 4 == 2) {
            viewHolder.mTvTime.setTextColor(Color.parseColor("#999999"));
            viewHolder.mTvState.setVisibility(View.VISIBLE);
            viewHolder.mTvState.setText("(结束时间)");
            viewHolder.mTvState.setTextColor(Color.parseColor("#e55c5e"));
            viewHolder.mLl.setBackgroundColor(Color.parseColor("#e2e2e2"));
        } else if (position % 4 == 1) {
            viewHolder.mTvTime.setTextColor(Color.parseColor("#333333"));
            viewHolder.mTvState.setVisibility(View.GONE);
            viewHolder.mLl.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }


    class ViewHolder {
        public LinearLayout mLl;
        public TextView mTvTime;
        public TextView mTvState;


        public ViewHolder(View view) {
            mLl = view.findViewById(R.id.ll_worktime);
            mTvTime = view.findViewById(R.id.tv_worktime_time);
            mTvState = view.findViewById(R.id.tv_worktime_state);
        }
    }
}
