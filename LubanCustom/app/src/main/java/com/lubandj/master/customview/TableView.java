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

import com.lubandj.master.R;
import com.lubandj.master.adapter.HomeListAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class TableView extends LinearLayout {


    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HomeListAdapter homeListAdapter;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();

    public TableView(Context context) {
        super(context);
        initView(context);
    }

    public TableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_home_table, this);
        ButterKnife.inject(this, view);
        for (int i=0;i<6;i++){
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        homeListAdapter = new HomeListAdapter(msgBeens,context);
        GridLayoutManager manager = new  GridLayoutManager(context,3); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeListAdapter);
    }


    //初始化ViewPager
    public void initViewPager(FragmentManager fm) {

    }
}
