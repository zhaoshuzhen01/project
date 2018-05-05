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
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.Gson;
import com.lubandj.customer.bean.OrderDetailBean;
import com.lubandj.customer.bean.RefundBean;
import com.lubandj.customer.bean.ServiceTotalBean;
import com.lubandj.customer.bean.ServiceUserBean;
import com.lubandj.customer.httpbean.OrderDetailResponse;
import com.lubandj.customer.widget.OrderTraceView;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.activity.CancleOrderActivity;
import com.lubandj.master.activity.CheckStandActivity;
import com.lubandj.master.adapter.EngineerAdapter;
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
    private EngineerAdapter mEngineerAdapter;
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
        mBinding.rvItems.setNestedScrollingEnabled(false);

        mRefundAdapter = new RefundAdapter(refundList, this);
        mBinding.rvRefund.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRefund.setHasFixedSize(true);
        mBinding.rvRefund.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));
        mBinding.rvRefund.setAdapter(mRefundAdapter);
        mBinding.rvRefund.setNestedScrollingEnabled(false);

        mEngineerAdapter = new EngineerAdapter(new ArrayList<ServiceUserBean>(), this);
        mBinding.rvEngineer.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvEngineer.setHasFixedSize(true);
        mBinding.rvEngineer.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));
        mBinding.rvEngineer.setAdapter(mEngineerAdapter);
        mBinding.rvEngineer.setNestedScrollingEnabled(false);

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
        mBinding.tvStateTime.setText(mBean.order_log.get(mBean.order_log.size() - 1).created_time);

        //服务项
        mServiceTotalAdapter.setNewData(mBean.items);

        mBinding.tvSalePrice.setText(mBean.coupon_amount);
        mBinding.tvOrderPriceTotal.setText("¥" + mBean.amount);
        //退款控制
        if (mBean.refund_info != null && mBean.refund_info.size() > 0) {
            mRefundAdapter.setNewData(mBean.refund_info);
            mBinding.llRefundDetails.setVisibility(View.VISIBLE);
        } else {
            mBinding.llRefundDetails.setVisibility(View.GONE);
        }
        //师傅控制
        if (Integer.parseInt(mBean.status) >= 2 && Integer.parseInt(mBean.status) <= 5) {
            mEngineerAdapter.setNewData(mBean.service_user_info);
        } else {
            mBinding.rvEngineer.setVisibility(View.GONE);
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
     */
    public void onEngineerPhone(final String tel) {
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
                .setMsg(String.format(getString(R.string.txt_make_sure_phone), tel))
                .setPositiveButton(getString(R.string.txt_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
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
                        Intent intent = new Intent(NewOrderDetailsActivity.this, CancleOrderActivity.class);
                        intent.putExtra("orderid", mBean.order_id);
                        startActivityForResult(intent, 909);
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
        CheckStandActivity.startActivity(this, mBean.id + "", mBean.order_id,mBean.amount);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 909:
                    finish();
                    break;
            }
        }
    }
}