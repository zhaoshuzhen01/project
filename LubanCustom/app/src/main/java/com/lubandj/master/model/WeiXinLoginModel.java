package com.lubandj.master.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.been.WeiXinBeen;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.NetWeiXinLoginBeen;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.Map;

import master.lubandj.com.loginpaylibrary.LoginUtil;

/**
 * Created by ${zhaoshuzhen} on 2018/3/7.
 */

public class WeiXinLoginModel {
    private Context context ;
    private DataCall dataCall ;
    public WeiXinLoginModel(Context context,DataCall dataCall){
        this.context = context ;
        this.dataCall = dataCall ;
    }

    public void weixinLogin(Map<String, String> data) {
        NetWeiXinLoginBeen bean = new NetWeiXinLoginBeen();

        bean.unionid = data.get(LoginUtil.UNIONID);
        bean.openid = data.get(LoginUtil.OPEN_ID);
        bean.uid = data.get(LoginUtil.UID);
        bean.screen_name = data.get(LoginUtil.SCREEN_NAME);
        bean.name = data.get(LoginUtil.NAME);
        bean.gender = data.get(LoginUtil.GENDER);
        bean.country = data.get(LoginUtil.COUNTRY);
        bean.province = data.get(LoginUtil.PROVINCE);
        bean.city = data.get(LoginUtil.CITY);
        bean.access_token  = data.get(LoginUtil.ACCESS_TOKEN);
        bean.accessToken = data.get(LoginUtil.ACCESSTOKEN);
        bean.refreshToken = data.get(LoginUtil.REFRESH_TOKEN);
        bean.profile_image_url = data.get(LoginUtil.PROFILE_IMAGE_URL);
        bean.iconurl = data.get(LoginUtil.ICON_URL);
        bean.language = data.get(LoginUtil.LANGUAGE);

        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_WEIXIN_LOGIN,bean, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                BaseEntity msgCenterBeen = new Gson().fromJson(s, BaseEntity.class);
                if (msgCenterBeen != null) {
                    if (msgCenterBeen.getCode() == 0) {
                        WeiXinBeen been = new Gson().fromJson(s, WeiXinBeen.class);
                        dataCall.getServiceData(been);
                    }else {
                        dataCall.getServiceData(msgCenterBeen);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(context.getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(context, format);
                    }
                }
            }
        });
    }

}
