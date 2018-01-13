package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.TestBean;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/12/10.
 */

public class MsgCenterAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> {
    private Context context;
    public MsgCenterAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_msg, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        int position = helper.getAdapterPosition();
        ImageView iconMsg =  ((ImageView) (helper.getView(R.id.state_img)));
        TextView msgtitle = ((TextView) (helper.getView(R.id.msgtitle)));
        TextView msgContent= ((TextView) (helper.getView(R.id.msgContent)));
        TextView msgTime= ((TextView) (helper.getView(R.id.msgTime)));
        msgtitle.setText(item.getTitle()+"");
        msgContent.setText(item.getContent()+"");
        msgTime.setText(item.getDatatime()+"");
        switch (Integer.parseInt(item.getType())){
            case 1:
                iconMsg.setImageResource(R.drawable.msg_work);
                break;
            case 2:
                iconMsg.setImageResource(R.drawable.msg_sys);
                break;
            case 3:
                iconMsg.setImageResource(R.drawable.msg_sys);
                break;
            case 4:
                iconMsg.setImageResource(R.drawable.msg_sys);
                break;
            case 5:
                iconMsg.setImageResource(R.drawable.msg_finish);
                break;
        }

    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
