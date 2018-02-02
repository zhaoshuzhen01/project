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
import com.lubandj.master.R;
import com.lubandj.master.adapter.BookOrderOdapter;
import com.lubandj.master.adapter.MsgCenterAdapter;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BookOrderActivity extends TitleBaseActivity {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BookOrderOdapter bookOrderOdapter;
    private TextView tv_settlement;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BookOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_book_order;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("预约下单");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        tv_settlement = findView(R.id.tv_settlement);
        tv_settlement.setOnClickListener(this);
        bookOrderOdapter = new BookOrderOdapter(msgBeens, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));

        recyclerView.setAdapter(bookOrderOdapter);
        initData();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 5; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
        bookOrderOdapter.notifyDataSetChanged();
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_settlement:
                CheckStandActivity.startActivity(this);
                break;
        }
    }
}
