package com.lubandj.master.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lubandj.master.R;

/**
 * @author: lj
 * @Time: 2017/12/6 16:09
 * @Description: This is WorkSheetDetailItem
 */

public class WorkSheetDetailItem extends FrameLayout {


    private TextView tvSheetItem;
    private TextView tvSheetItemCount;

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
        addView(tvSheetItem);

        tvSheetItemCount = new TextView(context);
        tvSheetItemCount.setTextColor(txtColor);
        tvSheetItemCount.setTextSize(13);
        tvSheetItemCount.setGravity(Gravity.RIGHT);
        addView(tvSheetItemCount);

    }


    public void initData(String item, int count) {
        tvSheetItem.setText(item);
        tvSheetItemCount.setText("×" + count);
    }
}
