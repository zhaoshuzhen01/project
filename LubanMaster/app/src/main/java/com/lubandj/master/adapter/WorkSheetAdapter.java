package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.baiduUtil.BaiduApi;
import com.lubandj.master.been.WorkListBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public class WorkSheetAdapter extends BaseQuickAdapter<WorkListBeen.InfoBean, BaseViewHolder> implements DialogTagin.DialogSure {
    private Context context;
    private  int modeStyle = 0 ;// 0 未完成  1  已完成  2 已取消
    private int currentIndex = 0 ;
    private List<WorkListBeen.InfoBean> mdata ;
    private ClickButton clickButton ;
    public WorkSheetAdapter(@Nullable List<WorkListBeen.InfoBean> data,Context context,int modeStyle,ClickButton clickButton) {
        super(R.layout.item_worksheet, data);
        this.context = context ;
        this.mdata = data;
        this.modeStyle = modeStyle ;
        this.clickButton = clickButton;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkListBeen.InfoBean item) {
        initView(helper,item);
    }

    public void remove(int pos){
        mdata.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * 初始化view
     * @param helper
     * @param item
     */
    private void initView(final BaseViewHolder helper, WorkListBeen.InfoBean item){
        int position = helper.getAdapterPosition();
        TextView finishState =  ((TextView) (helper.getView(R.id.finishState)));
        TextView serviceState = ((TextView) (helper.getView(R.id.serviceState)));
        TextView daohangState= ((TextView) (helper.getView(R.id.daohangState)));
        TextView worklist_address = ((TextView) (helper.getView(R.id.worklist_address)));
        TextView worklist_code= ((TextView) (helper.getView(R.id.worklist_code)));
        TextView worklist_time = ((TextView) (helper.getView(R.id.worklist_time)));
        worklist_address.setText(item.getAddress()+"");
        worklist_code.setText("工单号："+item.getTicketSn());
        worklist_time.setText(item.getBeginTime());
        daohangState.setVisibility(View.GONE);
        ((ImageView) (helper.getView(R.id.pic_finish))).setVisibility(View.GONE);
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
               unFinish(item.getStatus(),position,serviceState,finishState,daohangState,helper);
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
        currentIndex = position;
        switch (view.getId()) {
            case R.id.finishState:
                switch (Integer.parseInt(mdata.get(currentIndex).getStatus())) {
                    case 3:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.KEY_SHEET_STATUS_IN_SERVICE + "").setDialogSure(this);
                        break;
                    case 1:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.KEY_SHEET_STATUS_TO_PERFORM + "").setDialogSure(this);
                        break;
                    case 2:
                        DialogTagin.getDialogTagin(context).messageShow(Canstance.KEY_SHEET_STATUS_ON_ROAD + "").setDialogSure(this);
                        break;
                }
                break;
            case R.id.daohangState:
                BaiduApi.getBaiduApi().baiduNavigation(context,mdata.get(currentIndex).getAddress(),mdata.get(currentIndex).getLat(),mdata.get(currentIndex).getLng());
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
    private void unFinish(String status,int position,TextView serviceState,TextView finishState,TextView daohangState,final BaseViewHolder helper){
        switch (Integer.parseInt(status)){
            case 3:
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

            case 2:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.workpath);
                serviceState.setText("正在上门");
                finishState.setText("开始服务");
                daohangState.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 完成or取消模块
     * @param position
     * @param serviceState
     */
    private void finishOrCancle(String title,int position,TextView serviceState,final BaseViewHolder helper) {
        serviceState.setText(title);
        RelativeLayout bottomLay = ((RelativeLayout) (helper.getView(R.id.bottom_lay)));
        bottomLay.setVisibility(View.GONE);
        if (title.equals("已完成")) {
            ((ImageView) (helper.getView(R.id.pic_finish))).setVisibility(View.VISIBLE);
        ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_completed);
    }
        else
            ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_canceled);

    }

    @Override
    public void dialogCall() {
        WorkListBeen.InfoBean entity = mdata.get(currentIndex);
        if (clickButton!=null)
            clickButton.callClick(entity,currentIndex);
    }

    public interface ClickButton{
        void callClick(WorkListBeen.InfoBean entity,int currentIndex);
    }
}