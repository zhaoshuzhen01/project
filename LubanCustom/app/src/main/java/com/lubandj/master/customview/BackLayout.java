package com.lubandj.master.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2017/12/9.
 */

public class BackLayout extends LinearLayout {
    @InjectView(R.id.no_work_tip2)
    TextView noWorkTip2;
    @InjectView(R.id.button_text)
    TextView buttonText;
    private LinearLayout reflush;

    public BackLayout(Context context) {
        super(context);
        initView(context);
    }

    public BackLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BackLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.back_layout, this);
        reflush = view.findViewById(R.id.work_reflush_lay);
        ButterKnife.inject(this);

    }
    public void setNodataText(String text){
        noWorkTip2.setText(text);
    }

    public void setButtonText(String text){
        buttonText.setText(text);
    }

    public void setOnclick(OnClickListener listener) {
        reflush.setOnClickListener(listener);
    }

}
