package com.lubandj.master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    private List<SuggestionResult.SuggestionInfo> dataLit;

    public SelectAddressAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        dataLit = new ArrayList<>();
    }

    public void clearData() {
        dataLit.clear();
    }

    public void setData(List<SuggestionResult.SuggestionInfo> newList) {
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
        SuggestionResult.SuggestionInfo info = dataLit.get(position);
        viewHolder.mTvName.setText(info.key + "");
        viewHolder.mTvDetail.setText(info.district+"");
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
