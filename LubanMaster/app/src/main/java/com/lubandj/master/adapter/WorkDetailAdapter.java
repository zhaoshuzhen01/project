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

import java.util.ArrayList;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class WorkDetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<WorkListBeen.InfoBean> dataList;

    public WorkDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public void setData(ArrayList<WorkListBeen.InfoBean> newList) {
        dataList = newList;
        notifyDataSetChanged();
    }

    public void removeId(String id) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getId().equals(id)) {
                dataList.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        WorkListBeen.InfoBean info = dataList.get(position);
        viewHolder.mTvNum.setText("工单号:    " + info.getTicketSn());
        viewHolder.mTvTime.setText(info.getTimeStr());
        viewHolder.mTvPlace.setText(info.getAddress());
        return convertView;
    }


    class ViewHolder {
        public TextView mTvNum;
        public TextView mTvTime;
        public TextView mTvPlace;


        public ViewHolder(View view) {
            mTvNum = view.findViewById(R.id.tv_workdetail_num);
            mTvTime = view.findViewById(R.id.tv_workdetail_time);
            mTvPlace = view.findViewById(R.id.tv_workdetail_place);
        }
    }
}
