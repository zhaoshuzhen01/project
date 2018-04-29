package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.activity.PhotoViewActivity;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.PingJiaBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class PingJIaAdapter extends BaseQuickAdapter<PingJiaBeen.InfoBean.ResultBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener{
    private Context context;
    private RecyclerView recyclerView ;
    private PingJiaPicAdapter pingJiaPicAdapter ;
    private List<PingJiaBeen.InfoBean.ResultBean> msgBeens = new ArrayList<>();

    public PingJIaAdapter(@Nullable List<PingJiaBeen.InfoBean.ResultBean> data, Context context) {
        super(R.layout.item_pingjia, data);
        this.context = context ;
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new PingJiaBeen.InfoBean.ResultBean());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, PingJiaBeen.InfoBean.ResultBean item) {
        int position = helper.getAdapterPosition();
        recyclerView =  ((RecyclerView) (helper.getView(R.id.recyclerView)));
        List<String>imgs = item.getImg();
        pingJiaPicAdapter = new PingJiaPicAdapter(imgs,context);
        pingJiaPicAdapter.setIndex(position);
        pingJiaPicAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager =   new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0,5,0,0));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pingJiaPicAdapter);

    }

    @Override
    public void childViewClick(int position, View view) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showShort(context,position+""+adapter.getIndex());
        Intent intent = new Intent(context, PhotoViewActivity.class);
       /* Bundle bundle = new Bundle();
        bundle.putSerializable("dataBean", mData);
        intent.putExtras(bundle);*/
        intent.putExtra("currentPosition", position);
        context.startActivity(intent);
    }
}

