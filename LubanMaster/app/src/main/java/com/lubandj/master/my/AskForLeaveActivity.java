package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkCalendarAdapter;
import com.lubandj.master.databinding.ActivityAskforleaveBinding;
import com.lubandj.master.databinding.ActivityWorkcodeBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class AskForLeaveActivity extends BaseActivity {
    private ActivityAskforleaveBinding binding;
    private WorkCalendarAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_askforleave);

        mAdapter = new WorkCalendarAdapter(AskForLeaveActivity.this);
        binding.lvWorkCalendar.setAdapter(mAdapter);
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
}
