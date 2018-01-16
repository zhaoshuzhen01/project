package com.lubandj.master.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.been.WorkSheetDetailBean;

/**
 * @author: lj
 * @Time: 2017/12/6 16:09
 * @Description: This is WorkSheetDetailItem
 */

public class WorkSheetDetailItem extends FrameLayout {


    private TextView tvSheetItem;
    private ImageView ivStatus;
    private TextView tvStatus;
    private static final String KEY_STATUS_CANCEL = "7";
    private static final String KEY_STATUS_NORMAL = "2";

    public WorkSheetDetailItem(@NonNull Context context) {
        this(context, null);
    }

    public WorkSheetDetailItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkSheetDetailItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        int txtColor = ContextCompat.getColor(context, R.color.color_666666);

        tvSheetItem = new TextView(context);
        tvSheetItem.setTextColor(txtColor);
        tvSheetItem.setTextSize(13);
        LayoutParams tvItemLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvItemLp.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        addView(tvSheetItem);

//        ivStatus = new ImageView(context);
//        ivStatus.setImageResource(R.drawable.selector_item_status);
//        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.leftMargin = (int) context.getResources().getDimension(R.dimen.h_10dp);
//        addView(ivStatus, layoutParams);

        tvStatus = new TextView(context);
        tvStatus.setGravity(Gravity.CENTER);
        tvStatus.setTextSize(12);
        tvStatus.setTextColor(Color.WHITE);
        int padding = (int) getResources().getDimension(R.dimen.h_8dp);
        tvStatus.setPadding(padding, 0, padding, 0);
        LayoutParams tvLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLp.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        addView(tvStatus, tvLp);


    }


    public void initData(WorkSheetDetailBean.InfoBean.ServiceItemBean serviceItemBean) {
        tvSheetItem.setText(serviceItemBean.getItem());
        tvStatus.setText(serviceItemBean.getStatusText());
        switch (serviceItemBean.getStatus()) {
            case KEY_STATUS_CANCEL:
                tvStatus.setBackgroundResource(R.drawable.shape_service_item_status_cancel);
                break;
            case KEY_STATUS_NORMAL:
                tvStatus.setBackgroundResource(R.drawable.shape_service_item_status_normal);
                break;
            default:
                tvStatus.setBackgroundResource(R.drawable.shape_service_item_status_normal);
                break;
        }
    }
}
