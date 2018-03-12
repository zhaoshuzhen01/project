package com.lubandj.customer.order;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lubandj.customer.base.PhonePermissionActivity;
import com.lubandj.customer.widget.OrderItemView;
import com.lubandj.customer.widget.OrderTraceView;
import com.lubandj.customer.widget.RefundDetailsView;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.BookOrderOdapter;
import com.lubandj.master.been.BookOrderBeen;
import com.lubandj.master.been.OrderDetailBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.been.WorkSheetDetailBean;
import com.lubandj.master.httpbean.NetBookBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetDetailsActivityPhone;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OrderDetailsActivity extends PhonePermissionActivity {


    @InjectView(R.id.iv_state_icon)
    ImageView ivStateIcon;
    @InjectView(R.id.tv_state_desc)
    TextView tvStateDesc;
    @InjectView(R.id.iv_right_arrow)
    ImageView ivRightArrow;
    @InjectView(R.id.tv_refresh_time)
    TextView tvRefreshTime;
    @InjectView(R.id.ll_state)
    RelativeLayout llState;
    @InjectView(R.id.ll_order_item)
    LinearLayout llOrderItem;
    @InjectView(R.id.ll_engineer_info)
    LinearLayout llEngineerInfo;
    @InjectView(R.id.tv_sale_price)
    TextView tvSalePrice;
    @InjectView(R.id.tv_order_price_total)
    TextView tvOrderPriceTotal;
    @InjectView(R.id.iv_engineer_photo)
    ImageView ivEngineerPhoto;
    @InjectView(R.id.tv_engineer_name)
    TextView tvEngineerName;
    @InjectView(R.id.iv_phone_icon)
    ImageView ivPhoneIcon;
    @InjectView(R.id.ll_refund_details)
    LinearLayout llRefundDetails;
    @InjectView(R.id.tv_contact_name)
    TextView tvContactName;
    @InjectView(R.id.tv_contact_phone)
    TextView tvContactPhone;
    @InjectView(R.id.tv_service_address)
    TextView tvServiceAddress;
    @InjectView(R.id.tv_comment_info)
    TextView tvCommentInfo;
    @InjectView(R.id.tv_order_num)
    TextView tvOrderNum;
    @InjectView(R.id.tv_copy)
    TextView tvCopy;
    @InjectView(R.id.tv_refund_details)
    TextView tvRefundDetails;
    @InjectView(R.id.tv_place_time)
    TextView tvPlaceTime;
    @InjectView(R.id.tv_pay_type)
    TextView tvPayType;
    @InjectView(R.id.btn_cancel_order)
    Button btnCancelOrder;
    @InjectView(R.id.btn_go_to_pay)
    Button btnGoToPay;
    @InjectView(R.id.ll_small_btn)
    FrameLayout llSmallBtn;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.btn_buy_again)
    Button btnBuyAgain;
    public static final String KEY_DETAILS_ID = "details_id";
    private String workSheetId;
    private BookOrderOdapter bookOrderOdapter;
    private List<ShoppingCartBean> msgBeens = new ArrayList<>();
    private  List<OrderDetailBeen.InfoBean.ItemsBean> items = new ArrayList<>();
    private PopupWindow orderTracePop;
    private int mStatus = Canstance.TYPE_ORDER_DETAILS_IN_THE_SINGLE;
private OrderDetailBeen msgCenterBeen ;
    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ButterKnife.inject(this);
        setTitleText(R.string.txt_order_details);
        setBackImg(R.drawable.back_mark);
        setOKImg(R.drawable.ic_service);
        setStatus(mStatus);
        workSheetId = getIntent().getStringExtra(KEY_DETAILS_ID);
        bookOrderOdapter = new BookOrderOdapter(msgBeens, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));

        recyclerView.setAdapter(bookOrderOdapter);
        initData();
    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", workSheetId);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_DETAILS, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
                try {
                     msgCenterBeen = new Gson().fromJson(s, OrderDetailBeen.class);
                    if (msgCenterBeen.getCode() == 0) {
                        items = msgCenterBeen.getInfo().getItems();
                        for (OrderDetailBeen.InfoBean.ItemsBean bean:items){
                            ShoppingCartBean bean1 = new ShoppingCartBean(0,bean.getService_name(),"",0,0.0,Integer.parseInt(bean.getNum()),0,0,0);
//                            bean1.setImageUrl(bean.getService_icon());
                            msgBeens.add(bean1);
                        }
                        bookOrderOdapter.notifyDataSetChanged();
                        tvOrderPriceTotal.setText("¥ " + msgCenterBeen.getInfo().getPay_amount()+"");
                        tvContactName.setText(msgCenterBeen.getInfo().getContact_name()+"");
                        tvContactPhone.setText(msgCenterBeen.getInfo().getContact_mobile()+"");
                        tvServiceAddress.setText(msgCenterBeen.getInfo().getAddress()+"");
                        tvCommentInfo.setText("备注信息");
                        tvOrderNum.setText(msgCenterBeen.getInfo().getOrder_id()+"");
                        tvPlaceTime.setText(msgCenterBeen.getInfo().getDatatime()+"");
                    }else if(msgCenterBeen.getCode()==104){
                        CommonUtils.tokenNullDeal(OrderDetailsActivity.this);
                    } else {
                        ToastUtils.showShort(OrderDetailsActivity.this, msgCenterBeen.getMessage());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                CommonUtils.fastShowError(OrderDetailsActivity.this,volleyError);
            }
        });

    }

    private int refundCount = 1;

    private void setStatus(int status) {
        switch (status) {
            case Canstance.TYPE_ORDER_DETAILS_CANCELED:
                llEngineerInfo.setVisibility(View.GONE);
                break;
            case Canstance.TYPE_ORDER_DETAILS_IN_THE_SINGLE:
                llEngineerInfo.setVisibility(View.GONE);
                refundCount = 2;
                break;
            case Canstance.TYPE_ORDER_DETAILS_COMPLETED:
                break;
            case Canstance.TYPE_ORDER_DETAILS_PAY_OVERTIME:
                llEngineerInfo.setVisibility(View.GONE);
                refundCount = 0;
                tvRefundDetails.setVisibility(View.GONE);
                break;
            case Canstance.TYPE_ORDER_DETAILS_NO_PAYMENT:
                llEngineerInfo.setVisibility(View.GONE);
                refundCount = 0;
                tvRefundDetails.setVisibility(View.GONE);
                llSmallBtn.setVisibility(View.VISIBLE);
                btnBuyAgain.setVisibility(View.GONE);
                break;
            case Canstance.TYPE_ORDER_DETAILS_WAIT_SERVICE:
                llEngineerInfo.setVisibility(View.GONE);
                refundCount = 0;
                tvRefundDetails.setVisibility(View.GONE);
                btnBuyAgain.setText(R.string.txt_cancel_order);
                break;
            case Canstance.TYPE_ORDER_DETAILS_ON_ROAD:
                refundCount = 0;
                tvRefundDetails.setVisibility(View.GONE);
                btnBuyAgain.setText(R.string.txt_cancel_order);
                break;
            case Canstance.TYPE_ORDER_DETAILS_IN_SERVICE:
                llEngineerInfo.setVisibility(View.GONE);
                refundCount = 0;
                tvRefundDetails.setVisibility(View.GONE);
                btnBuyAgain.setText(R.string.txt_cancel_order);
                break;
            default:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                String serviceNum = "4006-388-818";
                callToClient(serviceNum, String.format(getString(R.string.txt_confirm_call_service), serviceNum));
                break;
            case R.id.tv_close_order_trace:
                if (orderTracePop != null) {
                    orderTracePop.dismiss();
                }
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.ll_state, R.id.iv_phone_icon, R.id.tv_copy, R.id.btn_cancel_order, R.id.btn_go_to_pay, R.id.btn_buy_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_state:
                showOrderTracePop();
                break;
            case R.id.iv_phone_icon:
                callToClient("10086", String.format(getString(R.string.txt_make_sure_phone), "10086"));
                break;
            case R.id.tv_copy:
                copy("10086");
                break;
            case R.id.btn_cancel_order:
                ToastUtils.showShort(this, R.string.txt_cancel_order);
                break;
            case R.id.btn_go_to_pay:
                ToastUtils.showShort(this, R.string.txt_go_to_pay);
                break;
            case R.id.btn_buy_again:
                String text = btnBuyAgain.getText().toString();
                ToastUtils.showShort(this, text);
                if (TextUtils.equals(text, getString(R.string.txt_buy_again))) {

                } else if (TextUtils.equals(text, getString(R.string.txt_cancel_order))) {
                    new AlertDialog(this)
                            .builder()
                            .setMsg(getString(R.string.txt_confirm_cancel_order))
                            .setPositiveButton(getString(R.string.txt_confirm_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            })
                            .setNegativeButton(getString(R.string.txt_give_up_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();

                }
                break;
        }
    }

    public void showOrderTracePop() {
        if (orderTracePop == null) {
            OrderTraceView orderTraceView = new OrderTraceView(this, this);
            orderTracePop = new PopupWindow(orderTraceView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            orderTracePop.setTouchable(true);
            orderTracePop.setOutsideTouchable(true);
            orderTracePop.setFocusable(true);
            orderTracePop.setAnimationStyle(R.style.ActionSheetDialogAnimation);
            orderTracePop.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
            orderTracePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            orderTracePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    darkenBackground(1f);
                }
            });
        }
        orderTracePop.showAtLocation(ivBaseTitleBack, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        darkenBackground(0.5f);
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgColor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgColor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }


}
