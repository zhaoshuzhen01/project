package com.lubandj.master.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/28.
 */

public class PingJiaPicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private ImageLoader imageLoader;

    public PingJiaPicAdapter(@Nullable List<String> data, Context context) {
        super(R.layout.item_pingjia_pic, data);
        this.context = context ;
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getAdapterPosition();
        ImageView imageView = ((ImageView) (helper.getView(R.id.state_img)));
//        Glide.with(context).load(item).into(imageView);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.drawable.homelistdefaut, R.drawable.homelistdefaut);
        imageLoader.get(item, imageListener);
    }

    @Override
    public void childViewClick(int position, View view) {

    }
}
