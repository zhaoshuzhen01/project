package com.example.baselibrary.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.baselibrary.GuangGaoBeen;
import com.example.baselibrary.HomeBeen;
import com.example.baselibrary.R;
import com.example.baselibrary.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ViewPager实现的轮播图,支持自动轮播,手势滑动切换页面
 */

public class SlideShowView extends FrameLayout {

    private final int LOOPTIME = 5000;//轮询时间

    private CustomViewPager mViewPager;

    private LinearLayout ll_containertopo; // 小圆点的容器


    private Context context;
    //数据集合
    //    private List<GameBanners> m_AdvImgs=new ArrayList<>();
    private List<String> m_AdvImgs = new ArrayList<>();
    private MyAdatper adatper;

    private float oldX;
    private Runnable mRunnable;
    private boolean isDarged;
    private boolean isScroll;
    private Handler mHandler = new Handler();
    private int clickPos;
    public static int GUANG = 1;//广告
    public static int TOPCONTENT = 2;//分类内容
    private int mtype;
    private LayoutView mlayoutView;
    private List<List<HomeBeen.InfoBean>> contentlist = new ArrayList<>();
    private OnClickListener mListener;

    public void setGuangGaolists(List<GuangGaoBeen.InfoBean> guangGaolists) {
        this.guangGaolists = guangGaolists;
    }

    private List<GuangGaoBeen.InfoBean> guangGaolists;

    public SlideShowView(Context context) {
        this(context, null);

    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initUI(context);
    }

    public void setData(List<String> m_AdvImgs, int type, LayoutView layoutView, List<List<HomeBeen.InfoBean>> contentlist,OnClickListener listener) {
        mtype = type;
        mlayoutView = layoutView;
        stopLoopAdv();
        this.contentlist = contentlist;
        this.m_AdvImgs.clear();
        this.m_AdvImgs.addAll(m_AdvImgs);
        this.mListener=listener;


        adatper = new MyAdatper();

        mViewPager.setAdapter(adatper);
//        adatper.notifyDataSetChanged();

        setListener();
        if (mtype != GUANG) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_containertopo.getLayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels;
        }
    }

    public void startLoopAdv() {
        if (m_AdvImgs.size() > 1) {//轮播图的个数大于1的时候才能滑动
            mHandler.postDelayed(mRunnable, LOOPTIME);
        }
    }

    public void stopLoopAdv() {

        mHandler.removeCallbacksAndMessages(null);
    }


    private void initUI(Context context) {

        LayoutInflater.from(context).inflate(R.layout.viewpager, this, true);

        mViewPager = (CustomViewPager) findViewById(R.id.vp_pager);
        ll_containertopo = (LinearLayout) findViewById(R.id.ll_containertopo);
        adatper = new MyAdatper();

        mViewPager.setAdapter(adatper);

        mViewPager.setScrollable(true);


        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = mViewPager.getCurrentItem(); // 获取当前页面
                mViewPager.setCurrentItem(++currentItem);
                mHandler.postDelayed(this, LOOPTIME);

            }
        };
    }


    private void setListener() {

        if (m_AdvImgs.size() <= 1) {
            mHandler.removeCallbacksAndMessages(null);
            mViewPager.setScrollable(false);
        } else {
            startLoopAdv();
            mViewPager.setScrollable(true);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (m_AdvImgs.size() == 0) {
                    return;
                }
                position = position % m_AdvImgs.size();
                clickPos = position;
                for (int i = 0; i < ll_containertopo.getChildCount(); i++) {
                    ll_containertopo.getChildAt(i).setEnabled(false);
                }

                ll_containertopo.getChildAt(position).setEnabled(true);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                isScroll = true;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        isScroll = false;
                        if (isDarged) {
                            isDarged = false;

                            startLoopAdv();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isDarged = true;

                        stopLoopAdv();
                        break;
                }

            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return false;
            }
        });

        // 动态添加小圆点
        ll_containertopo.removeAllViews();
        for (int i = 0; i < m_AdvImgs.size(); i++) {
            ImageView point = new ImageView(context);

            point.setScaleType(ImageView.ScaleType.FIT_XY);
            if (mtype == GUANG)
                point.setImageResource(R.drawable.select_pot);
            else
                point.setImageResource(R.drawable.select_pot1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 10;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            ll_containertopo.addView(point);


        }
    }

    class MyAdatper extends PagerAdapter {

        @Override
        public int getCount() {

            return Integer.MAX_VALUE;


        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = null;
            if (m_AdvImgs.size() > 0) {
                if (mtype == GUANG) {
                    imageLayout = LayoutInflater.from(context).inflate(R.layout.adver_banner_item, view, false);
                    ImageView imageView = (ImageView) imageLayout.findViewById(R.id.bi_imageView);
                    final int pos = position % m_AdvImgs.size(); // 为了避免角标越界，进行取余运算
                    //                ImageUtils.requestImage(imageView, m_AdvImgs.get(pos).getPic(), 0, 0, null);
                    //                ImageUtils.requestImage(imageView, m_AdvImgs.get(pos), 0, 0, null);
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.height = (getResources().getDisplayMetrics().widthPixels / 2);
//                    Glide.with(context).load(guangGaolists.get(pos).getPicture()).skipMemoryCache(false).into(imageView);
                    Glide.with(context).load(guangGaolists.get(pos).getPicture()).placeholder(R.drawable.lunbo).dontAnimate().error(R.drawable.lunbo).into(imageView);
                    imageView.setTag(guangGaolists.get(pos).getLinkurl());
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            ToastUtils.showShort(context,"跳转webview");
                            if(mListener!=null)
                            mListener.onClick(v);
                        }
                    });


                    imageView.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:

                                    oldX = event.getX();

                                    break;

                                case MotionEvent.ACTION_MOVE:
                                    break;

                                case MotionEvent.ACTION_UP:

                                    break;

                                case MotionEvent.ACTION_CANCEL:

                                    break;
                            }

                            return false;
                        }
                    });
                } else {
                    int mposition = position % m_AdvImgs.size();
                    imageLayout = mlayoutView.getView(mposition);

                }
                view.addView(imageLayout, 0);
                return imageLayout;
            } else {
                return imageLayout;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        stopLoopAdv();
        if (hasWindowFocus) {
            startLoopAdv();
        } else {
            stopLoopAdv();
        }
    }

    public interface LayoutView {
        View getView(int position);
    }

}
