package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.lubandj.customer.my.FeedBackInfoActivity;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.BookOrderOdapter;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.CarListBeen;
import com.lubandj.master.been.HomeBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.model.CarListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BookOrderActivity extends TitleBaseActivity {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.choose_youhui)
    RelativeLayout chooseYouhui;
    @InjectView(R.id.choose_time)
    RelativeLayout chooseTime;
    @InjectView(R.id.choose_liuyan)
    RelativeLayout chooseLiuyan;
    @InjectView(R.id.choose_address)
    RelativeLayout choose_address;
    @InjectView(R.id.tv_settlement)
    TextView tv_settlement;
    @InjectView(R.id.tv_show_price)
    TextView tv_show_price;
    @InjectView(R.id.tv_show_price11)
    TextView tv_show_price11;
    @InjectView(R.id.tv_ordertime)
    TextView tv_ordertime;

    @InjectView(R.id.choose_serice_address_lay)
    RelativeLayout choose_address_none;
    @InjectView(R.id.choose_address_has)
    LinearLayout choose_address_has;
    @InjectView(R.id.tv_name)
    TextView tv_name;
    @InjectView(R.id.tv_phone)
    TextView tv_phone;
    @InjectView(R.id.tv_address)
    TextView tv_address;

    private BookOrderOdapter bookOrderOdapter;
    private List<ShoppingCartBean> msgBeens = new ArrayList<>();

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
        msgBeens = LocalleCarData.newInstance().getShoppingBeenList();
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
        tv_show_price.setText("¥" + LocalleCarData.newInstance().getTotalPrice() + "");
        tv_show_price11.setText("¥" + LocalleCarData.newInstance().getTotalPrice() + "");
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

    @OnClick({R.id.choose_youhui, R.id.choose_time, R.id.choose_liuyan, R.id.tv_settlement, R.id.choose_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_youhui:
                CouponsActivity.startActivity(this);
                break;
            case R.id.choose_time:
                Intent intent = new Intent(BookOrderActivity.this, OrderCalendarActivity.class);
                startActivityForResult(intent, 303);
                break;
            case R.id.choose_liuyan:
                startActivity(new Intent(this, FeedBackInfoActivity.class));

                break;
            case R.id.tv_settlement:
                CheckStandActivity.startActivity(this);
                break;
            case R.id.choose_address:
                Intent intent2 = new Intent(BookOrderActivity.this, CustomAddressActivity.class);
                intent2.putExtra("mark", "select");
                startActivityForResult(intent2, 304);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 100) {
            return;
        }
        if (requestCode == 303) {//获取时间返回
            String datetime = data.getStringExtra("time");//格式化的时间
            String week = data.getStringExtra("week");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = sdf.parse(datetime);
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日 HH:mm");
                tv_ordertime.setText(sdf2.format(date).replace(" ", "(" + week + ") "));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 304) {//获取地址返回
            AddressBean bean = (AddressBean) data.getSerializableExtra("address");
            choose_address_none.setVisibility(View.INVISIBLE);
            choose_address_has.setVisibility(View.VISIBLE);
            tv_name.setText("联系人：" + bean.linkman);
            tv_phone.setText(bean.phone);
            tv_address.setText((bean.province.equals(bean.city) ? bean.city : bean.province + bean.city) + bean.area + bean.housing_estate + bean.linkman);
        }
    }

    /*   @Override
    public void getDataLists(List<CarListBeen.InfoBean> datas) {
//        pullToRefreshAndPushToLoadView.finishLoading();
        if (msgBeens.size()==0&&datas==null){
            return;
        }
        msgBeens.clear();
        msgBeens.addAll(datas);
        bookOrderOdapter.notifyDataSetChanged();
    }*/
}
