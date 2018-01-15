package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.http.AsyncHttpClient;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.adapter.WorkCalendarAdapter;
import com.lubandj.master.databinding.ActivityAskforleaveBinding;
import com.lubandj.master.databinding.ActivityWorkcodeBinding;
import com.lubandj.master.dialog.ClickListenerInterface;
import com.lubandj.master.dialog.ScrollSelectDialog;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.httpbean.AskForLeaveRequest;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class AskForLeaveActivity extends BaseActivity {
    private ActivityAskforleaveBinding binding;
    private AskForLeaveRequest mRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_askforleave);
        mRequest = new AskForLeaveRequest();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }

    public void onSubmit(View view) {
        if (TextUtils.isEmpty(mRequest.beginTime) || TextUtils.isEmpty(mRequest.endTime)) {
            ToastUtils.showShort(AskForLeaveActivity.this, "请假时间尚未全部设置");
            return;
        }
        mRequest.reason = binding.etLeaveReason.getText().toString();
        if (TextUtils.isEmpty(mRequest.reason)) {
            ToastUtils.showShort(AskForLeaveActivity.this, "请假事由尚未填写");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDay = sdf.format(new Date());
        String begin = mRequest.beginTime + (mRequest.beginSign == 1 ? " 08:00:00" : " 12:00:00");
        if (begin.compareTo(nowDay) < 0) {
            ToastUtils.showShort(AskForLeaveActivity.this, "请假时间最早不能早于当前时间");
            return;
        }
        if (mRequest.beginTime.compareTo(mRequest.endTime) > 0 || (mRequest.beginTime.compareTo(mRequest.endTime) == 0 && mRequest.beginSign > mRequest.endSign)) {
            ToastUtils.showShort(AskForLeaveActivity.this, "开始时间不能大于结束时间");
            return;
        }
        TipDialog dialog = new TipDialog(AskForLeaveActivity.this);
        dialog.setNoPomptTitle();
        dialog.setTextDes("确定要请假吗？");
        dialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                sendRequest();
                dialog.dismiss();
            }
        });
        dialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void onStartTime(View view) {
        ScrollSelectDialog dialog = new ScrollSelectDialog(AskForLeaveActivity.this, new ClickListenerInterface() {
            @Override
            public void doConfirm(String mark) {
                binding.tvStarttime.setText(mark);
                String[] strings = mark.split(" ");
                mRequest.beginTime = strings[0];
                mRequest.beginSign = strings[1].equals("上午") ? 1 : 2;
            }

            @Override
            public void doCancel(String mark) {

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void onEndTime(View view) {
        ScrollSelectDialog dialog = new ScrollSelectDialog(AskForLeaveActivity.this, new ClickListenerInterface() {
            @Override
            public void doConfirm(String mark) {
                binding.tvEndtime.setText(mark);
                String[] strings = mark.split(" ");
                mRequest.endTime = strings[0];
                mRequest.endSign = strings[1].equals("上午") ? 1 : 2;
            }

            @Override
            public void doCancel(String mark) {

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void sendRequest() {
        initProgressDialog("正在提交...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_LEAVEREQUEST, mRequest, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                BaseResponseBean response = new BaseResponseBean();
                response = (BaseResponseBean) CommonUtils.generateEntityByGson(AskForLeaveActivity.this, s, response);
                if (response != null) {
                    ToastUtils.showShort(AskForLeaveActivity.this, "提交成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(AskForLeaveActivity.this, volleyError);
            }
        });
    }
}
