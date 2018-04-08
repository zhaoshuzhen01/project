package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.TestBean;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public class MsgCenterAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;

    public MsgCenterAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_msg, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.state_img)));
        TextView msgtitle = ((TextView) (helper.getView(R.id.msgtitle)));
        TextView msgContent = ((TextView) (helper.getView(R.id.msgContent)));
        TextView msgTime = ((TextView) (helper.getView(R.id.msgTime)));
        msgtitle.setText(item.getTitle() + "");
        msgContent.setText(item.getContent() + "");
        msgTime.setText(item.getDatatime() + "");
        switch (Integer.parseInt(item.getType())) {
            case 1:
                iconMsg.setImageResource(R.drawable.msg_work);
                break;
            case 2:
                iconMsg.setImageResource(R.drawable.msg_sys);
                break;
            case 3:
                iconMsg.setImageResource(R.drawable.xinxireset);
                break;
            case 4:
                iconMsg.setImageResource(R.drawable.xinxfinish);
                break;
            case 5:
                iconMsg.setImageResource(R.drawable.msg_finish);
                break;
            case 6:
                iconMsg.setImageResource(R.drawable.msg_change);
                break;
        }

    }

    @Override
    public void childViewClick(int position, View view) {
        MsgCenterBeen.InfoBean.ListBean bean = getItem(position);
        if (!TextUtils.isEmpty(bean.getTicket_sn())) {//检测跳转
            Intent intent = new Intent(context, WorkSheetDetailsActivity.class);
            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID, bean.getTicket_sn());
            context.startActivity(intent);
        }
    }
}
