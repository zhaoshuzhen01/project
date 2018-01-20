package com.lubandj.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.been.LeaveBean;
import com.lubandj.master.been.WorkDayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2018-1-1
 * company:九州宏图
 */

public class LeaveRecordAdapter extends RecyclerView.Adapter<LeaveRecordAdapter.WorkDateViewHolder> {
    private LayoutInflater mInflater;
    private List<LeaveBean> dataList;

    public LeaveRecordAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
    }

    public void setData(List<LeaveBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public WorkDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkDateViewHolder viewHolder = new WorkDateViewHolder(mInflater.inflate(R.layout.item_leaverecord, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WorkDateViewHolder holder, int position) {
        LeaveBean bean = dataList.get(position);
        holder.mTvTime.setText(bean.applyTime);
        holder.mTvStartTime.setText(bean.beginTime + " " + bean.beginText);
        holder.mTvEndTime.setText(bean.endTime + " " + bean.endText);
        holder.mTvReason.setText(bean.reason);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class WorkDateViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvTime;
        public TextView mTvStartTime;
        public TextView mTvEndTime;
        public TextView mTvReason;

        public WorkDateViewHolder(View itemView) {
            super(itemView);
            mTvTime = itemView.findViewById(R.id.tv_leave_time);
            mTvStartTime = itemView.findViewById(R.id.tv_leave_starttime);
            mTvEndTime = itemView.findViewById(R.id.tv_leave_endtime);
            mTvReason = itemView.findViewById(R.id.tv_leave_reason);
        }
    }
}
