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
import com.lubandj.master.been.ServiceDetailBeen;

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
    private XingHao xingHao ;
    private List<ServiceDetailBeen.InfoBean.ItemsBean> msgBeens = new ArrayList<>();
    private Context context ;
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
        this.context = context ;
        View view = LayoutInflater.from(context).inflate(R.layout.view_home_table, this);
        ButterKnife.inject(this, view);
        GridLayoutManager manager = new  GridLayoutManager(context,4); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10,10,0,10));
        recyclerView.setHasFixedSize(true);
    }

    public void setData(List<ServiceDetailBeen.InfoBean.ItemsBean> msgBeens,XingHao xingHao){
        this.msgBeens = msgBeens ;
        this.xingHao = xingHao ;
        homeListAdapter = new ChooseXingHaoAdapter(msgBeens,context);
        homeListAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(homeListAdapter);
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (ServiceDetailBeen.InfoBean.ItemsBean bean:msgBeens){
            bean.setSelect(false);
        }
        msgBeens.get(position).setSelect(!msgBeens.get(position).isSelect());
        homeListAdapter.notifyDataSetChanged();
        if (msgBeens.get(position).isSelect()){
            xingHao.getXingHao(msgBeens.get(position));
        }
    }

    public interface XingHao{
        void getXingHao(ServiceDetailBeen.InfoBean.ItemsBean xinghao);
    }
}
