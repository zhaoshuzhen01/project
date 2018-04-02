package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.baselibrary.HomeBeen;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.GlideRoundTransform;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.customview.RoundImageView;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/24.
 */

public class HomePagerAdapter extends BaseQuickAdapter<HomeBeen.InfoBean, BaseViewHolder> {
    private Context context;
    private ImageLoader imageLoader;

    public HomePagerAdapter(@Nullable List<HomeBeen.InfoBean> data, Context context) {
        super(R.layout.item_home_pager, data);
        this.context = context ;
       imageLoader= new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeen.InfoBean item) {
        int position = helper.getAdapterPosition();
        RoundImageView iconMsg = ((RoundImageView) (helper.getView(R.id.state_img)));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iconMsg.getLayoutParams();
        int width = context.getResources().getDisplayMetrics().widthPixels;
        params.width = width/8;
        params.height = width/8;
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iconMsg, R.drawable.bingxiangicon, R.drawable.bingxiangicon);
        if (!TextUtils.isEmpty(item.getService_pic()))
            imageLoader.get(item.getService_pic(), imageListener);
        TextView title = ((TextView) (helper.getView(R.id.home_list_title)));
        title.setText(item.getName());
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}

