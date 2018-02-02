package com.lubandj.customer.order;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lubandj.customer.base.PhonePermissionActivity;
import com.lubandj.customer.widget.OrderTraceView;
import com.lubandj.master.R;

import butterknife.ButterKnife;

public class OrderDetailsActivity extends PhonePermissionActivity {


    private PopupWindow orderTracePop;

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
    }

    @Override
    public void initData() {

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

    }

}
