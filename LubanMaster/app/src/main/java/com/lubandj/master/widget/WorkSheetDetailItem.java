package com.lubandj.master.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;

/**
 * @author: lj
 * @Time: 2017/12/6 16:09
 * @Description: This is WorkSheetDetailItem
 */

public class WorkSheetDetailItem extends LinearLayout {


    private TextView tvSheetItem;
    private ImageView ivStatus;

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
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        int txtColor = ContextCompat.getColor(context, R.color.color_666666);

        tvSheetItem = new TextView(context);
        tvSheetItem.setTextColor(txtColor);
        tvSheetItem.setTextSize(13);
        addView(tvSheetItem);

        ivStatus = new ImageView(context);
        ivStatus.setImageResource(R.drawable.selector_item_status);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = (int) context.getResources().getDimension(R.dimen.h_10dp);
        addView(ivStatus, layoutParams);

    }


    public void initData(String item, String count) {
        tvSheetItem.setText(item);
        if (TextUtils.equals(count, "1")) {
            ivStatus.setSelected(false);
        } else {
            ivStatus.setSelected(true);
        }
    }
}
