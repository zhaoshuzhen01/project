package com.lubandj.master.worksheet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.baiduUtil.BaiduApi;
import com.lubandj.master.been.WorkSheetDetailBean;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.widget.WorkSheetDetailItem;

import java.util.List;

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
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.tv_remark)
    TextView tvReMark;
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

    public static final String KEY_DETAILS_ID = "details_id";

    private int currentType;
    private String workSheetId;


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
        workSheetId = getIntent().getStringExtra(KEY_DETAILS_ID);
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
                    WorkSheetDetailBean workSheetDetailBean = new Gson().fromJson(s, WorkSheetDetailBean.class);
                    if (workSheetDetailBean.getCode() == 0) {
                        refreshPage(workSheetDetailBean);
                    } else {
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, workSheetDetailBean.getMessage());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
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
                }
            }
        });
    }


    @OnClick({R.id.iv_phone_icon, R.id.iv_address_icon, R.id.tv_copy, R.id.btn_sign_exception, R.id.btn_start_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_icon:
                String s = tvPhoneNum.getText().toString();
                if(TextUtils.isEmpty(s)){
                    return;
                }
                new AlertDialog(this)
                        .builder()
                        .setTitle(getString(R.string.txt_calling))
                        .setMsg(String.format(getString(R.string.txt_make_sure_phone),s))
                        .setPositiveButton(getString(R.string.txt_sure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callPhone(tvPhoneNum.getText().toString());
                            }
                        })
                        .setNegativeButton(getString(R.string.txt_cancel), new View.OnClickListener() {
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

    private void refreshPage(WorkSheetDetailBean workSheetDetailBean) {
        if (workSheetDetailBean == null && workSheetDetailBean.getInfo() == null) {
            return;
        }
        WorkSheetDetailBean.InfoBean info = workSheetDetailBean.getInfo();

        String status = info.getStatus();
        switch (status) {
            case Canstance.KEY_SHEET_STATUS_TO_PERFORM:
                ivStateIcon.setImageResource(R.drawable.ic_details_to_perform);
                tvStateDesc.setText(R.string.txt_sheet_state_to_perform);
                btnStartServer.setText(R.string.txt_work_sheet_details_on_road);
                break;
            case Canstance.KEY_SHEET_STATUS_ON_ROAD:
                ivStateIcon.setImageResource(R.drawable.ic_details_on_road);
                tvStateDesc.setText(R.string.txt_sheet_state_on_road);
                btnStartServer.setText(R.string.txt_work_sheet_details_start_service);
                break;
            case Canstance.KEY_SHEET_STATUS_IN_SERVICE:
                ivStateIcon.setImageResource(R.drawable.ic_details_in_service);
                tvStateDesc.setText(R.string.txt_sheet_state_in_service);
                btnStartServer.setText(R.string.txt_work_sheet_details_service_completed);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                break;
            case Canstance.KEY_SHEET_STATUS_COMPLETED:
                ivStateIcon.setImageResource(R.drawable.ic_details_completed);
                tvStateDesc.setText(R.string.txt_sheet_state_completed);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                btnStartServer.setVisibility(View.GONE);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) btnSignException.getLayoutParams();
                layoutParams.gravity = Gravity.CENTER;
                btnSignException.setLayoutParams(layoutParams);
                break;
            case Canstance.KEY_SHEET_STATUS_CANCELED:
                ivStateIcon.setImageResource(R.drawable.ic_details_canceled);
                tvStateDesc.setText(R.string.txt_sheet_state_canceled);
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                llBtn.setVisibility(View.GONE);
                llCancelReason.setVisibility(View.VISIBLE);
                break;
//            case Canstance.TYPE_WORKCALENDAR:
//                llState.setVisibility(View.GONE);
//                btnStartServer.setVisibility(View.GONE);
//                llCancelReason.setVisibility(View.GONE);
//                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) btnSignException.getLayoutParams();
//                layoutParams2.gravity = Gravity.CENTER;
//                btnSignException.setLayoutParams(layoutParams2);
//                break;
            default:
                break;
        }

        tvPhoneNum.setText(info.getCustPhone());
        tvAddressDesc.setText(info.getAddress());
        tvContactName.setText(info.getCustName());
        tvTime.setText(info.getOrderTime());
        tvReMark.setText(info.getRemark());
        tvWorkSheetNo.setText(info.getTicketSn());

        List<WorkSheetDetailBean.InfoBean.ServiceItemBean> serviceItem = info.getServiceItem();
        if (serviceItem == null || serviceItem.size() == 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.h_8dp);
        for (int i = 0; i < serviceItem.size(); i++) {
            WorkSheetDetailBean.InfoBean.ServiceItemBean serviceItemBean = serviceItem.get(i);
            WorkSheetDetailItem workSheetDetailItem = new WorkSheetDetailItem(this);
            workSheetDetailItem.initData(serviceItemBean.getItem(),serviceItemBean.getNum());
            if (i != 0) {
                workSheetDetailItem.setLayoutParams(layoutParams);
            }
            llDetailItems.addView(workSheetDetailItem);
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
