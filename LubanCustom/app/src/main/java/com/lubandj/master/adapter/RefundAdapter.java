package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.customer.bean.RefundBean;
import com.lubandj.customer.bean.ServiceTotalBean;
import com.lubandj.master.R;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/29.
 */

public class RefundAdapter extends BaseQuickAdapter<RefundBean, BaseViewHolder> {
    private Context context;

    public RefundAdapter(@Nullable List<RefundBean> data, Context context) {
        super(R.layout.item_refund, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundBean item) {
        TextView tvNo = helper.getView(R.id.tv_refund_no);
        tvNo.setText(item.refund_no);
        TextView tvService = helper.getView(R.id.tv_refund_service);
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < item.services.size(); i++) {
            if (i == 0)
                sb.append(item.services.get(i).service_name + "X" + item.services.get(i).num);
            else
                sb.append("\n" + item.services.get(i).service_name + "X" + item.services.get(i).num);
        }
        tvService.setText(sb.toString());
        TextView tvBack = helper.getView(R.id.tv_refund_back);
        tvBack.setText(item.breach_amount);
        TextView tvTotal = helper.getView(R.id.tv_total_refund);
        tvTotal.setText(item.amount);
        TextView tvStart = helper.getView(R.id.tv_refund_start);
        tvStart.setText(item.created_time);
        TextView tvState = helper.getView(R.id.tv_refund_state);
        tvState.setText(item.status);
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
