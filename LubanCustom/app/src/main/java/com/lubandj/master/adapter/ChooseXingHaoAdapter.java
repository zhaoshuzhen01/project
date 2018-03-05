package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ServiceDetailBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class ChooseXingHaoAdapter extends BaseQuickAdapter<ServiceDetailBeen.InfoBean.ItemsBean, BaseViewHolder> {
    private Context context;
    public ChooseXingHaoAdapter(@Nullable List<ServiceDetailBeen.InfoBean.ItemsBean> data, Context context) {
        super(R.layout.item_choose_xinghao, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDetailBeen.InfoBean.ItemsBean item) {
        int position = helper.getAdapterPosition();
        TextView finishState =  ((TextView) (helper.getView(R.id.home_list_title)));
        finishState.setText(item.getSpec_name()+"");
        if (item.isSelect()){
            finishState.setBackgroundResource(R.drawable.selector_btn_send_code_bg);
            finishState.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            finishState.setBackgroundResource(R.drawable.gray_listback);
            finishState.setTextColor(context.getResources().getColor(R.color.color_cecece));
        }
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
