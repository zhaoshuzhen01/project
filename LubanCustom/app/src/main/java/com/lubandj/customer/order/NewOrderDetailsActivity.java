package com.lubandj.customer.order;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.Gson;
import com.lubandj.customer.bean.OrderDetailBean;
import com.lubandj.customer.bean.RefundBean;
import com.lubandj.customer.bean.ServiceTotalBean;
import com.lubandj.customer.httpbean.OrderDetailResponse;
import com.lubandj.customer.widget.OrderTraceView;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.activity.CancleOrderActivity;
import com.lubandj.master.activity.CheckStandActivity;
import com.lubandj.master.adapter.RefundAdapter;
import com.lubandj.master.adapter.ServiceTotalAdapter;
import com.lubandj.master.databinding.ActivityNeworderdetailsBinding;
import com.lubandj.master.httpbean.HttpDetailOrder;
import com.lubandj.master.my.PermissionActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class NewOrderDetailsActivity extends PermissionActivity {
    public static final String KEY_DETAILS_ID = "details_id";
    private String workSheetId;
    private ServiceTotalAdapter mServiceTotalAdapter;
    private RefundAdapter mRefundAdapter;
    private PopupWindow orderTracePop;
    private int mStatus = Canstance.TYPE_ORDER_DETAILS_IN_THE_SINGLE;
    private ActivityNeworderdetailsBinding mBinding;
    private OrderDetailBean mBean;
    private List<ServiceTotalBean> itemList = new ArrayList<>();
    private List<RefundBean> refundList = new ArrayList<>();

//    private String serviceNum = "4006-388-818";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_neworderdetails);
        initView();

    }

    @Override
    public int getLayout() {
        return 0;
    }

    public void initView() {
        workSheetId = getIntent().getStringExtra(KEY_DETAILS_ID);
        itemList = new ArrayList<>();
        mServiceTotalAdapter = new ServiceTotalAdapter(itemList, this);
        mBinding.rvItems.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvItems.setHasFixedSize(true);
        mBinding.rvItems.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));
        mBinding.rvItems.setAdapter(mServiceTotalAdapter);


        mRefundAdapter = new RefundAdapter(refundList, this);
        mBinding.rvRefund.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRefund.setHasFixedSize(true);
        mBinding.rvRefund.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));
        mBinding.rvRefund.setAdapter(mRefundAdapter);

        initData();
    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        HttpDetailOrder detailOrder = new HttpDetailOrder();
        detailOrder.id = workSheetId;
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_DETAILS, detailOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                try {
                    OrderDetailResponse response = new Gson().fromJson(s, OrderDetailResponse.class);
                    if (response.code == 0) {
                        if (response.info != null) {
                            mBean = response.info;
                            setData();
                        }
                    } else if (response.code == 104) {
                        CommonUtils.tokenNullDeal(NewOrderDetailsActivity.this);
                    } else {
                        ToastUtils.showShort(NewOrderDetailsActivity.this, response.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(NewOrderDetailsActivity.this, "返回数据解析出错");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                CommonUtils.fastShowError(NewOrderDetailsActivity.this, volleyError);
            }
        });
    }

    private void setStatus(int status) {
        switch (status) {
//            case Canstance.TYPE_ORDER_DETAILS_CANCELED:
//                llEngineerInfo.setVisibility(View.GONE);
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_IN_THE_SINGLE:
//                llEngineerInfo.setVisibility(View.GONE);
//                refundCount = 2;
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_COMPLETED:
//                ivStateIcon.setImageResource(R.drawable.ic_details_to_perform);
//                btnBuyAgain.setText(R.string.txt_cancel_order);
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_PAY_OVERTIME:
//                llEngineerInfo.setVisibility(View.GONE);
//                refundCount = 0;
//                tvRefundDetails.setVisibility(View.GONE);
//                ivStateIcon.setImageResource(R.drawable.ic_details_on_road);
//
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_NO_PAYMENT:
//                llEngineerInfo.setVisibility(View.GONE);
//                refundCount = 0;
//                tvRefundDetails.setVisibility(View.GONE);
//                llSmallBtn.setVisibility(View.VISIBLE);
//                btnBuyAgain.setVisibility(View.GONE);
//                ivStateIcon.setImageResource(R.drawable.ic_details_in_service);
//
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_WAIT_SERVICE:
//                llEngineerInfo.setVisibility(View.GONE);
//                refundCount = 0;
//                tvRefundDetails.setVisibility(View.GONE);
//                btnBuyAgain.setText(R.string.txt_cancel_order);
//                ivStateIcon.setImageResource(R.drawable.ic_details_completed);
//                break;
//            case Canstance.TYPE_ORDER_DETAILS_IN_SERVICE:
//                llEngineerInfo.setVisibility(View.GONE);
//                refundCount = 0;
//                tvRefundDetails.setVisibility(View.GONE);
//                btnBuyAgain.setText(R.string.txt_cancel_order);
//                ivStateIcon.setImageResource(R.drawable.ic_details_canceled);
//
//                break;
            default:
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                String serviceNum = "4006-388-818";
//                callToClient(serviceNum, String.format(getString(R.string.txt_confirm_call_service), serviceNum));
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
//                callToClient("10086", String.format(getString(R.string.txt_make_sure_phone), "10086"));
                break;
            case R.id.tv_copy:
//                copy("10086");
                break;
            case R.id.btn_cancel_order:
                ToastUtils.showShort(this, R.string.txt_cancel_order);
                break;
            case R.id.btn_go_to_pay:
                ToastUtils.showShort(this, R.string.txt_go_to_pay);
                break;
            case R.id.btn_buy_again:
//                String text = btnBuyAgain.getText().toString();
//                if (TextUtils.equals(text, getString(R.string.txt_buy_again))) {
//
//                } else if (TextUtils.equals(text, getString(R.string.txt_cancel_order))) {
//                    new AlertDialog(this)
//                            .builder()
//                            .setMsg(getString(R.string.txt_confirm_cancel_order))
//                            .setPositiveButton(getString(R.string.txt_confirm_cancel), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    CancleOrderActivity.startActivity(NewOrderDetailsActivity.this);
//                                }
//                            })
//                            .setNegativeButton(getString(R.string.txt_give_up_cancel), new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }).show();
//
//                } else if (msgCenterBeen.getInfo().getPay_status().equals("1")) {
//                    BookOrderBeen bookOrderBeen = new BookOrderBeen();
//                    BookOrderBeen.InfoBean infoBean = new BookOrderBeen.InfoBean();
//                    infoBean.setId(msgCenterBeen.getInfo().getId());
//                    infoBean.setOrder_id(msgCenterBeen.getInfo().getOrder_id());
//                    bookOrderBeen.setInfo(infoBean);
//                    CheckStandActivity.startActivity(this, bookOrderBeen);
//
//                }
                break;
        }
    }

    public void generateOrderTracePop() {
        OrderTraceView orderTraceView = new OrderTraceView(this, this);
        orderTraceView.refreshData(NewOrderDetailsActivity.this, mBean.order_log);
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


    public void showOrderTracePop() {
//        if (orderTracePop == null) {
//            OrderTraceView orderTraceView = new OrderTraceView(this, this);
//            orderTracePop = new PopupWindow(orderTraceView,
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//            orderTracePop.setTouchable(true);
//            orderTracePop.setOutsideTouchable(true);
//            orderTracePop.setFocusable(true);
//            orderTracePop.setAnimationStyle(R.style.ActionSheetDialogAnimation);
//            orderTracePop.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//            orderTracePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            orderTracePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    darkenBackground(1f);
//                }
//            });
//        }
        orderTracePop.showAtLocation(mBinding.ivBack, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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


    @Override
    public void onPermissionGranted(String operation) {
        onEngineerPhone(null);
    }

    @Override
    public void onPermissionRefuse(String operation) {
        ToastUtils.showShort(this, "");
    }

    public void setData() {
        //订单状态控制
        if ("1".equals(mBean.pay_status)) {//待付款
            mBinding.tvStateDesc.setText(mBean.pay_statusText);
            mBinding.ivStateIcon.setImageResource(R.drawable.od_wait_to_pay);
        } else {//其他状态
            mBinding.tvStateDesc.setText(mBean.statusText);
            switch (mBean.status) {
                case "1"://待指派
                    mBinding.ivStateIcon.setImageResource(R.drawable.od_wate_to_be_assign);
                    break;
                case "2"://待服务
                    mBinding.ivStateIcon.setImageResource(R.drawable.od_wate_to_be_served);
                    break;
                case "3"://正在上门
                    mBinding.ivStateIcon.setImageResource(R.drawable.od_in_the_way);
                    break;
                case "4"://正在服务
                    mBinding.ivStateIcon.setImageResource(R.drawable.od_serving);
                    break;
                case "5"://服务已完成
                    mBinding.ivStateIcon.setImageResource(R.drawable.od_complete);
                    break;
                case "7"://订单已取消
                    mBinding.ivStateIcon.setImageResource(R.drawable.ic_details_canceled);
                    break;
            }
        }
        mBinding.tvStateTime.setText("");

        //服务项
        mServiceTotalAdapter.setNewData(mBean.items);

        mBinding.tvSalePrice.setText(mBean.coupon_amount);
        mBinding.tvOrderPriceTotal.setText("¥" + mBean.pay_amount);
        //退款控制
        if (mBean.refund_info != null && mBean.refund_info.size() > 0) {
            mRefundAdapter.setNewData(mBean.refund_info);
            mBinding.llRefundDetails.setVisibility(View.VISIBLE);
        } else {
            mBinding.llRefundDetails.setVisibility(View.GONE);
        }
        //师傅控制
        if (Integer.parseInt(mBean.status) >= 2 && Integer.parseInt(mBean.status) <= 5) {
            Glide.with(NewOrderDetailsActivity.this).load(mBean.service_user_info.face).skipMemoryCache(false).into(mBinding.ivEngineerPhoto);
            mBinding.tvEngineerName.setText(mBean.service_user_info.name);
        } else {
            mBinding.llEngineerInfo.setVisibility(View.GONE);
        }
        //订单信息
        mBinding.tvContactName.setText(mBean.contact_name);
        mBinding.tvContactPhone.setText(mBean.contact_mobile);
        mBinding.tvServiceAddress.setText(mBean.address);
        mBinding.tvDateTime.setText(mBean.datatime);
        mBinding.tvCommentInfo.setText(mBean.remark);

        mBinding.tvOrderNum.setText(mBean.order_sn);
        mBinding.tvPlaceTime.setText(mBean.created_time);
        mBinding.tvPayType.setText(mBean.pay_way);
        //按钮控制
        if ("1".equals(mBean.pay_status)) {//待支付
            mBinding.btnPayOrder.setVisibility(View.VISIBLE);
            mBinding.btnCancelOrder.setVisibility(View.VISIBLE);
            mBinding.btnRebuyOrder.setVisibility(View.GONE);
        } else if ("1".equals(mBean.status) || "2".equals(mBean.status) || "3".equals(mBean.status)) {//待指派'待服务''正在上门',
            mBinding.btnPayOrder.setVisibility(View.GONE);
            mBinding.btnCancelOrder.setVisibility(View.VISIBLE);
            mBinding.btnRebuyOrder.setVisibility(View.GONE);
        } else if ("4".equals(mBean.status) || "5".equals(mBean.status) || "7".equals(mBean.status)) {//'服务中''已完成''取消订单'
            mBinding.btnPayOrder.setVisibility(View.GONE);
            mBinding.btnCancelOrder.setVisibility(View.GONE);
            mBinding.btnRebuyOrder.setVisibility(View.VISIBLE);
        }
        //
        generateOrderTracePop();
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }

    /**
     * 显示订单状态轨迹
     *
     * @param view
     */
    public void onShowOrderTrace(View view) {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        showOrderTracePop();
    }

    /**
     * 给师傅打电话
     *
     * @param view
     */
    public void onEngineerPhone(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(Manifest.permission.CALL_PHONE, "phone")) {
                setDialogTipUserGoToAppSettting("权限提醒", "应用需要打电话权限，请到应用设置中打开");
                startRequestPermission();
                return;
            }
        }
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        new AlertDialog(this)
                .builder()
                .setMsg(String.format(getString(R.string.txt_make_sure_phone), mBean.service_user_info.tel))
                .setPositiveButton(getString(R.string.txt_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mBean.service_user_info.tel));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.txt_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    /**
     * 复制订单号
     *
     * @param view
     */
    public void onCopyNo(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, mBean.order_sn));
        ToastUtils.showShort(this, R.string.txt_copy_success);
    }

    /**
     * 取消订单
     */
    public void onCancelOrder(View view) {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        new AlertDialog(this)
                .builder()
                .setMsg(getString(R.string.txt_confirm_cancel_order))
                .setPositiveButton(getString(R.string.txt_confirm_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CancleOrderActivity.startActivity(NewOrderDetailsActivity.this);
                    }
                })
                .setNegativeButton(getString(R.string.txt_give_up_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 去支付
     *
     * @param view
     */
    public void onGoToPay(View view) {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
        CheckStandActivity.startActivity(this, mBean.id + "", mBean.order_id);
    }

    /**
     * 再次购买
     *
     * @param view
     */
    public void onReBuyOrder(View view) {
        if (CommonUtils.isFastDoubleClick()) {
            return;
        }
    }
}