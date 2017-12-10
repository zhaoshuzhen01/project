package com.example.baselibrary.net;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private final long DEFAULT_TIMEOUT=3000;

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
       /* OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(commonInterceptor)
                .build();
        builder.client(client);*/
       builder.client(getOkHttpClient());
        return builder;
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder .addInterceptor(commonInterceptor);
        //使用拦截器
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        return httpClientBuilder.build();
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
    /**
     * 请求拦截器，修改请求header
     */
    private class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "text/html; charset=UTF-8")
                    .addHeader("key", "6EA0nvOz3aU5FAFquVTM9oF1yxWB8OpU")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Accept", "*/*")
//                    .addHeader("Access-Control-Allow-Origin", "*")
//                    .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
//                    .addHeader("Vary", "Accept-Encoding")
//                    .addHeader("Cookie", "add cookies here")
                    .build();

            Log.v("zcb", "request:" + request.toString());
            Log.v("zcb", "request headers:" + request.headers().toString());

            return chain.proceed(request);
        }
    }
    public static <T> void startEngin(Observable<T> observable, Subscriber<T> subscriber) {
        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())   //最后在主线程中执行
                .subscribe(subscriber);

    }

}
