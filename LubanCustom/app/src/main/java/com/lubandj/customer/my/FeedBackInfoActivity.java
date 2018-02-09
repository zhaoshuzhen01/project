package com.lubandj.customer.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityAskforleaveBinding;
import com.lubandj.master.databinding.ActivityFeedbackinfoBinding;
import com.lubandj.master.dialog.ClickListenerInterface;
import com.lubandj.master.dialog.ScrollSelectDialog;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.httpbean.AskForLeaveRequest;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class FeedBackInfoActivity extends BaseActivity {
    private ActivityFeedbackinfoBinding binding;
    private AskForLeaveRequest mRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedbackinfo);
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
        String feedbackStr = binding.etFeedbackinfo.getText().toString().trim();
        if (TextUtils.isEmpty(feedbackStr)) {
            ToastUtils.showShort(FeedBackInfoActivity.this, "请先写下您的意见");
            return;
        }
//        sendRequest();
        ToastUtils.showShort(FeedBackInfoActivity.this, "提交成功");
        finish();
    }

    public void sendRequest() {
        initProgressDialog("正在提交...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_LEAVEREQUEST, mRequest, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                BaseResponseBean response = new BaseResponseBean();
                response = (BaseResponseBean) CommonUtils.generateEntityByGson(FeedBackInfoActivity.this, s, response);
                if (response != null) {
                    ToastUtils.showShort(FeedBackInfoActivity.this, "提交成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(FeedBackInfoActivity.this, volleyError);
            }
        });
    }
}
