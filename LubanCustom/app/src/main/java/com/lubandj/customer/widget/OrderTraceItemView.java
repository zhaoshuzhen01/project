package com.lubandj.customer.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author: lj
 * @Time: 2018/2/2 17:23
 * @Description: This is OrderTraceItemView
 */

public class OrderTraceItemView extends FrameLayout {

    @InjectView(R.id.iv_circle)
    ImageView ivCircle;
    @InjectView(R.id.view_line)
    View viewLine;
    @InjectView(R.id.tv_status_desc)
    TextView tvStatusDesc;
    @InjectView(R.id.tv_time)
    TextView tvTime;

    public OrderTraceItemView(@NonNull Context context) {
        this(context, null);
    }

    public OrderTraceItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderTraceItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_order_trace_item, this);
        ButterKnife.inject(this);
    }


    public void  init(boolean isLast){
        if(isLast){
           viewLine.setVisibility(GONE);
           ivCircle.setImageResource(R.drawable.shape_order_trace_item_circle);
        }else{
            viewLine.setVisibility(VISIBLE);
            ivCircle.setImageResource(R.drawable.shape_order_trace_item_circle_gray);
        }
    }

}
