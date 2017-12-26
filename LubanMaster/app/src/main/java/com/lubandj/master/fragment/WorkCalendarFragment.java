package com.lubandj.master.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.WorkDetailAdapter;
import com.lubandj.master.adapter.WorkTimeAdapter;
import com.lubandj.master.my.WorkCalendarActivity;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-24
 * company:九州宏图
 */
public class WorkCalendarFragment extends Fragment {
    private WorkTimeAdapter mAdapter;
    private WorkDetailAdapter mDetailAdapter;

    private GridView mGvTime;
    private ListView mLvDetail;
    private LinearLayout mLayoutTime;
    private Button mBtnDetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workcalendar, container, false);

        mGvTime = view.findViewById(R.id.gv_workcalendar_time);
        mLvDetail = view.findViewById(R.id.lv_workcalendar_detail);
        mLayoutTime = view.findViewById(R.id.ll_workcalendar_timeplan);
        mBtnDetail = view.findViewById(R.id.btn_workcalendar_detail);

        mAdapter = new WorkTimeAdapter(getActivity());
        mGvTime.setAdapter(mAdapter);

        mDetailAdapter = new WorkDetailAdapter(getActivity());
        mLvDetail.setAdapter(mDetailAdapter);

        mBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutTime.setVisibility(View.INVISIBLE);
                mLvDetail.setVisibility(View.VISIBLE);
            }
        });

        mLvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkSheetDetailsActivity.class);
                intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, Canstance.TYPE_WORKCALENDAR);
                startActivity(intent);
            }
        });
        return view;
    }
}
