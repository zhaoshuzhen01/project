package com.lubandj.master.adapter;

import android.support.annotation.Nullable;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.TestBean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public class WorkSheetAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    public WorkSheetAdapter(@Nullable List<TestBean> data) {
        super(R.layout.item_test, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {

    }
}