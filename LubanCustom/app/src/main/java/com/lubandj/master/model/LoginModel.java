package com.lubandj.master.model;

import android.content.Context;
import android.util.Log;

import com.example.baselibrary.net.RetrofitUtil;
import com.lubandj.master.been.LoginBeen;
import com.lubandj.master.web.WebCommonInterceptor;
import com.lubandj.master.web.WebService;
import com.lubandj.master.web.WebUrl;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by ${zhaoshuzhen} on 2017/8/29.11
 */

public class LoginModel {
    public LoginModel() {
    }

    public void getLogin(Context context) {
        WebService service = RetrofitUtil.getInstance(new WebCommonInterceptor(context)).getBuilder().baseUrl(WebUrl.HOST_URL).build().create(WebService.class);
        JSONObject object = new JSONObject();
        try {
            object.put("tel","18813003698");
            object.put("verifyCode","1231");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitUtil.startEngin(service.login("10002",object), new Subscriber<LoginBeen>() {
            @Override
            public void onCompleted() {
                Log.e("deal","完成");
            }

            @Override
            public void onError(Throwable e) {
                //请求失败
                Log.e("deal","失败");

            }

            @Override
            public void onNext(LoginBeen loginBeen) {
                Log.e("deal",loginBeen+"");
            }

        });
    }

}