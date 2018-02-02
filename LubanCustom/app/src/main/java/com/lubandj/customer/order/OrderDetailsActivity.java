package com.lubandj.customer.order;

import android.support.v4.widget.DrawerLayout;
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

import com.lubandj.customer.base.PhonePermissionActivity;
import com.lubandj.customer.widget.OrderItemView;
import com.lubandj.customer.widget.OrderTraceView;
import com.lubandj.customer.widget.RefundDetailsView;
import com.lubandj.master.R;

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
    @InjectView(R.id.tv_place_time)
    TextView tvPlaceTime;
    @InjectView(R.id.tv_pay_type)
    TextView tvPayType;
    @InjectView(R.id.btn_sign_exception)
    Button btnSignException;
    @InjectView(R.id.btn_start_server)
    Button btnStartServer;
    @InjectView(R.id.ll_btn)
    FrameLayout llBtn;


    private PopupWindow orderTracePop;    @Override
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
        initData();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 2; i++) {
            OrderItemView orderItemView = new OrderItemView(this);
            llOrderItem.addView(orderItemView);
            if(i!=1){
                LayoutInflater.from(this).inflate(R.layout.line_cutting, llOrderItem);
            }

            RefundDetailsView refundDetailsView = new RefundDetailsView(this);
            llRefundDetails.addView(refundDetailsView);
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

    @OnClick({R.id.ll_state, R.id.iv_phone_icon,R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_state:
                showOrderTracePop();
                break;
            case R.id.iv_phone_icon:
                callToClient("10086",String.format(getString(R.string.txt_make_sure_phone), "10086"));
                break;
            case R.id.tv_copy:
                copy("10086");
                break;
            default:
                break;

        }
    }

    public void showOrderTracePop() {
        if(orderTracePop==null){
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

    }}
