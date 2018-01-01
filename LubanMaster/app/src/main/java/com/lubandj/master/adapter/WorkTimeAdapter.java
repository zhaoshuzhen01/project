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
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.httpbean.WorkDetailResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class WorkTimeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private WorkDetailResponse.WorkDetailBean mBean;
    private String beginLeave;
    private String endLeave;
    private ArrayList<WorkTime> workList;


    public WorkTimeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        workList = new ArrayList<>();
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

    public void setBean(WorkDetailResponse.WorkDetailBean newBean) {
        mBean = newBean;
        workList.clear();
        beginLeave = mBean.leaveTime.begin.split(" ")[0];
        endLeave = mBean.leaveTime.begin.split(" ")[0];
        int count = mBean.list.size();
        for (int i = 0; i < count; i++) {
            WorkTime time = new WorkTime();
            WorkListBeen.InfoBean infoBean = mBean.list.get(i);
            time.beginTime = getTime(infoBean.getBeginTime());
            time.endTime = getTime(infoBean.getEndTime());
            workList.add(time);
        }
        notifyDataSetChanged();
    }

    public String getTime(String fulltime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(fulltime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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
        String currentTime = (hour > 9 ? hour + "" : "0" + hour) + ":" + (min == 0 ? "00" : "30");
        viewHolder.mTvTime.setText(currentTime);
        boolean flag = false;
        if (mBean != null) {
            if ("1".equals(mBean.isLeave)) {
                if (currentTime.compareTo(beginLeave) >= 0 && currentTime.compareTo(endLeave) <= 0) {//请假状态
                    viewHolder.mTvTime.setTextColor(Color.parseColor("#999999"));
                    viewHolder.mTvState.setVisibility(View.VISIBLE);
                    viewHolder.mLl.setBackgroundColor(Color.WHITE);
                    flag = true;
                }
            }
            if (workList.size() > 0) {//存在工作
                for (int i = 0; i < workList.size(); i++) {
                    if (currentTime.compareTo(workList.get(i).beginTime) == 0 && currentTime.compareTo(workList.get(i).endTime) <= 0) {
                        viewHolder.mLl.setBackgroundColor(Color.parseColor("#e55c5e"));
                        viewHolder.mTvTime.setTextColor(Color.WHITE);
                        viewHolder.mTvState.setVisibility(View.GONE);
                        flag = true;
                    } else if (currentTime.compareTo(workList.get(i).beginTime) > 0 && currentTime.compareTo(workList.get(i).endTime) <= 0) {
                        viewHolder.mLl.setBackgroundColor(Color.parseColor("#e55c5e"));
                        viewHolder.mTvTime.setTextColor(Color.WHITE);
                        viewHolder.mTvState.setVisibility(View.GONE);
                        flag = true;
                    }
                    if (flag) {
                        break;
                    }
                }
            }
        }
        if (!flag) {
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

    public class WorkTime {
        public String beginTime;
        public String endTime;
    }
}