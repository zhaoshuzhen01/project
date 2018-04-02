package com.lubandj.master.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lubandj.master.TApplication;
import com.lubandj.master.httpbean.BaseRequestBean;


import java.util.HashMap;
import java.util.Map;

/**
 * 任务引擎，所有业务接口封装 Created by zhaoshuzhen on 2015/12/8.
 */
public class TaskEngine {

    private static TaskEngine instance;
    private RequestQueue mQueue;
    public static final String URL = "https://api.luban.eme.net.cn/customer";
//    public static final String URL = "https://app.api.lubandj.com/customer";

    public RequestQueue getQueue() {
        return mQueue;
    }

    private TaskEngine() {
        mQueue = Volley.newRequestQueue(TApplication.context);
    }

    public static synchronized TaskEngine getInstance() {
        if (instance == null) {
            instance = new TaskEngine();
        }
        return instance;
    }

    /**
     * 不带token参数的接口
     */
    public void commonHttps(final int method, final Object params, Response.Listener listener,
                            Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "3MrKNiC5VJwuCeOWNa7Fr6S3cqxWQWr8");
//                map.put("key", "3MrKNiC5VJwuCeOWNa7Fr6S3cqxWQWr8");
                return map;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                BaseRequestBean bean = new BaseRequestBean(method, params, false);
                return new Gson().toJson(bean).getBytes();
            }
        };
        mQueue.add(request);
    }

    /**
     * 带token参数的接口
     */
    public void tokenHttps(final int method, final Object params, Response.Listener listener,
                           Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.POST, URL, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "3MrKNiC5VJwuCeOWNa7Fr6S3cqxWQWr8");
                //                map.put("key", "3MrKNiC5VJwuCeOWNa7Fr6S3cqxWQWr8");
                return map;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                BaseRequestBean bean = new BaseRequestBean(method, params, true);
                return new Gson().toJson(bean).getBytes();
            }
        };
        mQueue.add(request);
    }

    /**
     * 上传头像
     *
     * @param listener
     * @param errorListener
     */
    public void faceHttps(final int method, final Object params, Response.Listener listener,
                          Response.ErrorListener errorListener) {
//        StringRequest request = new StringRequest(Request.Method.POST, URL, listener, errorListener) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("key", "6EA0nvOz3aU5FAFquVTM9oF1yxWB8OpU");
//                return map;
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                BaseRequestBean bean = new BaseRequestBean(method, params, true);
//                return new Gson().toJson(bean).getBytes();
//            }
//        };
//        mQueue.add(request);
    }
}