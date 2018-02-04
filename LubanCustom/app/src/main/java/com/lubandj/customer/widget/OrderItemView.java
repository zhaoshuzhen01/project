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

import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author: lj
 * @Time: 2018/1/28 18:20
 * @Description: This is OrderItemView
 */

public class OrderItemView extends FrameLayout implements View.OnClickListener {


    @InjectView(R.id.iv_item_photo)
    ImageView ivItemPhoto;
    @InjectView(R.id.tv_item_name)
    TextView tvItemName;
    @InjectView(R.id.tv_item_num)
    TextView tvItemNum;
    @InjectView(R.id.tv_item_price)
    TextView tvItemPrice;


    public OrderItemView(@NonNull Context context) {
        this(context, null);
    }

    public OrderItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_order_item, this);
        ButterKnife.inject(this);

        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        ToastUtils.showShort(getContext(),"跳转项目详情");
    }
}
