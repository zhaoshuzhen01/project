package com.example.baselibrary.recycleview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ${zhaoshuzhen} on 2017/9/2.
 */

public class RecycleViewScollOberver {
    private  IScollListener iScollListener ;

    public RecycleViewScollOberver(IScollListener iScollListener){
        this.iScollListener = iScollListener ;
    }

    public static void  onResview(RecyclerView mRecycleveiw, final LinearLayoutManager manager, final int count,final IScollListener iScollListener){
        mRecycleveiw.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int first =   manager.findFirstCompletelyVisibleItemPosition();
                        int last =   manager.findFirstCompletelyVisibleItemPosition();
                        if (first==0){
                          iScollListener.scollFirstItem();
                        }else if (last==count-1){
                          iScollListener.scolLastItem();
                        }else {
                           iScollListener.scolFling();
                        }

                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
}
