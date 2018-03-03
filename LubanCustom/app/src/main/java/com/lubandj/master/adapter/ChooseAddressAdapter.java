package com.lubandj.master.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.activity.AddAddressActivity;
import com.lubandj.master.activity.CustomAddressActivity;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/9.
 */

public class ChooseAddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    private Context context;
    private ArrayList<AddressBean> dataList;

    public ChooseAddressAdapter(@Nullable List<AddressBean> data, Context context) {
        super(R.layout.item_choose_address, data);
        this.context = context;
    }

    public void setData() {

    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        final int position = helper.getAdapterPosition();
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.tv_commodity_delete)));
        TextView tvCommodityAttr = ((TextView) (helper.getView(R.id.tv_commodity_attr)));
        TextView tvCommodityNum = ((TextView) (helper.getView(R.id.tv_commodity_num)));
        iconMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(position, view);
            }
        });

        tvCommodityAttr.setText(item.city + item.area + "    " + item.housing_estate + item.house_number);
        tvCommodityNum.setText(item.linkman + "|" + item.phone);
    }

    @Override
    public void childViewClick(int position, View view) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("bean", getItem(position));
        ((Activity) context).startActivityForResult(intent, 1001);
    }
}

