package com.lubandj.master.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.adapter.ChooseXingHaoAdapter;
import com.lubandj.master.adapter.HomePagerAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class ChooseXingHao extends LinearLayout implements BaseQuickAdapter.OnItemClickListener{


    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ChooseXingHaoAdapter homeListAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();

    public ChooseXingHao(Context context) {
        super(context);
        initView(context);
    }

    public ChooseXingHao(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChooseXingHao(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_home_table, this);
        ButterKnife.inject(this, view);
        for (int i=0;i<2;i++){
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        homeListAdapter = new ChooseXingHaoAdapter(msgBeens,context);
        homeListAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new  GridLayoutManager(context,4); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,10,0,10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeListAdapter);
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showShort(TApplication.context,position+"");
        msgBeens.get(position).setSelect(!msgBeens.get(position).isSelect());
        homeListAdapter.notifyDataSetChanged();
    }
}
