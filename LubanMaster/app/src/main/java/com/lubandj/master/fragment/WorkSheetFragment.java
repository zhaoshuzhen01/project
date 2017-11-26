package com.lubandj.master.fragment;


import android.os.Bundle;
import android.widget.ListView;

import com.example.baselibrary.BaseFragment;
import com.lubandj.master.R;

/**
 * Created by ${zhaoshuzhen} on 2017/9/5.
 */

public class WorkSheetFragment extends BaseFragment {
    public static boolean sNeedRefresh = false;
    private boolean isVisible ;
    private ListView listView;
    public static WorkSheetFragment newInstance() {
        WorkSheetFragment myFragment = new WorkSheetFragment();
        Bundle bundle = new Bundle();
//        bundle.putSerializable(DATAS, bean);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    protected void initData() {
        if (isVisible&& isFirst ) {
//            getData(1);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (isFirst&&listView!=null) {
//            refreshLayout.startRefresh();
          initData();
        }
    }

    ;

    protected void onInvisible() {
    }

    @Override
    protected void initView() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_worksheet;
    }




    @Override
    public void onResume() {
        super.onResume();
        if (sNeedRefresh){
//            getData(1);
            sNeedRefresh = false;
        }
    }
}
