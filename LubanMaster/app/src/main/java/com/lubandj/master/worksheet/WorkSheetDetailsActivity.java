package com.lubandj.master.worksheet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.baiduUtil.BaiduApi;
import com.lubandj.master.been.WorkSheetDetailBean;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.widget.WorkSheetDetailItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorkSheetDetailsActivity extends PermissionActivity implements DialogTagin.DialogSure {


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
    @InjectView(R.id.ll_cancel_time)
    LinearLayout llCancelTime;
    @InjectView(R.id.rl_time)
    RelativeLayout RlTime;
    @InjectView(R.id.ll_work_time)
    LinearLayout llWorkTime;
    @InjectView(R.id.tv_cancel_time)
    TextView tvCancelTime;
    @InjectView(R.id.tv_work_start_time)
    TextView tvWorkStartTime;
    @InjectView(R.id.tv_work_end_time)
    TextView tvWorkEndTime;

    @InjectView(R.id.tv_exception)
    TextView tvException;
    @InjectView(R.id.ll_exception)
    LinearLayout llException;

    public static final String KEY_DETAILS_ID = "details_id";
    public static final String KEY_DETAIL_LAT = "lat";
    public static final String KEY_DETAIL_LNG = "lng";
    public static final String WORK_NO = "work_no";
    private String workSheetId;
    private String workNo;
    private String lat;
    private String lng;
    private int updateStatus = 0;
    private String status;
    private static final int REQUEST_CODE_SIGN_EXCEPTION = 532;
    private String serviceNum;

    private WorkSheetDetailBean.InfoBean mInfoBean;


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

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
        workNo = getIntent().getStringExtra(WORK_NO);
        lat = getIntent().getStringExtra(KEY_DETAIL_LAT);
        lng = getIntent().getStringExtra(KEY_DETAIL_LNG);
        serviceNum = "4006-388-818";

        initData();
    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ticket_sn", workNo);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_DETAILS, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
                try {
                    WorkSheetDetailBean workSheetDetailBean = new Gson().fromJson(s, WorkSheetDetailBean.class);
                    if (workSheetDetailBean.getCode() == 0) {
                        refreshPage(workSheetDetailBean);
                    } else if (workSheetDetailBean.getCode() == 104) {
                        CommonUtils.tokenNullDeal(WorkSheetDetailsActivity.this);
                    } else if (workSheetDetailBean.getCode() == 301) {
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, "该工单不存在或已被改派");
                        setResult(RESULT_OK);
                        finish();
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
                CommonUtils.fastShowError(WorkSheetDetailsActivity.this, volleyError);
            }
        });
    }


    @OnClick({R.id.iv_phone_icon, R.id.iv_address_icon, R.id.tv_copy, R.id.btn_sign_exception, R.id.btn_start_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_icon:
                String s = tvPhoneNum.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                callToClient(s, String.format(getString(R.string.txt_make_sure_phone), s));
                break;
            case R.id.iv_address_icon:
                String address = tvAddressDesc.getText().toString();
                if (!TextUtils.isEmpty(address)) {
                    BaiduApi.getBaiduApi().baiduNavigation(this, address, lat, lng);
                }
                break;
            case R.id.tv_copy:
                copy(tvWorkSheetNo.getText().toString());
                break;
            case R.id.btn_sign_exception:
                if("0".equals(mInfoBean.getIs_exception()+"")) {
                    Intent intent = new Intent(this, SignExceptionActivity.class);
                    intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, workSheetId);
                    startActivityForResult(intent, REQUEST_CODE_SIGN_EXCEPTION);
                }else{
                    ToastUtils.showShort(WorkSheetDetailsActivity.this,"已标记");
                }
                break;
            case R.id.btn_start_server:
                DialogTagin.getDialogTagin(this).messageShow(status).setDialogSure(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SIGN_EXCEPTION) {
            setResult(RESULT_OK);
            new AlertDialog(this)
                    .builder()
                    .setTitle(getString(R.string.txt_submit_success))
                    .setMsg(getString(R.string.txt_submit_success_service))
                    .setPositiveButton(getString(R.string.txt_contact_service), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            call(serviceNum);
                        }
                    })
                    .setNegativeButton(getString(R.string.txt_later_call), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
            initData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                callToClient(serviceNum, String.format(getString(R.string.txt_confirm_call_service), serviceNum));
                break;
            default:
                break;
        }
    }


    /**
     * 更改订单状态
     */
    @Override
    public void dialogCall() {
        if (updateStatus == 0) {
            return;
        }
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", workSheetId);
        jsonObject.addProperty("status", updateStatus);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORK_SHEET_UPDATE_STATUS, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
                try {
                    BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                    if (baseEntity.getCode() == 0) {
                        initData();
                        setResult(RESULT_OK);
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, baseEntity.getMessage());
                    } else if (baseEntity.getCode() == 104) {
                        CommonUtils.tokenNullDeal(WorkSheetDetailsActivity.this);
                    } else if (baseEntity.getCode() == 301) {
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, "该工单不存在或已被改派");
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.showShort(WorkSheetDetailsActivity.this, baseEntity.getMessage());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                CommonUtils.fastShowError(WorkSheetDetailsActivity.this, volleyError);
            }
        });
    }


    private void refreshPage(WorkSheetDetailBean workSheetDetailBean) {
        if (workSheetDetailBean == null && workSheetDetailBean.getInfo() == null) {
            return;
        }
        WorkSheetDetailBean.InfoBean info = workSheetDetailBean.getInfo();
        mInfoBean=workSheetDetailBean.getInfo();
        status = info.getStatus();
        switch (status) {
            case Canstance.KEY_SHEET_STATUS_TO_PERFORM:
                ivStateIcon.setImageResource(R.drawable.workwait);
                btnStartServer.setText(R.string.txt_work_sheet_details_on_road);
                updateStatus = 2;
                RlTime.setVisibility(View.GONE);
                llCancelTime.setVisibility(View.GONE);
                llWorkTime.setVisibility(View.VISIBLE);
                break;
            case Canstance.KEY_SHEET_STATUS_ON_ROAD:
                ivStateIcon.setImageResource(R.drawable.workpath);
                btnStartServer.setText(R.string.txt_work_sheet_details_start_service);
                updateStatus = 3;
                RlTime.setVisibility(View.VISIBLE);
                llCancelTime.setVisibility(View.GONE);
                llWorkTime.setVisibility(View.VISIBLE);
                break;
            case Canstance.KEY_SHEET_STATUS_IN_SERVICE:
                ivStateIcon.setImageResource(R.drawable.workservie);
                btnStartServer.setText(R.string.txt_work_sheet_details_service_completed);
                updateStatus = 4;
//                ivPhoneIcon.setEnabled(false);
//                ivAddressIcon.setEnabled(false);
                RlTime.setVisibility(View.VISIBLE);
                llCancelTime.setVisibility(View.GONE);
                llWorkTime.setVisibility(View.VISIBLE);
                break;
            case Canstance.KEY_SHEET_STATUS_COMPLETED:
                ivStateIcon.setImageResource(R.drawable.ic_details_completed);
//                ivPhoneIcon.setEnabled(false);
//                ivAddressIcon.setEnabled(false);
                btnStartServer.setVisibility(View.GONE);
                btnSignException.setVisibility(View.GONE);
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) btnSignException.getLayoutParams();
//                layoutParams.gravity = Gravity.CENTER;
//                btnSignException.setLayoutParams(layoutParams);
                RlTime.setVisibility(View.VISIBLE);
                llCancelTime.setVisibility(View.GONE);
                llWorkTime.setVisibility(View.VISIBLE);
                break;
            case Canstance.KEY_SHEET_STATUS_CANCELED:
                ivStateIcon.setImageResource(R.drawable.ic_details_canceled);
//                ivPhoneIcon.setEnabled(false);
//                ivAddressIcon.setEnabled(false);
                llBtn.setVisibility(View.GONE);
                llCancelReason.setVisibility(View.GONE);
                RlTime.setVisibility(View.VISIBLE);
                llCancelTime.setVisibility(View.VISIBLE);
                llWorkTime.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        tvStateDesc.setText(info.getStatusText());
        tvPhoneNum.setText(info.getCustPhone());
        tvAddressDesc.setText(info.getAddress());
        tvContactName.setText(info.getCustName());
        tvTime.setText(info.getOrderTime());
        tvReMark.setText(info.getRemark());
        tvWorkSheetNo.setText(info.getTicketSn());

        if ("0".equals(info.getIs_exception() + "")) {
            llException.setVisibility(View.GONE);
            btnSignException.setSelected(false);
        } else {
            if (info.getException_remark() != null) {
                llException.setVisibility(View.VISIBLE);
                tvException.setText(info.getException_remark().getContent());
                btnSignException.setSelected(true);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (TextUtils.isEmpty(info.getClose_time())) {
            tvCancelTime.setText("");
        } else {
            tvCancelTime.setText(sdf.format(new Date(1000 * Long.parseLong(info.getClose_time()))));
        }
        if (TextUtils.isEmpty(info.getWork_begin_time())) {
            tvWorkStartTime.setText("");
        } else {
            tvWorkStartTime.setText(sdf.format(new Date(1000 * Long.parseLong(info.getWork_begin_time()))));
        }
        if (TextUtils.isEmpty(info.getWork_end_time())) {
            tvWorkEndTime.setText("");
        } else {
            tvWorkEndTime.setText(sdf.format(new Date(1000 * Long.parseLong(info.getWork_end_time()))));
        }

        List<WorkSheetDetailBean.InfoBean.ServiceItemBean> serviceItem = info.getServiceItem();
        if (serviceItem == null || serviceItem.size() == 0) {
            return;
        }
        if (llDetailItems.getChildCount() > 0) {
            llDetailItems.removeAllViews();
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.h_8dp);
        for (int i = 0; i < serviceItem.size(); i++) {
            WorkSheetDetailBean.InfoBean.ServiceItemBean serviceItemBean = serviceItem.get(i);
            if (serviceItemBean != null) {
                WorkSheetDetailItem workSheetDetailItem = new WorkSheetDetailItem(this);
                workSheetDetailItem.initData(serviceItemBean);
                if (i != 0) {
                    workSheetDetailItem.setLayoutParams(layoutParams);
                }
                llDetailItems.addView(workSheetDetailItem);
            }
        }
    }


    public void copy(String selectedText) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, selectedText));
        ToastUtils.showShort(this, R.string.txt_copy_success);
    }
}
