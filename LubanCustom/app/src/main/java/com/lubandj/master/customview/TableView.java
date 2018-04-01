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

import com.example.baselibrary.HomeBeen;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.activity.ServiceDetailActivity;
import com.lubandj.master.adapter.HomeListAdapter;
import com.lubandj.master.adapter.HomePagerAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class TableView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener {


    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HomePagerAdapter homeListAdapter;
    private List<HomeBeen.InfoBean> msgBeens = new ArrayList<>();
private Context mcontext ;
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
        mcontext = context ;
        View view = LayoutInflater.from(context).inflate(R.layout.view_home_table, this);
        ButterKnife.inject(this, view);
        homeListAdapter = new HomePagerAdapter(msgBeens, context);
        homeListAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(context, 3); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 10, 10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeListAdapter);
    }


    //初始化ViewPager
    public void setData(List<HomeBeen.InfoBean> mdata) {
        msgBeens.addAll(mdata);
        homeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ServiceDetailActivity.startActivity(mcontext, msgBeens.get(position).getService_id());

    }
}
