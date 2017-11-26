package com.example.baselibrary.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * FileName: RecyclerViewUtils
 * description:RecyclerView设置Header/Footer所用到的工具类
 * Author: 丁博洋
 * Date: 2016/9/12
 */
public class RecyclerViewUtils {

    /**
     * 设置HeaderView
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderViewRecyclerAdapter)) {
            return;
        }

        HeaderViewRecyclerAdapter headerAndFooterAdapter = (HeaderViewRecyclerAdapter) outerAdapter;
        if (headerAndFooterAdapter.getHeadersCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);
        }
    }

    /**
     * 设置FooterView
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderViewRecyclerAdapter)) {
            return;
        }

        HeaderViewRecyclerAdapter headerAndFooterAdapter = (HeaderViewRecyclerAdapter) outerAdapter;
        if (headerAndFooterAdapter.getFootersCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    /**
     * 移除FooterView
     *
     * @param recyclerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderViewRecyclerAdapter) {

            int footerViewCounter = ((HeaderViewRecyclerAdapter) outerAdapter).getFootersCount();
            if (footerViewCounter > 0) {
                View footerView = ((HeaderViewRecyclerAdapter) outerAdapter).getFooterView();
                ((HeaderViewRecyclerAdapter) outerAdapter).removeFooterView(footerView);
            }
        }
    }

    /**
     * 移除HeaderView
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof HeaderViewRecyclerAdapter) {

            int headerViewCounter = ((HeaderViewRecyclerAdapter) outerAdapter).getHeadersCount();
            if (headerViewCounter > 0) {
                View headerView = ((HeaderViewRecyclerAdapter) outerAdapter).getHeaderView();
                ((HeaderViewRecyclerAdapter) outerAdapter).removeHeaderView(headerView);
            }
        }
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getLayoutPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderViewRecyclerAdapter) {

            int headerViewCounter = ((HeaderViewRecyclerAdapter) outerAdapter).getHeadersCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderViewRecyclerAdapter) {

            int headerViewCounter = ((HeaderViewRecyclerAdapter) outerAdapter).getHeadersCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }

        return holder.getAdapterPosition();
    }
}