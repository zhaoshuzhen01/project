package com.example.baselibrary;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.util.ActUtils;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public abstract class TitleBaseActivity extends BaseActivity {
    private RelativeLayout llRoot;
    private LinearLayout llBasetitleBack;
    protected RelativeLayout titleRightLay ;
    private TextView tvBasetitleTitle;
    protected TextView tv_basetitle_right;
    private ImageView ivBasetitleOK;
    protected TextView tv_basetitle_back,msgCount;
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ImageView ivBaseTitleBack,tv_basetitle_ok;
    protected View leftView ;
    protected boolean leftClick = false ;
    protected  Window window ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        findView();
        setContentView(getLayout());
        window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        initView();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }*/
    }

    private void findView() {
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView.setItemIconTintList(null);
        llRoot = findView(R.id.ll_basetitle_root);
        ivBaseTitleBack = findView(R.id.iv_basetitle_back);
        llBasetitleBack = findView(R.id.ll_basetitle_back);
        tvBasetitleTitle = findView(R.id.tv_basetitle_title);
        ivBasetitleOK = findView(R.id.tv_basetitle_ok);
        titleRightLay = findView(R.id.ll_basetitle_back1);
        tv_basetitle_right = findView(R.id.tv_basetitle_right);
        tv_basetitle_right.setOnClickListener(this);
        tv_basetitle_ok = findView(R.id.tv_basetitle_ok);
        msgCount = findView(R.id.msgCount);
        msgCount.setVisibility(View.GONE);
        titleRightLay.setOnClickListener(this);
        tv_basetitle_back = findView(R.id.tv_basetitle_back);
        tv_basetitle_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleLeftClick();
            }
        });
        llBasetitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleLeftClick();
            }
        });
        ivBasetitleOK.setOnClickListener(this);
        initLeftMenu();
    }
public void setTitleColor(int color){
    llRoot.setBackgroundColor(color);
}
    public void MenuShow() {
        ViewGroup.LayoutParams params = mNavigationView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        params.height = getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 重点是重写setContentView，让继承者可以继续设置setContentView
     * 重写setContentView
     *
     * @param resId
     */
    @Override
    public void setContentView(int resId) {
        View view = getLayoutInflater().inflate(resId, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.ll_basetitle);
        if (null != llRoot)
            llRoot.addView(view, lp);
    }

    /**
     * 设置中间标题文字
     *
     * @param c
     */
    public void setTitleText(CharSequence c) {
        if (tvBasetitleTitle != null)
            tvBasetitleTitle.setText(c);
    }

    public void setRightText(String text){
        tv_basetitle_right.setVisibility(View.VISIBLE);
        tv_basetitle_right.setText(text);
    }

    /**
     * 设置中间标题文字
     *
     * @param resId
     */
    public void setTitleText(int resId) {
        if (tvBasetitleTitle != null)
            tvBasetitleTitle.setText(resId);
    }

    /**
     * 设置返回按钮
     *
     * @param resId
     */
    public void setBackImg(@DrawableRes int resId) {
        if (ivBaseTitleBack != null) {
            ivBaseTitleBack.setImageResource(resId);
        }
    }

    /**
     * 设置返回按钮
     *
     * @param visiable
     */
    public void setBackImgVisiable(int visiable) {
        if (ivBaseTitleBack != null) {
            ivBaseTitleBack.setVisibility(visiable);
        }
    }

    /**
     * 设置右边标题
     */
    public void setOKImg(@DrawableRes int resId) {
        if (ivBasetitleOK != null) {
            ivBasetitleOK.setImageResource(resId);
        }
    }

    /**
     * 设置右边按钮是否显示
     *
     * @param visible
     */
    public void setOkVisibity(boolean visible) {
        if (ivBasetitleOK != null) {
            if (visible){
                tv_basetitle_ok.setVisibility(View.VISIBLE);
                msgCount.setVisibility(View.VISIBLE);
            }
            else{
                tv_basetitle_ok.setVisibility(View.INVISIBLE);
                msgCount.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置左边按钮是否显示
     *
     * @param visible
     */
    public void setLeftVisibity(boolean visible) {
        if (llBasetitleBack != null) {
            if (visible)
                llBasetitleBack.setVisibility(View.VISIBLE);
            else
                llBasetitleBack.setVisibility(View.INVISIBLE);
        }
    }

    public abstract void titleLeftClick();

    public LinearLayout getLlBasetitleBack() {
        return llBasetitleBack;
    }


    public TextView getTvBasetitleTitle() {
        return tvBasetitleTitle;
    }


    public ImageView getIvBasetitleOK() {
        return ivBasetitleOK;
    }

    /**
     * 点击左边菜单
     */
    protected abstract   void clickMenu();
    private void initLeftMenu() {
//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                // mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (leftClick)
                    clickMenu();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }
}
