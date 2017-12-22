package com.lubandj.master.worksheet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.JsonObject;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.baiduUtil.BaiduApi;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.widget.WorkSheetDetailItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorkSheetDetailsActivity extends TitleBaseActivity implements DialogTagin.DialogSure {


    @InjectView(R.id.iv_state_icon)
    ImageView ivStateIcon;
    @InjectView(R.id.tv_state_desc)
    TextView tvStateDesc;
    @InjectView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @InjectView(R.id.iv_phone_icon)
    ImageView ivPhoneIcon;
    @InjectView(R.id.tv_address_desc)
    TextView tvAddressDesc;
    @InjectView(R.id.iv_address_icon)
    ImageView ivAddressIcon;
    @InjectView(R.id.tv_contact_name)
    TextView tvContactName;
    @InjectView(R.id.tv_work_sheet_no)
    TextView tvWorkSheetNo;
    @InjectView(R.id.tv_copy)
    TextView tvCopy;
    @InjectView(R.id.btn_sign_exception)
    Button btnSignException;
    @InjectView(R.id.btn_start_server)
    Button btnStartServer;
    @InjectView(R.id.ll_btn)
    FrameLayout llBtn;
    @InjectView(R.id.ll_cancel_reason)
    LinearLayout llCancelReason;
    @InjectView(R.id.ll_detail_items)
    LinearLayout llDetailItems;
    @InjectView(R.id.ll_state)
    LinearLayout llState;

    public static final String KEY_DETAILS_TYPE = "details_type";
    private static final String TAG = "WorkSheetDetail";
    private int currentType;


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_details;
    }

    @Override
    public void initView() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ButterKnife.inject(this);
        setTitleText(R.string.txt_work_sheet_details_title);
        setBackImg(R.drawable.back_mark);
        setOKImg(R.drawable.ic_service);
        currentType = getIntent().getIntExtra(KEY_DETAILS_TYPE, Canstance.TYPE_TO_PERFORM);
        initData();


        Log.e(TAG, "initView: " + currentType);
        switch (currentType) {
            case Canstance.TYPE_TO_PERFORM:
                ivStateIcon.setImageResource(R.drawable.ic_details_to_perform);
                tvStateDesc.setText(R.string.txt_sheet_state_to_perform);
                btnStartServer.setText(R.string.txt_work_sheet_details_on_road);
                break;
            case Canstance.TYPE_ON_ROAD:
                ivStateIcon.setImageResource(R.drawable.ic_details_on_road);
                tvStateDesc.setText(R.string.txt_sheet_state_on_road);
                btnStartServer.setText(R.string.txt_work_sheet_details_start_service);
                break;
            case Canstance.TYPE_IN_SERVICE:
                ivStateIcon.setImageResource(R.drawable.ic_details_in_service);
                tvStateDesc.setText(R.string.txt_sheet_state_in_service);
                btnStartServer.setText(R.string.txt_work_sheet_details_service_completed);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                break;
            case Canstance.TYPE_COMPLETED:
                ivStateIcon.setImageResource(R.drawable.ic_details_completed);
                tvStateDesc.setText(R.string.txt_sheet_state_completed);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                btnStartServer.setVisibility(View.GONE);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) btnSignException.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER;
                btnSignException.setLayoutParams(layoutParams);
                break;
            case Canstance.TYPE_CANCELED:
                ivStateIcon.setImageResource(R.drawable.ic_details_canceled);
                tvStateDesc.setText(R.string.txt_sheet_state_canceled);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                llBtn.setVisibility(View.GONE);
                llCancelReason.setVisibility(View.VISIBLE);
                break;
            case Canstance.TYPE_WORKCALENDAR:
                llState.setVisibility(View.GONE);
                btnStartServer.setVisibility(View.GONE);
                llCancelReason.setVisibility(View.GONE);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) btnSignException.getLayoutParams();
                layoutParams2.gravity = Gravity.CENTER;
                btnSignException.setLayoutParams(layoutParams2);
                break;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.h_8dp);
        for (int i = 0; i < 3; i++) {
            WorkSheetDetailItem workSheetDetailItem = new WorkSheetDetailItem(this);
            workSheetDetailItem.initData("空调保养-挂式", i);
            if (i != 0) {
                workSheetDetailItem.setLayoutParams(layoutParams);
            }
            llDetailItems.addView(workSheetDetailItem);
        }

    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",1);


        TaskEngine.getInstance().commonHttps(Canstance.HTTP_WORK_SHEET_DETAILS, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
//                        LoginBeen loginBeen = new Gson().fromJson(s, LoginBeen.class);
//                        if (loginBeen != null) {
//                            ToastUtils.showShort(LoginActivity.this, loginBeen.getMessage());
//                            if (loginBeen.getCode() == 0) {
//                                SPUtils.getInstance().put(Canstance.KEY_SP_PHONE_NUM, mPhoneNum);
//                                SPUtils.getInstance().put(Canstance.KEY_SP_USER_INFO, loginBeen.getInfo().toString());
//                                startActivity(WorkSheetListActivity.class, null);
//                                finish();
//                            }
//                            Logger.e(loginBeen.toString());
//                        }
//                UserInfoResponse response = new UserInfoResponse();
//                response = (UserInfoResponse) CommonUtils.generateEntityByGson(WorkSheetDetailsActivity.this, s, response);
//                if (response != null) {
//                    CommonUtils.setUid(response.info.uid);
//                    CommonUtils.setToken(response.info.token);
//                    TApplication.context.mUserInfo = response.info;
//                    startActivity(WorkSheetListActivity.class, null);
//                    finish();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, format);
                    }
//                            Logger.e(volleyError.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.iv_phone_icon, R.id.iv_address_icon, R.id.tv_copy, R.id.btn_sign_exception, R.id.btn_start_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_icon:
                new AlertDialog(this)
                        .builder()
                        .setTitle("即将拨打电话")
                        .setMsg("确定拨打电话：" + tvPhoneNum.getText().toString() + "吗？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callPhone(tvPhoneNum.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                break;
            case R.id.iv_address_icon:
                BaiduApi.getBaiduApi(this).baiduNavigation();
                break;
            case R.id.tv_copy:
                onClickCopy(tvWorkSheetNo.getText().toString());
                break;
            case R.id.btn_sign_exception:
                startActivity(SignExceptionActivity.class, null);
                break;
            case R.id.btn_start_server:
                DialogTagin.getDialogTagin(this).messageShow(currentType).setDialogSure(this);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                toast(this, "客服");
                break;
            default:
                break;
        }

    }

    @SuppressLint("MissingPermission")
    private void callPhone(String num) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        startActivity(intent);
    }

    public void onClickCopy(String selectedText) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, selectedText));
        toast(this, "复制成功");
    }


    @Override
    public void dialogCall() {
        ToastUtils.showShort(this, "sdfdsf");
    }
}
