package com.lubandj.master.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-6
 * company:九州宏图
 */

public class DayShowView extends LinearLayout {
    private View mVLine;
    private TextView mTvDay;

    public DayShowView(Context context) {
        super(context);
        initView(context);
    }

    public DayShowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DayShowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.selfdefineview_day, this);
        mVLine = view.findViewById(R.id.v_line);
        mTvDay = view.findViewById(R.id.tv_week_day);
    }

    public void setTvTextClickable(String text, boolean flag) {
        if (TextUtils.isEmpty(text)) {
            mVLine.setVisibility(View.INVISIBLE);
            mTvDay.setText("");
            mTvDay.setSelected(false);
            mTvDay.setBackgroundColor(Color.WHITE);
        } else {
            mVLine.setVisibility(View.VISIBLE);
            if (flag) {
                mTvDay.setBackgroundColor(Color.WHITE);
                mTvDay.setTextColor(Color.BLACK);
                mTvDay.setText(text);
                mTvDay.setSelected(true);
            } else {
                mTvDay.setBackgroundColor(Color.WHITE);
                mTvDay.setTextColor(Color.parseColor("#999999"));
                mTvDay.setText(text);
                mTvDay.setSelected(false);
            }
        }
    }

    public void setTvSelect(String text) {
        mTvDay.setSelected(true);
        mTvDay.setText(text);
        mVLine.setVisibility(View.VISIBLE);
        mTvDay.setBackgroundResource(R.drawable.shape_circle);
        mTvDay.setTextColor(Color.WHITE);
    }

    public void setOnClickListener(OnClickListener listener) {
        mTvDay.setOnClickListener(listener);
    }
}
