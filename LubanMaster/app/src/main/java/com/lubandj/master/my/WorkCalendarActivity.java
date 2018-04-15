package com.lubandj.master.my;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.DensityUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.adapter.RecycleViewDivider;
import com.lubandj.master.adapter.WorkDateAdapter;
import com.lubandj.master.adapter.WorkDetailAdapter;
import com.lubandj.master.adapter.WorkTimeAdapter;
import com.lubandj.master.been.WorkListBeen;
import com.lubandj.master.databinding.ActivityWorkcalendarBinding;
import com.lubandj.master.fragment.WorkCalendarFragment;
import com.lubandj.master.httpbean.WorkDetailRequest;
import com.lubandj.master.httpbean.WorkDetailResponse;
import com.lubandj.master.login.SplashActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;
import com.lubandj.master.worksheet.WorkSheetListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class WorkCalendarActivity extends BaseActivity {
    private ActivityWorkcalendarBinding binding;

    private WorkDateAdapter titleAdapter;

    private WorkTimeAdapter mAdapter;
    private WorkDetailAdapter mDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workcalendar);

        binding.rvDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvDate.addItemDecoration(new RecycleViewDivider(
                WorkCalendarActivity.this, LinearLayoutManager.HORIZONTAL, DensityUtils.dip2px(WorkCalendarActivity.this, 0.5f), getResources().getColor(R.color.color_line)));
        titleAdapter = new WorkDateAdapter(WorkCalendarActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (pos != titleAdapter.getSelectPos()) {
                    titleAdapter.setSelectPos(pos);
                    //获取详情
                    getDayDetail(titleAdapter.getCurrentSelectDay());
                }
            }
        });
        binding.rvDate.setAdapter(titleAdapter);


        mAdapter = new WorkTimeAdapter(WorkCalendarActivity.this);
        binding.gvWorkcalendarTime.setAdapter(mAdapter);

        mDetailAdapter = new WorkDetailAdapter(WorkCalendarActivity.this);
        binding.lvWorkcalendarDetail.setAdapter(mDetailAdapter);

        binding.lvWorkcalendarDetail.setVisibility(View.GONE);
        binding.llWorkcalendarTimeplan.setVisibility(View.VISIBLE);

        binding.lvWorkcalendarDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkListBeen.InfoBean info = (WorkListBeen.InfoBean) mDetailAdapter.getItem(position);
                Intent intent = new Intent(WorkCalendarActivity.this, WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, info.getId());
                intent.putExtra(WorkSheetDetailsActivity.WORK_NO, info.getTicketSn());
                startActivityForResult(intent, 1001);
            }
        });

        getDayDetail(titleAdapter.getCurrentSelectDay());
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


    public void onChangeDetail(View view) {
        if (binding.lvWorkcalendarDetail.getVisibility() == View.GONE) {
            if (mDetailAdapter.getCount() == 0) {
                ToastUtils.showShort(WorkCalendarActivity.this, "该天没有任务");
                return;
            }
            binding.lvWorkcalendarDetail.setVisibility(View.VISIBLE);
            binding.llWorkcalendarTimeplan.setVisibility(View.GONE);
            ((TextView) view).setText("时间表");
        } else {
            binding.lvWorkcalendarDetail.setVisibility(View.GONE);
            binding.llWorkcalendarTimeplan.setVisibility(View.VISIBLE);
            ((TextView) view).setText("详情");
        }
    }

    public void getDayDetail(String day) {
        initProgressDialog("正在获取详情...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WORKDETAIL, new WorkDetailRequest(day), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                WorkDetailResponse response = new WorkDetailResponse();
                response = (WorkDetailResponse) CommonUtils.generateEntityByGson(WorkCalendarActivity.this, s, response);
                if (response != null) {
                    mAdapter.setBean(response.info,titleAdapter.getCurrentSelectDay());
                    mDetailAdapter.setData(response.info.list);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(WorkCalendarActivity.this, volleyError);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (requestCode == RESULT_OK) {
//                mDetailAdapter.removeId(data.getStringExtra("id"));
            }
        }
    }
}
