package com.lubandj.master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lubandj.master.R;

import java.util.List;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-4
 * company:九州宏图
 */

public class SelectAddressAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public SelectAddressAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_selectaddress, null);
        }

        return convertView;
    }
}
