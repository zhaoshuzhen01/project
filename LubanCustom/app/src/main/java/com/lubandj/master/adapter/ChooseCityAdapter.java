package com.lubandj.master.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/26.
 */

public class ChooseCityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    public ChooseCityAdapter(@Nullable List<String> data, Context context) {
        super(R.layout.item_city_address, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       final int position = helper.getAdapterPosition();
        TextView msgContent= ((TextView) (helper.getView(R.id.home_list_title)));
        msgContent.setText(item+"");
        msgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(position, view);
            }
        });
    }

    @Override
    public void childViewClick(int position, View view) {
        Canstance.CITY =getItem(position);
        ((Activity)context).finish();
    }
}

