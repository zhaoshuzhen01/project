package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.activity.PhotoViewActivity;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.MyPingjiaBeen;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/9.
 */

public class MyEvaluationAdapter extends BaseQuickAdapter<MyPingjiaBeen.InfoBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {
    private Context context;
    private RecyclerView recyclerView;
    private ScrollView scrooview;
    private ImageView pingclick;
    private PingJiaPicAdapter pingJiaPicAdapter;
    private List<MyPingjiaBeen.InfoBean> msgBeens = new ArrayList<>();
    private ImageLoader imageLoader;

    public MyEvaluationAdapter(@Nullable List<MyPingjiaBeen.InfoBean> data, Context context) {
        super(R.layout.item_my_pingjia, data);
        this.context = context;
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());

    }

    @Override
    protected void convert(BaseViewHolder helper, MyPingjiaBeen.InfoBean item) {
        int position = helper.getAdapterPosition();
        TextView name = ((TextView) (helper.getView(R.id.pingjia_num)));
        name.setText(item.getName());
        TextView contnet = ((TextView) (helper.getView(R.id.pingjia_mydata)));
        contnet.setText(item.getContent());
        TextView time = ((TextView) (helper.getView(R.id.pingjia_time)));
        time.setText(item.getCreate_time());
        ImageView imageView = ((ImageView) (helper.getView(R.id.pingjiatitle)));
//        Glide.with(context).load(item.getService_icon()).into(imageView);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.drawable.homelistdefaut, R.drawable.homelistdefaut);
        imageLoader.get(item.getService_icon(), imageListener);
        recyclerView = ((RecyclerView) (helper.getView(R.id.recyclerView)));
        scrooview = ((ScrollView) (helper.getView(R.id.scrooview)));
        List<String> imgs = item.getImg();
        pingJiaPicAdapter = new PingJiaPicAdapter(imgs, context);
        pingJiaPicAdapter.setIndex(position);
        pingJiaPicAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 5, 0, 0));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pingJiaPicAdapter);
        pingclick = ((ImageView) (helper.getView(R.id.pingclick)));
        pingclick.setOnClickListener(new MyClick(scrooview, position, pingclick));
        RatingBar ratingBar = ((RatingBar) (helper.getView(R.id.ratingBar)));
        if (TextUtils.isEmpty(item.getStar())){
            ratingBar.setRating(0);
        }else {
            ratingBar.setRating(Float.parseFloat(item.getStar()));
        }

    }

    @Override
    public void childViewClick(int position, View view) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showShort(context, position + "" + adapter.getIndex());
        Intent intent = new Intent(context, PhotoViewActivity.class);
       /* Bundle bundle = new Bundle();
        bundle.putSerializable("dataBean", mData);
        intent.putExtras(bundle);*/
        intent.putExtra("currentPosition", position);
        context.startActivity(intent);
    }

    private class MyClick implements View.OnClickListener {
        private ScrollView scrooview;
        private int position;
        private ImageView img;

        public MyClick(ScrollView scrooview, int position, ImageView img) {
            this.scrooview = scrooview;
            this.position = position;
            this.img = img;
        }

        @Override
        public void onClick(View view) {
            if (scrooview.getVisibility() == View.VISIBLE) {
                scrooview.setVisibility(View.GONE);
                img.setImageResource(R.drawable.citydown);
            } else {
                scrooview.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.cityup);
            }
        }
    }
}
