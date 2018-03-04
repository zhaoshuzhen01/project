package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
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
import com.lubandj.master.utils.CommonUtils;

import java.util.ArrayList;
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
    RelativeLayout choose_address ;
    @InjectView(R.id.tv_settlement)
    TextView tv_settlement;
    @InjectView(R.id.tv_show_price)
    TextView tv_show_price ;
    @InjectView(R.id.tv_show_price11)
    TextView tv_show_price11 ;
    @InjectView(R.id.show_address_lay)
    RelativeLayout show_address_lay;
    @InjectView(R.id.address_peo)
    TextView address_peo;
    @InjectView(R.id.address_adress)
    TextView address_adress;
    @InjectView(R.id.address_phone)
    TextView address_phone;
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
        msgBeens= LocalleCarData.newInstance().getShoppingBeenList();
        tv_settlement = findView(R.id.tv_settlement);
        tv_settlement.setOnClickListener(this);
        bookOrderOdapter = new BookOrderOdapter(msgBeens, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));

        recyclerView.setAdapter(bookOrderOdapter);
        String[]datas = CommonUtils.getAddress();
        if (datas!=null&&datas.length>1){
            show_address_lay.setVisibility(View.VISIBLE);
            address_peo.setText("联系人 "+datas[0]+"");
            address_phone.setText(datas[1]+"");
            address_adress.setText(datas[2]+datas[3]+datas[4]+datas[5]+"");
        }
        initData();
    }

    @Override
    public void initData() {
        tv_show_price.setText("¥" +LocalleCarData.newInstance().getTotalPrice()+"");
        tv_show_price11.setText("¥" +LocalleCarData.newInstance().getTotalPrice()+"");
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

    @OnClick({R.id.choose_youhui, R.id.choose_time, R.id.choose_liuyan,R.id.tv_settlement,R.id.choose_address,R.id.show_address_lay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_youhui:
                CouponsActivity.startActivity(this);
                break;
            case R.id.choose_time:
                break;
            case R.id.choose_liuyan:
                startActivity(new Intent(this, FeedBackInfoActivity.class));

                break;
            case R.id.tv_settlement:
                if (show_address_lay.getVisibility()==View.GONE){
                    ToastUtils.showShort(this,"请选择服务地址");
                    return;
                }
                CheckStandActivity.startActivity(this);
                break;
            case R.id.show_address_lay:
            case R.id.choose_address:
                Intent intent = new Intent(this, CustomAddressActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddressBean bean =(AddressBean) data.getSerializableExtra("data");
        if (bean!=null){
            show_address_lay.setVisibility(View.VISIBLE);
            address_peo.setText("联系人 "+bean.linkman+"");
            address_phone.setText(bean.phone);
            address_adress.setText(bean.city+bean.area+bean.address+bean.housing_estate+"");
            CommonUtils.setAddress(bean.linkman+","+bean.phone+","+bean.city+","+bean.area+","+bean.address+","+bean.housing_estate+"");
        }

    }
}
