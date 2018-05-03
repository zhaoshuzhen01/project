package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.customer.my.LeaveMsgActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.R;
import com.lubandj.master.adapter.BookOrderOdapter;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.BookOrderBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.httpbean.AddressListReponse;
import com.lubandj.master.httpbean.NetBookBeen;
import com.lubandj.master.httpbean.UidParamsRequest;
import com.lubandj.master.model.BookOrderModel;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BookOrderActivity extends TitleBaseActivity implements DataCall<BookOrderBeen> {
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
    @InjectView(R.id.show_address_lay)
    RelativeLayout show_address_lay;
    @InjectView(R.id.address_peo)
    TextView address_peo;
    @InjectView(R.id.address_adress)
    TextView address_adress;
    @InjectView(R.id.address_phone)
    TextView address_phone;
    @InjectView(R.id.tv_ordertime)
    TextView tv_ordertime;
    @InjectView(R.id.tv_marktext)
    TextView tv_marktext;

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
    private BookOrderModel bookOrderModel;
    private String addressId = "";

    private BookOrderOdapter bookOrderOdapter;
    private List<ShoppingCartBean> msgBeens = new ArrayList<>();
    private List<NetBookBeen.ItemsBean> items = new ArrayList<>();
    private NetBookBeen netBookBeen = new NetBookBeen();

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
        setAddress();
        bookOrderModel = new BookOrderModel(this, this);
        for (ShoppingCartBean bean : msgBeens) {
            NetBookBeen.ItemsBean itemsBean = new NetBookBeen.ItemsBean();
            itemsBean.setId(bean.getId());
            itemsBean.setService_id(bean.getService_id());
            itemsBean.setSpec_id(bean.getSpec_id());
            itemsBean.setService_name(bean.getShoppingName());
            itemsBean.setPrice(bean.getPrice() + "");
            itemsBean.setNum(bean.getCount());
            items.add(itemsBean);
        }
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

    @OnClick({R.id.choose_youhui, R.id.choose_time, R.id.choose_liuyan, R.id.tv_settlement, R.id.choose_address, R.id.show_address_lay})
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
                Intent intent1=new Intent(this, LeaveMsgActivity.class);
                startActivityForResult(intent1,101);
                break;
            case R.id.tv_settlement:
                if (show_address_lay.getVisibility() == View.GONE) {
                    ToastUtils.showShort(this, "请选择服务地址");
                    return;
                }
                if (tv_ordertime.getText().toString().equals("意向上门时间")) {
                    ToastUtils.showShort(this, "请选择意向上门时间");
                    return;
                }
                netBookBeen.setAddress_id(addressId);
                netBookBeen.setAmount(LocalleCarData.newInstance().getTotalPrice());
                netBookBeen.setCoupon_id(0);
                netBookBeen.setCoupon_amount(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                netBookBeen.setDatetime(sdf.format(new Date()));
                netBookBeen.setRemark(tv_marktext.getText().toString());
                netBookBeen.setItems(items);
                bookOrderModel.bookOrder(netBookBeen);
//                CheckStandActivity.startActivity(this);
                break;
            case R.id.show_address_lay:
            case R.id.choose_address:
                intent = new Intent(this, CustomAddressActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (TextUtils.isEmpty(CommonUtils.getAddressID()) && TextUtils.isEmpty(addressId)) {//这种情况说明预存的地址被删除了
                    show_address_lay.setVisibility(View.GONE);
                }
                if (data == null) {
                    return;
                }
                AddressBean bean = (AddressBean) data.getSerializableExtra("data");
                if (bean != null) {
                    show_address_lay.setVisibility(View.VISIBLE);
                    address_peo.setText("联系人 " + bean.linkman + "");
                    address_phone.setText(bean.phone);
                    if (TextUtils.isEmpty(bean.province)) {
                        bean.province = "";
                    }
                    String city = bean.province.equals(bean.city) ? bean.city : bean.province + bean.city;
                    address_adress.setText(city + bean.area + bean.housing_estate + bean.house_number);
                    CommonUtils.setAddress(bean.linkman + "," + bean.phone + "," + city + "," + bean.area + "," + bean.housing_estate + "," + bean.house_number);
                    addressId = bean.id + "";
                    CommonUtils.setAddressID(addressId);
                }
                break;
            case 303:
                if (requestCode == 303) {
                    if (resultCode == 100) {//获取时间返回
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
                    }
                }
                break;
            case 101:
                if(resultCode==10001&&data!=null){
                    tv_marktext.setText(data.getStringExtra("msg"));
                }
                break;
        }
    }

    @Override
    public void getServiceData(BookOrderBeen data) {
        CheckStandActivity.startActivity(this, data.getInfo().getId(),data.getInfo().getOrder_id(),data.getInfo().getPay_amount());
        //清理购物车和界面
        LocalleCarData.newInstance().clear();
        setResult(1010);
        finish();
    }

    /**
     * 设置地址
     */
    public void setAddress() {
        addressId = CommonUtils.getAddressID();
        String[] datas = CommonUtils.getAddress();
        //判断本地存储的地址是否有效，无效则清除
        if (addressId != null && datas != null && datas.length > 1) {
            UidParamsRequest request = new UidParamsRequest(CommonUtils.getUid());
            TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETADDRESS, request, new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    fastDismiss();
                    AddressListReponse bean = new AddressListReponse();
                    bean = (AddressListReponse) CommonUtils.generateEntityByGson(BookOrderActivity.this, s, bean);
                    if (bean != null) {
                        boolean flag = false;
                        for (int i = 0; i < bean.info.size(); i++) {
                            if (addressId.equals(bean.info.get(i).id + "")) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            String[] datas = CommonUtils.getAddress();
                            if (datas != null && datas.length > 1) {
                                show_address_lay.setVisibility(View.VISIBLE);
                                address_peo.setText("联系人 " + datas[0] + "");
                                address_phone.setText(datas[1] + "");
                                address_adress.setText(datas[2] + datas[3] + datas[4] + datas[5] + "");
                            }
                        } else {
                            addressId = "";
                            CommonUtils.setAddressID("");
                            CommonUtils.setAddress("");
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    fastDismiss();
//                    CommonUtils.fastShowError(BookOrderActivity.this, volleyError);
                }
            });
        }
    }
}