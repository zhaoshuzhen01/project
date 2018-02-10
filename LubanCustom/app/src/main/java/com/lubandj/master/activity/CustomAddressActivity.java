package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.ChooseAddressAdapter;
import com.lubandj.master.adapter.ChooseCityAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CustomAddressActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.fankui_button)
    TextView fankuiButton;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();
    private ChooseAddressAdapter chooseCityAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomAddressActivity.class);
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
        return R.layout.activity_custom_address;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("我的地址");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        initData();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        chooseCityAdapter = new ChooseAddressAdapter(msgBeens, this);
        chooseCityAdapter.setOnItemClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10, 10, 20, 10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chooseCityAdapter);
    }

    @OnClick(R.id.fankui_button)
    public void onClick() {
        AddAddressActivity.startActivity(this);
    }



    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        finish();
    }

    @Override
    public void onClick(View view) {

    }
}