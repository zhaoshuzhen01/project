package com.lubandj.master.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.baselibrary.adapter.MainAdapter;
import com.example.baselibrary.adapter.MyViewPagerAdapter;
import com.example.baselibrary.widget.CustomViewPager;
import com.example.baselibrary.widget.SlideShowView;
import com.example.baselibrary.widget.ViewPagerSlide;
import com.lubandj.master.R;
import com.lubandj.master.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/20.
 */

public class HomeTopView extends LinearLayout implements ViewPager.OnPageChangeListener, SlideShowView.LayoutView {

    @InjectView(R.id.banner_view)
    SlideShowView bannerView;
    @InjectView(R.id.viewPager)
    SlideShowView viewPager;
    protected List<View> mList = new ArrayList<>(); //ViewPager的数据源
    private List<String> list = new ArrayList<>();
    private List<String> contentlist = new ArrayList<>();
    private Context mcontext;

    public HomeTopView(Context context) {
        super(context);
        initView(context);
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mcontext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_home_top, this);
        ButterKnife.inject(this, view);
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        bannerView.setData(list, SlideShowView.GUANG, null);
        LinearLayout.LayoutParams params = (LayoutParams) viewPager.getLayoutParams();
        params.height = getResources().getDisplayMetrics().widthPixels / 2;
        viewPager.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams params1 = (LayoutParams) bannerView.getLayoutParams();
        params1.height = getResources().getDisplayMetrics().widthPixels /2;
    }


    //初始化ViewPager
    public void initViewPager(Context context) {
        mList.add(new TableView(context));
        mList.add(new TableView(context));
       /* MyViewPagerAdapter  adapter = new MyViewPagerAdapter(mList);
        viewPager.setAdapter(adapter);
        viewPager.setScrollable(true);*/
        contentlist.add("1");
        contentlist.add("1");
        viewPager.setData(contentlist, SlideShowView.TOPCONTENT, this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public View getView() {
        return new TableView(mcontext);
    }
}

