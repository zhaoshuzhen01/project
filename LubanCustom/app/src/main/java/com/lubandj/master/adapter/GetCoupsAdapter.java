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

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/5.//
 */

public class GetCoupsAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;

    public GetCoupsAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_getcoups, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        initView(helper, item);

    }

    private void initView(final BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        final int position = helper.getAdapterPosition();
        LinearLayout msgtitle = ((LinearLayout) (helper.getView(R.id.detail_coups)));
        TextView getCous = ((TextView) (helper.getView(R.id.get_click)));
        getCous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(position,view);
            }
        });
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.open_youhui)));
        iconMsg.setOnClickListener(new MyClick(msgtitle, position));
    }

    @Override
    public void childViewClick(int position, View view) {
        ToastUtils.showShort(context, position + "领取");
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