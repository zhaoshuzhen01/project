package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.ActionSheetDialog;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.baiduUtil.BaiduApi;
import com.lubandj.master.been.TestBean;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public class WorkSheetAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    private Context context;
    private  int modeStyle = 0 ;// 0 未完成  1  已完成  2 已取消
    public WorkSheetAdapter(@Nullable List<TestBean> data,Context context,int modeStyle) {
        super(R.layout.item_worksheet, data);
        this.context = context ;
        this.modeStyle = modeStyle ;
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        initView(helper,item);
    }

    /**
     * 初始化view
     * @param helper
     * @param item
     */
    private void initView(final BaseViewHolder helper, TestBean item){
        int position = helper.getAdapterPosition();
        TextView finishState =  ((TextView) (helper.getView(R.id.finishState)));
        TextView serviceState = ((TextView) (helper.getView(R.id.serviceState)));
        TextView daohangState= ((TextView) (helper.getView(R.id.daohangState)));
        daohangState.setVisibility(View.GONE);
        daohangState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(helper.getAdapterPosition(),view);
            }
        });
        finishState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(helper.getAdapterPosition(),view);
            }
        });
        switch (modeStyle){
            case 0:
               unFinish(position,serviceState,finishState,daohangState,helper);
                break;
            case 1:
                finishOrCancle("已完成",position,serviceState,helper);
                break;
            case 2:
                finishOrCancle("已取消",position,serviceState,helper);
                break;
        }
    }
    @Override
    public void childViewClick(int position,View view) {
        switch (view.getId()){
            case R.id.finishState:
                switch (position){
                    case 0:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.TYPE_IN_SERVICE);
                        break;
                    case 1:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.TYPE_TO_PERFORM);
                        break;
                    default:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.TYPE_ON_ROAD);
                        break;
                }
                break;
            case R.id.daohangState:
                BaiduApi.getBaiduApi(context).baiduNavigation();
                break;
        }
    }
    /**
     * 未完成模块
     * @param position
     * @param serviceState
     * @param finishState
     * @param daohangState
     */
    private void unFinish(int position,TextView serviceState,TextView finishState,TextView daohangState,final BaseViewHolder helper){
        switch (position){
            case 0:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.workservie);
                serviceState.setText("服务中");
                finishState.setText("服务完成");
                break;
            case 1:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.workwait);
                serviceState.setText("待执行");
                finishState.setText("开始上门");
                daohangState.setVisibility(View.VISIBLE);
                break;

            default:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.workpath);
                serviceState.setText("正在上门");
                finishState.setText("开始上门");
                daohangState.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 完成or取消模块
     * @param position
     * @param serviceState
     */
    private void finishOrCancle(String title,int position,TextView serviceState,final BaseViewHolder helper){
        serviceState.setText(title);
        RelativeLayout bottomLay =  ((RelativeLayout) (helper.getView(R.id.bottom_lay)));
        bottomLay.setVisibility(View.GONE);
    }
}