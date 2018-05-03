package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.customer.bean.ServiceTotalBean;
import com.lubandj.master.R;
import com.lubandj.master.been.ServiceDetailBeen;
import com.lubandj.master.been.ShoppingCartBean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/29.
 */

public class ServiceTotalAdapter extends BaseQuickAdapter<ServiceTotalBean, BaseViewHolder> {
    private Context context;

    public ServiceTotalAdapter(@Nullable List<ServiceTotalBean> data, Context context) {
        super(R.layout.item_servicetotal, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ServiceTotalBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg = ((helper.getView(R.id.iv_show_pic)));
        Glide.with(context).load(item.service_icon).skipMemoryCache(false).into(iconMsg);
        TextView tvServiceName = helper.getView(R.id.tv_service_name);
        TextView tvServiceNum = helper.getView(R.id.tv_service_num);
        TextView tvServicePrice = helper.getView(R.id.tv_service_price);
        tvServiceName.setText(item.service_name);
        tvServiceNum.setText("x"+item.num);
        tvServicePrice.setText("¥" + item.amount);
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
