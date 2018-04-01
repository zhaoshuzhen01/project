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
import com.lubandj.master.been.OrderListBeen;
import com.lubandj.master.been.WorkListBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public class WorkSheetAdapter extends BaseQuickAdapter<OrderListBeen.InfoBean, BaseViewHolder> implements DialogTagin.DialogSure {
    private Context context;
    private  int modeStyle = 0 ;// 0 未完成  1  已完成  2 已取消
    private int currentIndex = 0 ;
    private List<OrderListBeen.InfoBean> mdata ;
    private ClickButton clickButton ;
    public WorkSheetAdapter(@Nullable List<OrderListBeen.InfoBean> data, Context context, int modeStyle, ClickButton clickButton) {
        super(R.layout.item_worksheet, data);
        this.context = context ;
        this.mdata = data;
        this.modeStyle = modeStyle ;
        this.clickButton = clickButton;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderListBeen.InfoBean item) {
        initView(helper,item);
    }

    /**
     * 初始化view
     * @param helper
     * @param item
     */
    private void initView(final BaseViewHolder helper, OrderListBeen.InfoBean item){
        int position = helper.getAdapterPosition();
        TextView finishState =  ((TextView) (helper.getView(R.id.finishState)));
        TextView serviceState = ((TextView) (helper.getView(R.id.serviceState)));
        TextView daohangState= ((TextView) (helper.getView(R.id.daohangState)));
        TextView worklist_address = ((TextView) (helper.getView(R.id.worklist_address)));
        TextView worklist_code= ((TextView) (helper.getView(R.id.worklist_code)));
        TextView worklist_time = ((TextView) (helper.getView(R.id.worklist_time)));
        TextView price = ((TextView) (helper.getView(R.id.daohangprice)));
        worklist_address.setText(item.getAddress()+"");
        worklist_time.setText(item.getDatatime()+"");
        price.setText("¥" +item.getAmount());
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
        unFinish(Integer.parseInt(item.getStatus())+"",position,serviceState,finishState,daohangState,helper,item);
    }
    @Override
    public void childViewClick(int position,View view) {
        currentIndex = position;
        if (mdata.get(currentIndex).getPay_status().equals("1")){
            DialogTagin.getDialogTagin(context).messageShow(Canstance.TYPE_ORDER_DETAILS_IN_PAY + "").setDialogSure(this);
            return;
        }
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
        }
    }
    /**
     * 未完成模块
     * @param position
     * @param serviceState
     * @param finishState
     * @param daohangState
     */
    private void unFinish(String status,int position,TextView serviceState,TextView finishState,TextView daohangState,final BaseViewHolder helper,OrderListBeen.InfoBean item){
       serviceState.setText(item.getStatusText());
       if (item.getPay_status().equals("1")){
           serviceState.setText(item.getPay_statusText());
           finishState.setText("去支付");
           return;
       }
        switch (Integer.parseInt(status)){
            case 0:
                finishState.setText("取消订单");
                break;
            case 1:
                finishState.setText("取消订单");
                break;

            case 2:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_to_perform);
                finishState.setText("取消订单");
                break;
            case 3:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_on_road);
                finishState.setText("取消订单");
                break;
            case 4:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_in_service);
                finishState.setText("再次购买");
                break;

            case 5:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_completed);
                finishState.setText("取消订单");
                break;
            case 7:
                ((ImageView) (helper.getView(R.id.state_img))).setImageResource(R.drawable.ic_details_canceled);
                finishState.setText("取消订单");
                break;
        }
    }

    /**
     * 完成or取消模块
     * @param position
     * @param serviceState
     */
    private void finishOrCancle(String title,int position,TextView serviceState,final BaseViewHolder helper,OrderListBeen.InfoBean item) {
        serviceState.setText(item.getStatusText());


    }

    @Override
    public void dialogCall() {
        OrderListBeen.InfoBean entity = mdata.get(currentIndex);
        if (clickButton!=null)
            clickButton.callClick(entity,currentIndex);
    }

    public interface ClickButton{
        void callClick(OrderListBeen.InfoBean entity,int currentIndex);
    }
}