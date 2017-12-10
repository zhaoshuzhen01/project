package com.example.baselibrary.net;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数（Key和密码）
 * <p>
 */
public class CommonInterceptor implements Interceptor {
    protected   String token;
    protected   String version;
    protected   String deviceId;
    protected   String timestamp;
    protected   String os;

    public CommonInterceptor(){

    }

    public CommonInterceptor(String token1, String version1, String os1) {
        token = token1;
        version = version1;
        os = os1;
    }

    @Override public Response intercept(Chain chain) throws IOException {
//        String marvelHash = ApiUtils.generateMarvelHash(mApiKey, mApiSecret);
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("token", token)
                .addQueryParameter("version", version)
                .addQueryParameter("deviceId", deviceId)
                .addQueryParameter("timestamp", System.currentTimeMillis()+"")
                ;

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}