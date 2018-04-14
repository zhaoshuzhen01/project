package com.lubandj.master.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.baselibrary.widget.SlideShowView;
import com.lubandj.master.R;
import com.example.baselibrary.HomeBeen;
import com.example.baselibrary.GuangGaoBeen;

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
    private List<String> list = new ArrayList<>();
    private List<String> pagerlist = new ArrayList<>();

    private List<List<HomeBeen.InfoBean>> contentlist = new ArrayList<>();
    private List<HomeBeen.InfoBean> viewpagerDatas ;
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
        LinearLayout.LayoutParams params = (LayoutParams) viewPager.getLayoutParams();
        params.height = getResources().getDisplayMetrics().widthPixels / 2;
        viewPager.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams params1 = (LayoutParams) bannerView.getLayoutParams();
        params1.height = getResources().getDisplayMetrics().widthPixels /2;
    }


    //初始化ViewPager
    public void initViewPager(Context context,List<HomeBeen.InfoBean> datas) {
       for (int i=0;i<datas.size();i++){

           if (i%6==0){
               pagerlist.add("");
               viewpagerDatas = new ArrayList<>();
               viewpagerDatas.add(datas.get(i));
               contentlist.add(viewpagerDatas);
           }else {
               viewpagerDatas.add(datas.get(i));
           }
       }
        viewPager.setData(pagerlist, SlideShowView.TOPCONTENT, this,contentlist);

    }
    public void initGuangGao(List<GuangGaoBeen.InfoBean> guangGaolists){
        for (int i=0;i<guangGaolists.size();i++){
            list.add("1");
        }
       bannerView.setGuangGaolists(guangGaolists);
        bannerView.setData(list, SlideShowView.GUANG, null,null);
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
    public View getView(int position) {
        TableView tableView = new TableView(mcontext);
        if (position<contentlist.size()){
            List<HomeBeen.InfoBean> mdata = contentlist.get(position);
            tableView.setData(mdata);
        }
        return tableView;
    }
}

