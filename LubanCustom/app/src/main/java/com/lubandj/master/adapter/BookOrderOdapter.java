package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.CarListBeen;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/29.
 */

public class BookOrderOdapter extends BaseQuickAdapter<CarListBeen.InfoBean, BaseViewHolder> {
    private Context context;
    public BookOrderOdapter(@Nullable List<CarListBeen.InfoBean> data, Context context) {
        super(R.layout.item_shopping_cart_layout, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, CarListBeen.InfoBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg =  ((ImageView) (helper.getView(R.id.iv_show_pic)));
        Glide.with(context).load("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg").skipMemoryCache(false).into(iconMsg);
       TextView tvCommodityAttr = ((TextView)(helper.getView(R.id.tv_commodity_attr)));
       TextView tvCommodityNum = ((TextView)(helper.getView(R.id.tv_commodity_num)));
        TextView tvCommodityPrice = ((TextView)(helper.getView(R.id.tv_commodity_price)));
        LinearLayout linearLayout = helper.getView(R.id.rl_edit);
        linearLayout.setVisibility(View.GONE);
        tvCommodityAttr.setText(item.getService_name());
       tvCommodityNum.setText(item.getNum()+"");
       tvCommodityPrice.setText(item.getPrice());
        CheckBox checkBox = ((CheckBox) (helper.getView(R.id.ck_chose)));
        checkBox.setVisibility(View.GONE);

    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
