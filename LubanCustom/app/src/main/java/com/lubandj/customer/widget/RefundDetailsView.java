package com.lubandj.customer.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lj on 2018/1/29.
 */

public class RefundDetailsView extends LinearLayout {


    @InjectView(R.id.tv_refund_num)
    TextView tvRefundNum;
    @InjectView(R.id.tv_refund_status)
    TextView tvRefundStatus;
    @InjectView(R.id.ll_cancel_service)
    LinearLayout llCancelService;
    @InjectView(R.id.tv_refund_penal_sum)
    TextView tvRefundPenalSum;
    @InjectView(R.id.tv_refund_sum)
    TextView tvRefundSum;
    @InjectView(R.id.tv_refund_start_time)
    TextView tvRefundStartTime;
    @InjectView(R.id.tv_refund_complete_time)
    TextView tvRefundCompleteTime;

    public RefundDetailsView(@NonNull Context context) {
        this(context, null);
    }

    public RefundDetailsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefundDetailsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.line_cutting, this);
        layoutInflater.inflate(R.layout.view_refund_details, this);
        ButterKnife.inject(this);


        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.h_8dp);
        for (int i = 0; i < 2; i++) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(13);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_666666));
            textView.setText("空调保养-柜式x1");
            if (i != 0) {
                llCancelService.addView(textView, layoutParams);
            } else {
                llCancelService.addView(textView);
            }
        }
    }
}
