package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class PingJIaAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;
    private RecyclerView recyclerView ;
    private PingJiaPicAdapter pingJiaPicAdapter ;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();

    public PingJIaAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_pingjia, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        int position = helper.getAdapterPosition();
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        recyclerView =  ((RecyclerView) (helper.getView(R.id.recyclerView)));
        pingJiaPicAdapter = new PingJiaPicAdapter(msgBeens,context);
        LinearLayoutManager layoutManager =   new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,10,0,0));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pingJiaPicAdapter);

    }

    @Override
    public void childViewClick(int position, View view) {

    }
}

