package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.customer.bean.ServiceTotalBean;
import com.lubandj.customer.httpbean.CancelOrderInfoResponse;
import com.lubandj.master.R;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/29.
 */

public class CancelOrderServiceAdapter extends BaseQuickAdapter<CancelOrderInfoResponse.CancelOrderService, BaseViewHolder> {
    private Context context;

    public CancelOrderServiceAdapter(@Nullable List<CancelOrderInfoResponse.CancelOrderService> data, Context context) {
        super(R.layout.item_servicetotal, data);
        this.context = context;
    }


    public void setData() {

    }

    @Override
    protected void convert(BaseViewHolder helper, CancelOrderInfoResponse.CancelOrderService item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg = ((helper.getView(R.id.iv_show_pic)));
        Glide.with(context).load(item.service_icon).skipMemoryCache(false).into(iconMsg);
        TextView tvServiceName = helper.getView(R.id.tv_service_name);
        TextView tvServiceNum = helper.getView(R.id.tv_service_num);
        TextView tvServicePrice = helper.getView(R.id.tv_service_price);
        tvServiceName.setText(item.name);
        tvServiceNum.setText("x"+item.total);
        tvServicePrice.setText("¥" + item.amount);
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
