package com.example.baselibrary;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public abstract class TitleBaseActivity extends BaseActivity{
    private RelativeLayout llRoot;
    private LinearLayout llBasetitleBack;
    private TextView tvBasetitleTitle;
    private TextView tvBasetitleOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        findView();
        setContentView(getLayout());
        initView();
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }*/
    }

    private void findView() {
        llRoot = findView(R.id.ll_basetitle_root);
        llBasetitleBack = findView(R.id.ll_basetitle_back);
        tvBasetitleTitle = findView(R.id.tv_basetitle_title);
        tvBasetitleOK = findView(R.id.tv_basetitle_ok);
        tvBasetitleOK.setOnClickListener(this);
        llBasetitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     重点是重写setContentView，让继承者可以继续设置setContentView
     * 重写setContentView
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
     *
     * 设置中间标题文字
     * @param c
     */
    public void setTitleText(CharSequence c) {
        if (tvBasetitleTitle != null)
            tvBasetitleTitle.setText(c);
    }
    /**
     *
     * 设置中间标题文字
     * @param resId
     */
    public void setTitleText(int resId) {
        if (tvBasetitleTitle != null)
            tvBasetitleTitle.setText(resId);
    }

    /**
     * 设置右边标题
     * @param c
     */
    public void setOKText(CharSequence c) {
        if (tvBasetitleOK != null)
            tvBasetitleOK.setText(c);
    }

    /**
     * 设置右边按钮是否显示
     * @param visible
     */
    public void setOkVisibity(boolean visible) {
        if (tvBasetitleOK != null) {
            if (visible)
                tvBasetitleOK.setVisibility(View.VISIBLE);
            else
                tvBasetitleOK.setVisibility(View.GONE);
        }
    }



    public LinearLayout getLlBasetitleBack() {
        return llBasetitleBack;
    }


    public TextView getTvBasetitleTitle() {
        return tvBasetitleTitle;
    }



    public TextView getTvBasetitleOK() {
        return tvBasetitleOK;
    }
}
