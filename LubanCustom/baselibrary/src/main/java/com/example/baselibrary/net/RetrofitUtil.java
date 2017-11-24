package com.example.baselibrary.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${zhaoshuzhen} on 2017/8/29.
 */

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil;

    private Retrofit.Builder builder;
    private OkHttpClient client;

    private RetrofitUtil(CommonInterceptor commonInterceptor) {
        client =new OkHttpClient.Builder()
                .addInterceptor(commonInterceptor)
                .build();
        builder = new Retrofit.Builder()//增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client);

    }

    public Retrofit.Builder getBuilder() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(commonInterceptor)
                .build();
        builder.client(client);
        return builder;
    }

    private static CommonInterceptor commonInterceptor;
    public static RetrofitUtil getInstance(CommonInterceptor commonInterceptor1) {
        if (retrofitUtil == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil(commonInterceptor);
                }
            }
        }
        commonInterceptor = commonInterceptor1;
        return retrofitUtil;

    }

    public static <T> void startEngin(Observable<T> observable, Subscriber<T> subscriber) {
        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())   //最后在主线程中执行
                .subscribe(subscriber);

    }

}
