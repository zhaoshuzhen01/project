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
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ServiceDetailBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceAdapter extends BaseQuickAdapter<ServiceDetailBeen.InfoBean.ItemsBean, BaseViewHolder> {
    private Context context;
    public IntroduceAdapter(@Nullable List<ServiceDetailBeen.InfoBean.ItemsBean> data, Context context) {
        super(R.layout.item_introduce_fragment, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDetailBeen.InfoBean.ItemsBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.state_img)));

        Glide.with(context).load(item.getItem_pic()).skipMemoryCache(false).into(iconMsg);
        TextView title = ((TextView) (helper.getView(R.id.home_list_title)));
        title.setText(item.getItem_name());
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
