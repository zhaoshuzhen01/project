package com.lubandj.customer.order;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.lubandj.customer.base.PhonePermissionActivity;
import com.lubandj.master.R;

import butterknife.ButterKnife;

public class OrderDetailsActivity extends PhonePermissionActivity {


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
                callToClient(serviceNum,  String.format(getString(R.string.txt_confirm_call_service), serviceNum));
                break;
            default:
                break;
        }
    }
}
