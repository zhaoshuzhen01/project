package com.lubandj.master.worksheet;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.widget.ActionSheetDialog;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.R;

import java.net.URISyntaxException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WorkSheetDetailsActivity extends TitleBaseActivity {


    public static final String KEY_DETAILS_TYPE = "details_type";
    private static final String TAG = "WorkSheetDetailsActivit";
    private static final int TYPE_TO_PERFORM = 0;
    private static final int TYPE_ON_ROAD = 1;
    private static final int TYPE_IN_SERVICE = 2;
    private static final int TYPE_COMPLETED = 3;
    private static final int TYPE_CANCELED = 4;
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
    @InjectView(R.id.fl_sign_exception)
    FrameLayout flSignException;
    @InjectView(R.id.btn_start_server)
    Button btnStartServer;
    @InjectView(R.id.fl_start_server)
    FrameLayout flStartServer;
    @InjectView(R.id.ll_btn)
    LinearLayout llBtn;

    @InjectView(R.id.ll_cancel_reason)
    LinearLayout llCancelReason;

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
        ButterKnife.inject(this);
        setTitleText(R.string.txt_work_sheet_details_title);
        setOKText("客服");
        currentType = getIntent().getIntExtra(KEY_DETAILS_TYPE, TYPE_TO_PERFORM);
        Log.e(TAG, "initView: " + currentType);
        switch (currentType) {
            case TYPE_TO_PERFORM:
                tvStateDesc.setText("待执行");
                btnStartServer.setText("开始上门");
                break;
            case TYPE_ON_ROAD:
                tvStateDesc.setText("正在上门");
                btnStartServer.setText("开始服务");
                break;
            case TYPE_IN_SERVICE:
                tvStateDesc.setText("服务中");
                btnStartServer.setText("服务完成");
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                break;
            case TYPE_COMPLETED:
                tvStateDesc.setText("已完成");
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                flStartServer.setVisibility(View.GONE);
                break;
            case TYPE_CANCELED:
                tvStateDesc.setText("已取消");
                ivPhoneIcon.setEnabled(false);
                ivAddressIcon.setEnabled(false);
                llBtn.setVisibility(View.GONE);
                llCancelReason.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_phone_icon, R.id.iv_address_icon, R.id.tv_copy, R.id.btn_sign_exception, R.id.btn_start_server})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_icon:
                new AlertDialog(this)
                        .builder()
                        .setTitle("即将拨打电话")
                        .setMsg("确定拨打电话："+tvPhoneNum.getText().toString()+"吗？")
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
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("高德地图",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        openMap(false);
                                    }
                                })
                        .addSheetItem("百度地图",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        openMap(true);
                                    }
                                }).show();
                break;
            case R.id.tv_copy:
                onClickCopy(tvWorkSheetNo.getText().toString());
                break;
            case R.id.btn_sign_exception:
                startActivity(SignExceptionActivity.class,null);
                break;
            case R.id.btn_start_server:
                new AlertDialog(this)
                        .builder()
                        .setTitle("确认提醒")
                        .setMsg(getRemindContent())
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toast(btnStartServer.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                break;
        }
    }


    private String getRemindContent(){
        String content="";
        switch (currentType) {
            case TYPE_TO_PERFORM:
                content="请确认将开始前往服务地点";
                break;
            case TYPE_ON_ROAD:
                content="请确认开始服务";
                break;
            case TYPE_IN_SERVICE:
                content="请确认服务已完成";
                break;
        }
        return content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                toast("客服");
                break;
            default:
                break;
        }

    }


    private void openMap(boolean isBaiduMap) {
        if (!checkApkExist(this, isBaiduMap ? "com.baidu.BaiduMap" : "com.autonavi.minimap")) {
            toast(isBaiduMap ? "请安装百度地图" : "请安装高德地图");
            return;
        }
        Intent intent = null;
        try {
            intent = isBaiduMap ? Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content =百度奎科大厦&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end") :
                    Intent.getIntent("androidamap://viewMap?sourceApplication=厦门通&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        startActivity(intent); //启动调用

    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
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
        toast("复制成功");
    }


}
