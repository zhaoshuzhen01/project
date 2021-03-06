package com.lubandj.customer.my;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityLeavemsgBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class LeaveMsgActivity extends BaseActivity {
    private ActivityLeavemsgBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leavemsg);
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
        String feedbackStr = binding.etLeavemsg.getText().toString().trim();
//        if (TextUtils.isEmpty(feedbackStr.trim())) {
//            ToastUtils.showShort(LeaveMsgActivity.this, "请先写下您的留言");
//            return;
//        }
        Intent intent=new Intent();
        intent.putExtra("msg",feedbackStr.trim());
        setResult(10001,intent);
        finish();
    }
}
