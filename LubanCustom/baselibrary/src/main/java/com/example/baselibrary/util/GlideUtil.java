package com.example.baselibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by ${zhaoshuzhen} on 2018/2/4.
 */

public class GlideUtil {


    public static  void circleImg(final Context context, String url,final ImageView imgview){
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgview) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgview.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
