package com.example.baselibrary.photomanager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Wang on 2015/11/19.
 */
public class SquareLayout extends RelativeLayout {

    public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareLayout(Context context) {
        super(context);
    }

    protected void onMeasure(int width, int height) {
        setMeasuredDimension(getDefaultSize(0, width), getDefaultSize(0, height));
        int childwidth = getMeasuredWidth();
        int childheight = getMeasuredHeight();
        width = height = MeasureSpec.makeMeasureSpec(childwidth, MeasureSpec.EXACTLY);
        super.onMeasure(width, height);
    }
}
