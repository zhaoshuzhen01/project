package com.lubandj.master.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义圆形图片
 * Created by yangjinjian on 2016/11/2.
 */

public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        RoundedBitmapDrawable mRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bm);
        mRoundedBitmapDrawable.setCornerRadius(getWidth() / 2);
        setImageDrawable(mRoundedBitmapDrawable);
    }
}
