package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.activity.PhotoViewActivity;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/2/9.
 */

public class MyEvaluationAdapter extends BaseQuickAdapter<MsgCenterBeen.InfoBean.ListBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener{
    private Context context;
    private RecyclerView recyclerView ;
    private ScrollView scrooview;
    private ImageView pingclick ;
    private PingJiaPicAdapter pingJiaPicAdapter ;
    private List<MsgCenterBeen.InfoBean.ListBean> msgBeens = new ArrayList<>();

    public MyEvaluationAdapter(@Nullable List<MsgCenterBeen.InfoBean.ListBean> data, Context context) {
        super(R.layout.item_my_pingjia, data);
        this.context = context ;
        for (int i = 0; i < 6; i++) {
            msgBeens.add(new MsgCenterBeen.InfoBean.ListBean());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgCenterBeen.InfoBean.ListBean item) {
        int position = helper.getAdapterPosition();
        recyclerView =  ((RecyclerView) (helper.getView(R.id.recyclerView)));
        scrooview = ((ScrollView) (helper.getView(R.id.scrooview)));
        List<String>imgs = new ArrayList<>();
        pingJiaPicAdapter = new PingJiaPicAdapter(imgs,context);
        pingJiaPicAdapter.setIndex(position);
        pingJiaPicAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager =   new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0,5,0,0));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pingJiaPicAdapter);
        pingclick = ((ImageView) (helper.getView(R.id.pingclick)));
        pingclick.setOnClickListener(new MyClick(scrooview, position,pingclick));

    }

    @Override
    public void childViewClick(int position, View view) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtils.showShort(context,position+""+adapter.getIndex());
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
        private ImageView img ;

        public MyClick(ScrollView scrooview, int position,ImageView img) {
            this.scrooview = scrooview;
            this.position = position;
            this.img= img;
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
