package com.lubandj.master.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.lubandj.master.R;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class SelectAddressAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PoiInfo> dataLit;
    private boolean isAuto;

    public SelectAddressAdapter(Context context, boolean isAuto) {
        mInflater = LayoutInflater.from(context);
        dataLit = new ArrayList<>();
        this.isAuto = isAuto;
    }

    public void clearData() {
        dataLit.clear();
        notifyDataSetChanged();
    }

    public void setData(List<PoiInfo> newList) {
        dataLit = newList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return dataLit.size();
    }

    @Override
    public Object getItem(int position) {
        return dataLit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_selectaddress, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PoiInfo info = dataLit.get(position);
        if (isAuto && position == 0) {
//            viewHolder.iv_gps_icon.setVisibility(View.VISIBLE);
            viewHolder.mTvName.setTextColor(Color.parseColor("#D13600"));
            viewHolder.mTvDetail.setTextColor(Color.parseColor("#D13600"));
            viewHolder.mTvName.setText("[当前位置]" + info.name);
            viewHolder.mTvDetail.setText(info.address);
        } else {
//            viewHolder.iv_gps_icon.setVisibility(View.INVISIBLE);
            viewHolder.mTvName.setTextColor(Color.parseColor("#4A4A4A"));
            viewHolder.mTvDetail.setTextColor(Color.parseColor("#ffa9a9a9"));
            viewHolder.mTvName.setText(info.name);
            viewHolder.mTvDetail.setText(info.address);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView mTvName;
        public TextView mTvDetail;

        public ViewHolder(View view) {
            mTvName = view.findViewById(R.id.tv_place_name);
            mTvDetail = view.findViewById(R.id.tv_place_detail);
        }
    }
}
