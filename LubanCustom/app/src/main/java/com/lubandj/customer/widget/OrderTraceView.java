package com.lubandj.customer.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.customer.bean.OrderLogBean;
import com.lubandj.master.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author: lj
 * @Time: 2018/2/2 16:36
 * @Description: 订单追踪
 */

public class OrderTraceView extends FrameLayout {


    private OnClickListener mListener;
    @InjectView(R.id.ll_order_trace_item)
    LinearLayout llOrderTraceItem;
    @InjectView(R.id.tv_close_order_trace)
    TextView tvCloseOrderTrace;


    public OrderTraceView(@NonNull Context context,OnClickListener listener) {
        this(context);
        this.mListener=listener;
    }


    public OrderTraceView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public OrderTraceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderTraceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_order_trace, this);
        ButterKnife.inject(this);


//        llOrderTraceItem.removeAllViews();
//        for (int i = 0; i < logBeans.size(); i++) {
//            OrderTraceItemView orderTraceItemView = new OrderTraceItemView(context);
//            orderTraceItemView.init(i == logBeans.size()-1);
//            llOrderTraceItem.addView(orderTraceItemView);
//        }

    }

    public void refreshData(Context context, List<OrderLogBean> logBeans){
        llOrderTraceItem.removeAllViews();
        for (int i = 0; i < logBeans.size(); i++) {
            OrderTraceItemView orderTraceItemView = new OrderTraceItemView(context);
            orderTraceItemView.init(i == logBeans.size()-1,logBeans.get(i));
            llOrderTraceItem.addView(orderTraceItemView);
        }
    }


    @OnClick(R.id.tv_close_order_trace)
    public void onViewClicked() {
        if(mListener!=null){
            mListener.onClick(tvCloseOrderTrace);
        }
    }
}
