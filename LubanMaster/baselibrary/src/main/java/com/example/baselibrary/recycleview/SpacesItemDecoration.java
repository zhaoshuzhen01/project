package com.example.baselibrary.recycleview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lile on 2017/5/18 0018 11:17.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int left, right, top, bottom;

    public SpacesItemDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;
        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildPosition(view) == 0)

    }
}
