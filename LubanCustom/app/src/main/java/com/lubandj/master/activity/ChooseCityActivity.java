package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.baselibrary.BaseRefreshActivity;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.ChooseCityAdapter;
import com.lubandj.master.adapter.HomePagerAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChooseCityActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private ChooseCityAdapter chooseCityAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChooseCityActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_choose_city;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText(R.string.choose_address);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        initData();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        chooseCityAdapter = new ChooseCityAdapter(msgBeens, this);
        chooseCityAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 3); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10, 10, 20, 10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chooseCityAdapter);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        finish();
    }
}
