package com.lubandj.master.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.DensityUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.RecycleViewDivider;
import com.lubandj.master.adapter.WorkDateAdapter;
import com.lubandj.master.adapter.WorkDetailAdapter;
import com.lubandj.master.adapter.WorkTimeAdapter;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.databinding.ActivityOrdercalendarBinding;
import com.lubandj.master.databinding.ActivityWorkcalendarBinding;
import com.lubandj.master.httpbean.WorkDetailRequest;
import com.lubandj.master.httpbean.WorkDetailResponse;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetDetailsActivityPhone;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class OrderCalendarActivity extends BaseActivity {
    private ActivityOrdercalendarBinding binding;

    private WorkDateAdapter titleAdapter;

    private WorkTimeAdapter mAdapter;
    private int selectDayPos = -1;
    private int selectPos = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ordercalendar);

        binding.rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvDate.addItemDecoration(new RecycleViewDivider(
                OrderCalendarActivity.this, LinearLayoutManager.HORIZONTAL, DensityUtils.dip2px(OrderCalendarActivity.this, 0.5f), getResources().getColor(R.color.color_line)));
        titleAdapter = new WorkDateAdapter(OrderCalendarActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (pos != titleAdapter.getSelectPos()) {
                    titleAdapter.setSelectPos(pos);
                    if (selectDayPos == pos)//之前已经设置过时间，再次进入该天，设置选中时间
                        mAdapter.setSelectTime(selectPos);
                    else
                        mAdapter.setSelectTime(-1);
                    //获取详情
//                    getDayDetail(titleAdapter.getCurrentSelectDay());
                }
            }
        });
        binding.rvDate.setAdapter(titleAdapter);

        mAdapter = new WorkTimeAdapter(OrderCalendarActivity.this);
        binding.gvWorkcalendarTime.setAdapter(mAdapter);

        binding.gvWorkcalendarTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.setSelectTime(position)) {
                    selectDayPos = titleAdapter.getSelectPos();
                    selectPos = position;
                    binding.btnConfirm.setEnabled(true);
                }
            }
        });
//        getDayDetail(titleAdapter.getCurrentSelectDay());
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

    public void getDayDetail(String day) {
        initProgressDialog("正在获取详情...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORKDETAIL, new WorkDetailRequest(day), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                WorkDetailResponse response = new WorkDetailResponse();
                response = (WorkDetailResponse) CommonUtils.generateEntityByGson(OrderCalendarActivity.this, s, response);
                if (response != null) {
                    mAdapter.setBean(response.info);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(OrderCalendarActivity.this, volleyError);
            }
        });
    }

    /**
     * 确认时间选择
     *
     * @param view
     */
    public void onConfirm(View view) {
        Intent intent = new Intent();
        intent.putExtra("week", titleAdapter.getSelectWeek());
        intent.putExtra("time", titleAdapter.getCurrentSelectDay() + " " + mAdapter.getTimeText());
        setResult(100, intent);
        finish();
    }
}