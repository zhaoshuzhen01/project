package com.example.baselibrary.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.baselibrary.R;
import com.example.baselibrary.photomanager.BaseHolder;
import com.example.baselibrary.photomanager.BaseSimpleAdapter;
import com.example.baselibrary.photomanager.IPhoto;

import java.io.File;

/**
 * Created by lile on 15-11-15.
 */
public class PhotoGridAdapter extends BaseSimpleAdapter<IPhoto> {
    private int imgHeight = ViewGroup.LayoutParams.MATCH_PARENT;
    private int imgWith = ViewGroup.LayoutParams.MATCH_PARENT;
    private OnPhotoClickListener listener;
    private OnPhotoMunisClickListener onMinuslistener;
    /**
     * 是否显示减号。用于操作删除
     */
    private boolean showMinus;


    public PhotoGridAdapter(Context context) {
        super(context);
    }

    /**
     * 是否允许减号显示减号删除
     *
     * @param showMinus
     */
    public void setShowMinus(boolean showMinus) {
        this.showMinus = showMinus;
    }

    /**
     * 设置图片控件的宽高，主要是为了适配屏幕的显示
     *
     * @param
     * @param
     */
    public void setPhotoSize(int imgCount, int paddingWidth) {
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.imgWith = (widthPixels - paddingWidth) / imgCount;
        this.imgHeight = imgWith;
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnPhotoClickListener(OnPhotoClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置减号点击事件
     *
     * @param listener
     */
    public void setOnMinusClickListener(OnPhotoMunisClickListener listener) {
        this.onMinuslistener = listener;
    }

    @Override
    protected BaseHolder<IPhoto> getHolder() {
        return new BaseHolder<IPhoto>() {
            ImageView img;
            ImageView imageViewMinus;

            @Override
            public void bindViews(View parentView) {
                img = (ImageView) parentView.findViewById(R.id.imageView);
                ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
                layoutParams.height = imgHeight;
                layoutParams.width = imgWith;
                imageViewMinus = (ImageView) parentView.findViewById(R.id.imageViewMinus);
            }

            @Override
            public void bindData(final IPhoto item, final int position) {
                if (item != null) {
                    String photoPath = item.getPhotoPath();
                    if (!TextUtils.isEmpty(photoPath)) {
                        if (photoPath.startsWith("http://")) {
                            // 网络图片
//                            ImageLoader.getInstance().displayImage(photoPath, img, CacheManager.getDefaultDisplay());
                        } else {
                            // 本地图片
                            Glide.with(context).load(item.getPhotoPath()).skipMemoryCache(false).into(img);
                        }
                    } else if (item.getPhotoRes() > 0) {
                        // 加载的是图片资源
                        img.setImageResource(item.getPhotoRes());
                    }
                    if (showMinus && !TextUtils.isEmpty(photoPath)) {
                        imageViewMinus.setVisibility(View.VISIBLE);
                        //减号的单击事件
                        imageViewMinus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onMinuslistener != null) {
                                    onMinuslistener.onItemClick(item, position);
                                }
                            }
                        });
                    } else {
                        imageViewMinus.setVisibility(View.GONE);
                        Glide.with(context).load(item.getPhotoRes()).skipMemoryCache(false).into(img);
                    }
                } else {
                    imageViewMinus.setVisibility(View.GONE);
                    Glide.with(context).load(item.getPhotoRes()).skipMemoryCache(false).into(img);
                }
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(item, position);
                        }
                    }
                });
            }
        };
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.item_photo_grid;
    }

    @Override
    protected View getCustomerView() {
        return null;
    }

    public interface OnPhotoClickListener {
        void onItemClick(IPhoto item, int position);
    }

    public interface OnPhotoMunisClickListener {
        void onItemClick(IPhoto item, int position);
    }
}
