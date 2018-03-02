package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.HomeBeen;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class HomeListAdapter extends BaseQuickAdapter<HomeBeen.InfoBean, BaseViewHolder> {
    private Context context;
    public HomeListAdapter(@Nullable List<HomeBeen.InfoBean> data, Context context) {
        super(R.layout.item_home_list, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeen.InfoBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.state_img)));
        Glide.with(context).load(item.getService_pic()).skipMemoryCache(false).into(iconMsg);
        TextView title = ((TextView) (helper.getView(R.id.home_list_title)));
        title.setText(item.getName());
        TextView price = ((TextView) (helper.getView(R.id.home_list_price)));
        price.setText(item.getPrice());
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
