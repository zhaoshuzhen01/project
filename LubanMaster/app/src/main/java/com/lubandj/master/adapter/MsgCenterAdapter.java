package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.TestBean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public class MsgCenterAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    private Context context;
    public MsgCenterAdapter(@Nullable List<TestBean> data, Context context) {
        super(R.layout.item_msg, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {

    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
