package com.example.baselibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/8/26.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder> {
    protected Context context ;
    protected List<?>list ;
    public abstract int setContentView();


    public abstract void getContentDeal(View parent, int position);

    public abstract int getCount();
    public abstract List<?> getlist();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private OnItemClickListener itemClickListener;

    protected OnButtonClickListener buttonClickListener ;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder ;
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
         holder = new MyViewHolder(LayoutInflater.from(
               context).inflate(setContentView(), parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        if (getCount()>0){
            final int pos = getRealPosition(holder);
            getContentDeal(holder.itemView, pos);
            if (getlist()==null||getlist().size()==0)
                return;
            final Object mbeen = getlist().get(pos);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener!=null)
                    itemClickListener.onItemClick(mbeen, pos);
                }
            });
        }
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? getCount() :getCount() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    //点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(T bean, int position);
    }

    //设置点击事件的方法
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //点击事件接口
    public interface OnButtonClickListener<T> {
        void onClick(T bean, int position);
    }

    //设置点击事件的方法
    public  void   setButtonClickListener(OnButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }


}
