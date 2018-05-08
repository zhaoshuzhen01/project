package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.customer.bean.ServiceUserBean;
import com.lubandj.customer.order.NewOrderDetailsActivity;
import com.lubandj.master.R;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/29.
 */

public class EngineerAdapter extends BaseQuickAdapter<ServiceUserBean, BaseViewHolder> {
    private Context context;

    public EngineerAdapter(@Nullable List<ServiceUserBean> data, Context context) {
        super(R.layout.item_engineer, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceUserBean item) {
        ImageView iconMsg = ((helper.getView(R.id.iv_engineer_photo)));
        Glide.with(context).load(item.face).skipMemoryCache(false).into(iconMsg);
        TextView tvServiceName = helper.getView(R.id.tv_engineer_name);
        ImageView mIvPhone = helper.getView(R.id.iv_phone_icon);
        tvServiceName.setText(item.name);
        mIvPhone.setTag(item.tel);
        mIvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = (String) v.getTag();
                ((NewOrderDetailsActivity) context).onEngineerPhone(tel);
            }
        });
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
