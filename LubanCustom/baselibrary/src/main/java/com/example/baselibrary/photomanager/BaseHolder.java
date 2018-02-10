package com.example.baselibrary.photomanager;

import android.view.View;

/**
 * 一般用作adapter的适配类
 * 需要实现自己的属性
 * Created by lile on 15-11-15.
 */
public abstract class BaseHolder<T> {
    /**
     * 绑定holer和view
     * 例如：
     * 有个属性为TextView title
     * 这里的操作为title = (TextView)parentView.findViewById(R.id.title);
     *
     * @param
     */
    public abstract void bindViews(View parentView);

    /**
     * 需要实现数据和view的绑定
     * 给绑定的属性赋值
     * 例如：
     * 有个属性为TextView title
     * title.setText(item.title);
     *
     * @param item
     * @param position
     */
    public abstract void bindData(T item, int position);
}
