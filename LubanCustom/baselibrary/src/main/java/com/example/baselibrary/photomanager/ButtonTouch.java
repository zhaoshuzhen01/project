package com.example.baselibrary.photomanager;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 自定义点击变暗的按钮
 * Created by xiaobaima on 15-11-15.
 */
public class ButtonTouch extends Button {
    private static final float Trans = -25f;
    private final static float[] BT_SELECTED = new float[]{1, 0, 0, 0, Trans,
            0, 1, 0, 0, Trans, 0, 0, 1, 0, Trans, 0, 0, 0, 1, 0};
    /**
     * 是否触摸变暗,默认是变暗
     */
    public boolean canTouchChange = true;
    private ColorMatrixColorFilter mPressFilter;

    public ButtonTouch(Context context) {
        super(context);
    }

    public ButtonTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonTouch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canTouchChange) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (getBackground() != null) {
                        if (mPressFilter == null) {
                            mPressFilter = new ColorMatrixColorFilter(BT_SELECTED);
                        }
                        getBackground().setColorFilter(mPressFilter);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (getBackground() != null) {
                        getBackground().clearColorFilter();
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }
}
