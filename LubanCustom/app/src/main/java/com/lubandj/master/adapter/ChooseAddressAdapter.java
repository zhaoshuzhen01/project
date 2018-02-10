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
import com.lubandj.master.R;
import com.lubandj.master.activity.AddAddressActivity;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/9.
 */

public class ChooseAddressAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;
    public ChooseAddressAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_choose_address, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        final int position = helper.getAdapterPosition();
        ImageView iconMsg =  ((ImageView) (helper.getView(R.id.tv_commodity_delete)));
        TextView tvCommodityAttr = ((TextView)(helper.getView(R.id.tv_commodity_attr)));
        TextView tvCommodityNum = ((TextView)(helper.getView(R.id.tv_commodity_num)));
        iconMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(position,view);
            }
        });

    }

    @Override
    public void childViewClick(int position, View view) {
        AddAddressActivity.startActivity(context);
    }
}

