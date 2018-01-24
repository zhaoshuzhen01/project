package com.example.baselibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/25.
 */

public class MainBottomView extends LinearLayout {

    LinearLayout mainBottomBar;
    private List<MainBottomTab> list = new ArrayList<>();
    private Context mcontext;
    private View view;
    private  BottomTabSelect bottomTabSelect ;
    public MainBottomView(Context context) {
        super(context);
        initView(context);
    }

    public MainBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MainBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mcontext = context;
        view = LayoutInflater.from(context).inflate(R.layout.view_main_bottom, this);
        mainBottomBar = (LinearLayout) view.findViewById(R.id.main_bottom_bar);
    }

    public void setBottomTabSelect(BottomTabSelect bottomTabSelect){
        this.bottomTabSelect = bottomTabSelect ;
    }

    public MainBottomView addItem(MainBottomTab tab) {
        list.add(tab);
        tab.setTag(list.size()-1);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        params.weight = 1 ;
        mainBottomBar.setGravity(Gravity.CENTER_VERTICAL);
        mainBottomBar.addView(tab,params);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                for (MainBottomTab tab:list){
                    tab.setUnSelect();
                }
                ((MainBottomTab)view).setSelect();
                if (bottomTabSelect!=null)
                    bottomTabSelect.onselectIndex((Integer) ((MainBottomTab)view).getTag());
            }
        });
        return this;
    }

    public void setSelectTab(int index){
        for (MainBottomTab tab:list){
            tab.setUnSelect();
        }
        list.get(index).setSelect();
    }

    public interface BottomTabSelect{
        void onselectIndex(int position);
    }
}
