package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.MyCons;
import com.lubandj.master.been.WorkListBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/4.
 */

public class CouponsAdapter extends BaseQuickAdapter<MyCons.InfoBean, BaseViewHolder> {
    private Context context;

    public CouponsAdapter(@Nullable List<MyCons.InfoBean> data, Context context) {
        super(R.layout.item_coupons, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCons.InfoBean item) {
        initView(helper, item);

    }

    private void initView(final BaseViewHolder helper, MyCons.InfoBean item) {
        int position = helper.getAdapterPosition();
        LinearLayout msgtitle = ((LinearLayout) (helper.getView(R.id.detail_coups)));
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.open_youhui)));
        iconMsg.setOnClickListener(new MyClick(msgtitle, position));
        TextView txt1 = ((TextView) (helper.getView(R.id.txt1)));
        txt1.setText(item.getAmount() + "");
        TextView txt2 = ((TextView) (helper.getView(R.id.txt2)));
        txt2.setText(item.getReduction() + "");
        TextView time = ((TextView) (helper.getView(R.id.msgTime)));
        time.setText(item.getExpiry_info() + "");
        TextView title = ((TextView) (helper.getView(R.id.msgtitle)));
        title.setText(item.getName() + "");

        TextView detail1 = ((TextView) (helper.getView(R.id.msgtitle)));
//        title.setText(item.getName()+"");
        TextView detail2 = ((TextView) (helper.getView(R.id.deatil2)));
        detail2.setText("适用地区:" + item.getCity() + "");
    }

    @Override
    public void childViewClick(int position, View view) {
        ToastUtils.showShort(context, position + "");
    }

    private class MyClick implements View.OnClickListener {
        private LinearLayout linearLayout;
        private int position;

        public MyClick(LinearLayout linearLayout, int position) {
            this.linearLayout = linearLayout;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (linearLayout.getVisibility() == View.VISIBLE) {
                linearLayout.setVisibility(View.GONE);
            } else {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}
