package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/24.
 */

public class HomePagerAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;
    public HomePagerAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_home_pager, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        int position = helper.getAdapterPosition();

    }

    @Override
    public void childViewClick(int position, View view) {

    }
}

