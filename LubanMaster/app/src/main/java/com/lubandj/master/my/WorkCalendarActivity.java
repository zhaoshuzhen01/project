package com.lubandj.master.my;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.example.baselibrary.BaseActivity;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkDetailAdapter;
import com.lubandj.master.adapter.WorkTimeAdapter;
import com.lubandj.master.databinding.ActivityWorkcalendarBinding;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class WorkCalendarActivity extends BaseActivity {
    private ActivityWorkcalendarBinding binding;
    private WorkTimeAdapter mAdapter;
    private WorkDetailAdapter mDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workcalendar);
        mAdapter = new WorkTimeAdapter(WorkCalendarActivity.this);
        binding.gvWorkcalendarTime.setAdapter(mAdapter);

        mDetailAdapter = new WorkDetailAdapter(WorkCalendarActivity.this);
        binding.lvWorkcalendarDetail.setAdapter(mDetailAdapter);

        binding.btnWorkcalendarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llWorkcalendarTimeplan.setVisibility(View.INVISIBLE);
                binding.lvWorkcalendarDetail.setVisibility(View.VISIBLE);
            }
        });

        binding.lvWorkcalendarDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WorkCalendarActivity.this, WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, Canstance.TYPE_WORKCALENDAR);
                startActivity(intent);
            }
        });
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
