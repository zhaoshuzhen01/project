package com.example.baselibrary.photomanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baselibrary.R;

import java.util.List;

/**
 * Created by lile on 2017/3/21 0021.
 */
public class TagsAdapter extends BaseAdapter {

    /**
     * View引用的holder
     */
    private ViewHolder viewHolder;
    private Context context;
    private List<String> infoList = null;

    public TagsAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    /**
     * 设置数据列表
     *
     * @param infoList
     */
    public void setList(List<String> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (infoList != null) {
            return infoList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (infoList != null) {
            infoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup praent) {
        // TODO Auto-generated method stub
        try {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.text_adapter_laoyout, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.updateView(position);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tags_txt;

        /**
         * ViewHolder构造函数
         *
         * @param v
         */
        public ViewHolder(View v) {
            initView(v);
        }

        private void initView(View v) {
            // TODO Auto-generated method stub
            tags_txt = (TextView) v.findViewById(R.id.tags_txt);
        }

        /**
         * 更新view
         * @param position
         */
        public void updateView(int position) {
            String s=infoList.get(position);
            tags_txt.setText(s);
        }
    }
}
