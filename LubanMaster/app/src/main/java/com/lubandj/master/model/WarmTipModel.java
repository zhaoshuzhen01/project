package com.lubandj.master.model;

import com.example.baselibrary.net.RetrofitUtil;

import rx.Subscriber;

/**
 * Created by ${zhaoshuzhen} on 2017/8/29.
 */

public class WarmTipModel {
/*    private IWarmTipModel iWarmTipModel;

    public WarmTipModel(IWarmTipModel iWarmTipModel) {
        this.iWarmTipModel = iWarmTipModel;
    }

    public void getWarmTipData() {
        String routeId = iWarmTipModel.getRouteId();
        WebService service = RetrofitUtil.getInstance(new WebCommonInterceptor()).getBuilder().baseUrl(AppConstant.API_ADRESS).build().create(WebService.class);
        RetrofitUtil.startEngin(service.getWarmTip(routeId), new Subscriber<WarmTipBeen>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                //请求失败
            }

            @Override
            public void onNext(WarmTipBeen userInfo) {
                Tools.tokenExpire(userInfo.getCode(),iWarmTipModel.getActivity());
                //请求成功
                if (userInfo.getCode()==0)
                iWarmTipModel.callBack(userInfo.getResult().getTips());
            }

        });
    }*/

}