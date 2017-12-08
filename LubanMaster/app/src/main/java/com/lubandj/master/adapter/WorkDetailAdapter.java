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

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class WorkDetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public WorkDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = mInflater.inflate(R.layout.item_workdetail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    class ViewHolder {
//        public LinearLayout mLl;
//        public TextView mTvTime;
//        public TextView mTvState;


        public ViewHolder(View view) {
//            mLl = view.findViewById(R.id.ll_worktime);
//            mTvTime = view.findViewById(R.id.tv_worktime_time);
//            mTvState = view.findViewById(R.id.tv_worktime_state);
        }
    }
}
