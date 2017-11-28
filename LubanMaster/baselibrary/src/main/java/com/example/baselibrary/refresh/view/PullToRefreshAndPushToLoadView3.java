package com.example.baselibrary.refresh.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.baselibrary.R;


/**
 * Created by -- on 2016/11/2.
 * 自定义下拉刷新上拉加载的基类，可以扩展多种可滑动view(ListView,GridView,RecyclerView...)
 * 第三版：采用Scroller实现下拉效果，支持ListView,GridView,RecyclerView，同时修复一些UI上滑动的bug
 */

public class PullToRefreshAndPushToLoadView3 extends LinearLayout {
    private static final String TAG = "tianbin";
    private Context mContext;
    private Scroller mScroller;
    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;
    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 用于存储上次更新时间
     */
    private SharedPreferences preferences;

    /**
     * 下拉头的View
     */
    private View header;

    /**
     * 需要去下拉刷新的View
     */
    private ViewGroup mView;

    /**
     * 刷新时显示的进度条
     */
    private ProgressBar progressBar;

    /**
     * 指示下拉和释放的箭头
     */
    private ImageView arrow;

    /**
     * 指示下拉和释放的文字描述
     */
    private TextView description;

    /**
     * 上次更新时间的文字描述
     */
    private TextView updateAt;

    /**
     * 上次更新时间的毫秒值
     */
    private long lastUpdateTime;

    /**
     * 一分钟的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;

    /**
     * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
     */
    private static final String UPDATED_AT = "updated_at";
    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
     */
    private boolean isTop;
    /**
     * 上次手指按下时的屏幕纵坐标
     */
    private float mLastY = -1;
    /**
     * 第一次手指按下时的屏幕纵坐标
     */
    private float mFirstY = -1;
    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private int currentStatus = STATUS_REFRESH_FINISHED;
    ;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private int lastStatus = currentStatus;
    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;

    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;
    /**
     * 下拉刷新的回调接口
     */
    private PullToRefreshListener mListener;

    /**
     * 是否正在刷新
     */
    private boolean isRefreshing;
    /**
     * 是否正在完成刷新
     */
    private boolean isFinishingRefresh=false;

    private static final float DEFAULT_RATIO = 2f;
    /**
     * 拖动阻力系数
     */
    private float ratio = DEFAULT_RATIO;

    private int screenHeight;
    /**
     * 是否支持下拉刷新
     */
    private boolean canRefresh=true;

    private Handler handler = new Handler(Looper.getMainLooper());

    public PullToRefreshAndPushToLoadView3(Context context) {
        this(context, null);
    }

    public PullToRefreshAndPushToLoadView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshAndPushToLoadView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PullToRefreshAndPushToLoadView3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context mContext) {
        this.mContext = mContext;
        mScroller = new Scroller(mContext);
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        header = LayoutInflater.from(mContext).inflate(R.layout.refresh_header, null, true);
        progressBar = (ProgressBar) header.findViewById(R.id.progress_bar);
        arrow = (ImageView) header.findViewById(R.id.arrow);
        description = (TextView) header.findViewById(R.id.description);
        updateAt = (TextView) header.findViewById(R.id.updated_at);
        touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        refreshUpdatedAtValue();
        setOrientation(VERTICAL);
        addView(header, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        //为ViewGroup设置宽高
        setMeasuredDimension(measuredWidth, measuredHeight);

        // 计算出所有的childView的宽和高---可用
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            hideHeaderHeight = -header.getHeight();
            mView = (ViewGroup) getChildAt(1);
            loadOnce = true;
        }
        header.layout(0, hideHeaderHeight, r, 0);
        mView.layout(0, 0, r, b);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!canRefresh){
            return super.dispatchTouchEvent(event);
        }
        judgeIsTop();//每次首先进行判断
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                mFirstY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                float totalDistance = event.getY() - mFirstY;
                float deltaY = event.getY() - mLastY;
                mLastY = event.getY();
//                if (Math.abs(deltaY) < touchSlop) {
//                    Log.e(TAG,deltaY+"=============Math.abs(deltaY) < touchSlop============="+touchSlop);
//                    return false;
//                }
                boolean showTop=deltaY>0 && isTop;
                boolean hideTop=deltaY<0 && getScrollY()<0;
                boolean noMove=deltaY==0;//当不动的时候屏蔽一切事件，防止列表滚动
//                Log.e(TAG, "dispatchTouchEvent: "+ratio+"+++"+isTop+"###"+getScrollY()+"$$$"+deltaY);
                if (showTop||hideTop||noMove) {
                    //说明头部显示，自己处理滑动，无论上滑下滑均同步移动（==0代表滑动到顶部可以继续下拉）
                    if (deltaY < 0) {//来回按住上下移动：下拉逐渐增加难度，上拉不变
                        ratio = DEFAULT_RATIO;//此处如果系数不是1，则会出现列表跳动的现象。。。暂未解决！！！
                    } else {
                        if(Math.abs(getScrollY())>=-hideHeaderHeight){
                            ratio += 0.05f;//当头部露出以后逐步增加下拉难度
                        }
                    }
                    int dy=(int) (deltaY / ratio);
                    if(deltaY<0 && Math.abs(dy)> Math.abs(getScrollY())){
                        //当滑动距离大于可滚动距离时，进行调整
                        dy=-Math.abs(getScrollY());
                    }
//                    Log.e(TAG, "dispatchTouchEvent: "+"###"+getScrollY()+"%%%"+dy);
                    scrollBy(0, -dy);
//                    Log.e(TAG, "dispatchTouchEvent: "+"###"+getScrollY()+"&&&"+dy);
                    if (currentStatus != STATUS_REFRESHING){
                        if (getScrollY() <= hideHeaderHeight) {
                            currentStatus = STATUS_RELEASE_TO_REFRESH;
                        } else {
                            currentStatus = STATUS_PULL_TO_REFRESH;
                        }
                        // 时刻记得更新下拉头中的信息
                        updateHeaderView();
                        // 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
                        mView.setPressed(false);
                        mView.setFocusable(false);
                        mView.setFocusableInTouchMode(false);
                        lastStatus = currentStatus;
                    }
                    return true;
                }else{
                    return super.dispatchTouchEvent(event);
                }
//                break;
            case MotionEvent.ACTION_UP:
            default:
                ratio = DEFAULT_RATIO;//重置
                if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                    // 松手时如果是释放立即刷新状态，就去调用正在刷新的任务
                    backToTop();
                } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
                    // 松手时如果是下拉状态，就去调用隐藏下拉头的任务
                    hideHeader(false);
                } else if (currentStatus == STATUS_REFRESHING) {
                    if (getScrollY() <= hideHeaderHeight) {
                        //回弹
                        backToTop();
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void backToTop() {
        currentStatus = STATUS_REFRESHING;
        updateHeaderView();
        mScroller.startScroll(0, getScrollY(), 0, hideHeaderHeight - getScrollY());
        invalidate();
        if (mListener != null && !isRefreshing) {
            isRefreshing = true;
            mListener.onRefresh();
        }
    }

    private void hideHeader(boolean isRefreshFinished) {
        currentStatus = STATUS_REFRESH_FINISHED;
        lastStatus=currentStatus;
        isRefreshing = false;
        isFinishingRefresh=false;
        if(isRefreshFinished){
            //只有刷新完成才更新时间，反弹不算
            preferences.edit().putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
        }
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
        invalidate();
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }else{
            //刷新动画完成以后初始化头部并且没有正在显示刷新成功的状态
            if(!isFinishingRefresh){
                updateHeaderView();
            }
        }
    }

    /**
     * 根据当前View的滚动状态来设定 {@link #isTop}
     * 的值，每次都需要在触摸事件中第一个执行，这样可以判断出当前应该是滚动View，还是应该进行下拉。
     */
    private void judgeIsTop() {
        if (mView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) mView;
            View firstChild = absListView.getChildAt(0);//返回的是当前屏幕中的第一个子view，非整个列表
            if (firstChild != null) {
                int firstVisiblePos = absListView.getFirstVisiblePosition();//不必完全可见，当前屏幕中第一个可见的子view在整个列表的位置
                if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
                    // 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新
                    isTop = true;
                } else {
                    isTop = false;
                }
            } else {
                // 如果ListView中没有元素，也应该允许下拉刷新
                isTop = true;
            }
        } else if (mView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mView;
            View firstChild = recyclerView.getLayoutManager().findViewByPosition(0);//firstChild不必须完全可见
            View firstVisibleChild = recyclerView.getChildAt(0);//返回的是当前屏幕中的第一个子view，非整个列表
//            if(firstChild!=null){
//                Log.e("tianbin",firstChild.getTop()+"==="+recyclerView.getChildAt(0).getTop());
//            }else{
//                Log.e("tianbin","+++++++++");
//            }
            if (firstVisibleChild != null) {
                if (firstChild != null && firstChild.getTop() == 0) {
                    isTop = true;
                } else {
                    isTop = false;
                }
            } else {
                //没有元素也允许刷新
                isTop = true;
            }
        } else {
            isTop = true;
        }
    }

    /**
     * 刷新下拉头中上次更新时间的文字描述。
     */
    private void refreshUpdatedAtValue() {
        lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastUpdateTime;
        long timeIntoFormat;
        String updateAtValue;
        if (lastUpdateTime == -1) {
            updateAtValue = getResources().getString(R.string.not_updated_yet);
        } else if (timePassed < 0) {
            updateAtValue = getResources().getString(R.string.time_error);
        } else if (timePassed < ONE_MINUTE) {
            updateAtValue = getResources().getString(R.string.updated_just_now);
        } else if (timePassed < ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_MINUTE;
            String value = timeIntoFormat + "分钟";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_DAY) {
            timeIntoFormat = timePassed / ONE_HOUR;
            String value = timeIntoFormat + "小时";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_MONTH) {
            timeIntoFormat = timePassed / ONE_DAY;
            String value = timeIntoFormat + "天";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_YEAR) {
            timeIntoFormat = timePassed / ONE_MONTH;
            String value = timeIntoFormat + "个月";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else {
            timeIntoFormat = timePassed / ONE_YEAR;
            String value = timeIntoFormat + "年";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        }
        updateAt.setVisibility(View.VISIBLE);
        updateAt.setText(updateAtValue);
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param time 指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleeping(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下拉刷新的监听器，使用下拉刷新的地方应该注册此监听器来获取刷新回调。
     */
    public interface PullToRefreshListener {

        /**
         * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
         */
        void onRefresh();

    }

    /**
     * 给下拉刷新控件注册一个监听器。
     *
     * @param listener 监听器的实现。
     * @param id       为了防止不同界面的下拉刷新在上次更新时间上互相有冲突， 请不同界面在注册下拉刷新监听器时一定要传入不同的id。
     *                 如果不用时间则可以不传递此参数
     */
    public void setOnRefreshListener(PullToRefreshListener listener, int id) {
        mListener = listener;
        mId = id;
    }

    /**
     * 给下拉刷新控件注册一个监听器。
     *
     * @param listener 监听器的实现。
     */
    public void setOnRefreshListener(PullToRefreshListener listener) {
        setOnRefreshListener(listener, mId);
    }

    /**
     * 当所有的刷新逻辑完成后，记录调用一下，否则你的View将一直处于正在刷新状态。
     */
    public void finishRefreshing() {
        isFinishingRefresh=true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                description.setText(getResources().getString(R.string.refresh_success));
                ((View)arrow.getParent()).setVisibility(View.GONE);
                updateAt.setVisibility(View.GONE);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideHeader(true);
            }
        },1000);
    }

    /**
     * 更新下拉头中的信息
     */
    private void updateHeaderView() {
        ((View)arrow.getParent()).setVisibility(View.VISIBLE);
        if (lastStatus != currentStatus && lastStatus!=STATUS_REFRESH_FINISHED) {
            if (currentStatus == STATUS_PULL_TO_REFRESH) {
                description.setText(getResources().getString(R.string.pull_to_refresh));
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rotateArrow();
            } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
                description.setText(getResources().getString(R.string.release_to_refresh));
                arrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                rotateArrow();
            } else if (currentStatus == STATUS_REFRESHING) {
                description.setText(getResources().getString(R.string.refreshing));
                progressBar.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                arrow.setVisibility(View.GONE);
            }
        }else if(currentStatus==STATUS_REFRESH_FINISHED&&lastStatus==STATUS_REFRESH_FINISHED){
            //说明刷新完成或者第一次进入
            description.setText(getResources().getString(R.string.pull_to_refresh));
            arrow.clearAnimation();
            arrow.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        refreshUpdatedAtValue();
    }

    /**
     * 根据当前的状态来旋转箭头。
     */
    private void rotateArrow() {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (currentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(300);
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }

    public boolean isCanRefresh() {
        return canRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }
}
