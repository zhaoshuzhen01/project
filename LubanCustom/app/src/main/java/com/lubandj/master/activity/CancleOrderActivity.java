package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.customer.my.FeedBackInfoActivity;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.R;
import com.lubandj.master.adapter.BookOrderOdapter;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.BookOrderBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.httpbean.NetBookBeen;
import com.lubandj.master.model.BookOrderModel;
import com.lubandj.master.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CancleOrderActivity extends TitleBaseActivity implements DataCall<BookOrderBeen> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;


    private BookOrderOdapter bookOrderOdapter;
    private List<ShoppingCartBean> msgBeens = new ArrayList<>();
    private  List<NetBookBeen.ItemsBean> items = new ArrayList<>();
    private  NetBookBeen netBookBeen = new NetBookBeen();
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CancleOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cancle_order;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("取消下单");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        msgBeens = LocalleCarData.newInstance().getShoppingBeenList();
        bookOrderOdapter = new BookOrderOdapter(msgBeens, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));

        recyclerView.setAdapter(bookOrderOdapter);

        for (ShoppingCartBean bean:msgBeens){
            NetBookBeen.ItemsBean itemsBean= new NetBookBeen.ItemsBean();
            itemsBean.setId(bean.getId());
            itemsBean.setService_id(bean.getService_id());
            itemsBean.setSpec_id(bean.getSpec_id());
            itemsBean.setService_name(bean.getShoppingName());
            itemsBean.setPrice(bean.getPrice()+"");
            itemsBean.setNum(bean.getCount());
            items.add(itemsBean);
        }
        initData();
    }

    @Override
    public void initData() {
       /* tv_show_price.setText("¥" + LocalleCarData.newInstance().getTotalPrice() + "");
        tv_show_price11.setText("¥" + LocalleCarData.newInstance().getTotalPrice() + "");*/
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.in_get, R.id.choose_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_get:
                break;
            case R.id.choose_address:

                break;
        }
    }


    @Override
    public void getServiceData(BookOrderBeen data) {
        CheckStandActivity.startActivity(this,data.getInfo().getId(),data.getInfo().getOrder_id());
    }
}