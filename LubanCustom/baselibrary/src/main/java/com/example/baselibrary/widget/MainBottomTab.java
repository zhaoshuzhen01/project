package com.example.baselibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.R;

/**
 * Created by ${zhaoshuzhen} on 2018/1/25.
 */

public class MainBottomTab extends LinearLayout {

    private Context mcontext;
    private ImageView mimg;
    private TextView textView;
    private int activeImg;
    private int inactiveImg;
    private int activeColor;
    private int inactiveColor;

    public MainBottomTab(Context context) {
        super(context);
        initView(context);
    }

    public MainBottomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MainBottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mcontext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_main_tab, this);
        mimg = (ImageView) view.findViewById(R.id.bottom_img1);
        textView = (TextView) findViewById(R.id.bottom_text1);
    }

    public MainBottomTab(Context context, String text) {
        super(context);
        initView(context);
        textView.setText(text);
    }

    //选中图标
    public MainBottomTab setActiviIcon(int img) {
        activeImg = img;
        return this;
    }

    //非选中图标
    public MainBottomTab setInActiviIcon(int img) {
        inactiveImg = img;
        mimg.setImageResource(activeImg);
        return this;
    }

    //选中颜色
    public MainBottomTab setActiviColor(int color) {
        activeColor = color;
        return this;
    }

    //非选中颜色
    public MainBottomTab setInActiviColor(int color) {
        inactiveColor = color;
        return this;
    }

    public void setSelect(){
        mimg.setImageResource(activeImg);
        textView.setTextColor(getResources().getColor(activeColor));
    }
    public void setUnSelect(){
        mimg.setImageResource(inactiveImg);
        textView.setTextColor(getResources().getColor(inactiveColor));
    }
}

