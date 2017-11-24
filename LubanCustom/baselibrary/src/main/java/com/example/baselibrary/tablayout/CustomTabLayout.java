package com.example.baselibrary.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.baselibrary.R;
import com.example.baselibrary.tablayout.indicators.AnimatedIndicatorInterface;
import com.example.baselibrary.tablayout.indicators.AnimatedIndicatorType;
import com.example.baselibrary.tablayout.indicators.DachshundIndicator;
import com.example.baselibrary.tablayout.indicators.LineFadeIndicator;
import com.example.baselibrary.tablayout.indicators.LineMoveIndicator;
import com.example.baselibrary.tablayout.indicators.PointFadeIndicator;
import com.example.baselibrary.tablayout.indicators.PointMoveIndicator;
import com.example.baselibrary.util.DensityUtils;


/**
 *
 *
 */
public class CustomTabLayout extends TabLayout implements ViewPager.OnPageChangeListener {

    private static final int DEFAULT_HEIGHT_DP =3;

    private int indicatorColor;
    private int indicatorHeight;
    private int size ;

    private int currentPosition;

    private ViewPager viewPager;
    private LinearLayout tabStrip;

    private AnimatedIndicatorInterface animatedIndicator;

    int startXLeft, endXLeft, startXCenter, endXCenter, startXRight, endXRight;

    public CustomTabLayout(Context context) {
        this(context, null);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        super.setSelectedTabIndicatorHeight(0);

        tabStrip = (LinearLayout) super.getChildAt(0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);

        this.indicatorHeight = a.getDimensionPixelSize(R.styleable.CustomTabLayout_CustomIndcatorHeight, DensityUtils.dip2px(context, DEFAULT_HEIGHT_DP));
        this.indicatorColor = a.getColor(R.styleable.CustomTabLayout_CustomIndicatorColor, Color.WHITE);
        this.size = a.getDimensionPixelSize(R.styleable.CustomTabLayout_CustomIndcatorWidth, DensityUtils.dip2px(context, DEFAULT_HEIGHT_DP));

        AnimatedIndicatorType animatedIndicatorType = AnimatedIndicatorType.values()[a.getInt(R.styleable.CustomTabLayout_CustomAnimatedIndicator, 0)];

        switch (animatedIndicatorType) {
            case DACHSHUND:
                setAnimatedIndicator(new DachshundIndicator(this));
                break;
            case POINT_MOVE:
                setAnimatedIndicator(new PointMoveIndicator(this));
                break;
            case LINE_MOVE:
                setAnimatedIndicator(new LineMoveIndicator(this));
                break;
            case POINT_FADE:
                setAnimatedIndicator(new PointFadeIndicator(this));
                break;
            case LINE_FADE:
                setAnimatedIndicator(new LineFadeIndicator(this));
                break;
        }

        a.recycle();
    }

    public void setAnimatedIndicator(AnimatedIndicatorInterface animatedIndicator) {
        this.animatedIndicator = animatedIndicator;

        animatedIndicator.setSelectedTabIndicatorColor(indicatorColor);
        animatedIndicator.setSelectedTabIndicatorHeight(indicatorHeight);

        invalidate();
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        this.indicatorColor = color;
        if (animatedIndicator != null) {
            animatedIndicator.setSelectedTabIndicatorColor(color);

            invalidate();
        }
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
        this.indicatorHeight = height;
        if (animatedIndicator != null) {
            animatedIndicator.setSelectedTabIndicatorHeight(height);

            invalidate();
        }

    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    @Override
    public void setupWithViewPager(@Nullable final ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);

        //TODO
        if (viewPager != null) {
            if (viewPager != this.viewPager) {
                viewPager.removeOnPageChangeListener(this);
                viewPager.addOnPageChangeListener(this);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (animatedIndicator != null)
            animatedIndicator.draw(canvas);

    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
                               final int positionOffsetPixels) {

        if ((position > currentPosition) || (position + 1 < currentPosition)) {
            currentPosition = position;
        }

        if (position != currentPosition) {

            startXLeft = (int) getChildXLeft(currentPosition);
            startXCenter = (int) getChildXCenter(currentPosition);
            startXRight = (int) getChildXRight(currentPosition);

            endXLeft = (int) getChildXLeft(position);
            endXRight = (int) getChildXRight(position);
            endXCenter = (int) getChildXCenter(position);

            if (animatedIndicator != null) {
                animatedIndicator.setIntValues(startXLeft, endXLeft,
                        startXCenter, endXCenter,
                        startXRight, endXRight);
                animatedIndicator.setCurrentPlayTime((long) ((1 - positionOffset) * (int) animatedIndicator.getDuration()));
            }

        } else {

            startXLeft = (int) getChildXLeft(currentPosition);
            startXCenter = (int) getChildXCenter(currentPosition);
            startXRight = (int) getChildXRight(currentPosition);

            if (tabStrip.getChildAt(position + 1) != null) {

                endXLeft = (int) getChildXLeft(position + 1);
                endXCenter = (int) getChildXCenter(position + 1);
                endXRight = (int) getChildXRight(position + 1);

            } else {
                endXLeft = (int) getChildXLeft(position);
                endXCenter = (int) getChildXCenter(position);
                endXRight = (int) getChildXRight(position);
            }

            if (animatedIndicator != null) {
                animatedIndicator.setIntValues(startXLeft, endXLeft,
                        startXCenter, endXCenter,
                        startXRight, endXRight);
                animatedIndicator.setCurrentPlayTime((long) (positionOffset * (int) animatedIndicator.getDuration()));
            }

        }

        if (positionOffset == 0) {
            currentPosition = position;
        }

    }

    @Override
    public void onPageSelected(final int position) {
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public float getChildXLeft(int position) {
        if (tabStrip.getChildAt(position) != null)
            return (tabStrip.getChildAt(position).getX());
        else
            return 0;
    }

    public float getChildXCenter(int position) {
        if (tabStrip.getChildAt(position) != null)
            return (tabStrip.getChildAt(position).getX() + tabStrip.getChildAt(position).getWidth() / 2);
        else
            return 0;
    }

    public float getChildXRight(int position) {
        if (tabStrip.getChildAt(position) != null)
            return (tabStrip.getChildAt(position).getX() + tabStrip.getChildAt(position).getWidth());
        else
            return 0;
    }

    public AnimatedIndicatorInterface getAnimatedIndicator() {
        return animatedIndicator;
    }
}
