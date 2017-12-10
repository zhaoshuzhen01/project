package com.lubandj.master.web;


import com.lubandj.master.been.LoginBeen;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${zhaoshuzhen} on 2017/8/29.
 */

public interface WebService {


    /**
     * 登陆
     * @param method
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("10002")
    Observable<LoginBeen> login(
            @Field("params") JSONObject params);

/*    *//**
     * 获取温馨提示
     * @param routeID
     * @return
     *//*
    @GET("route/tips")
    Observable<WarmTipBeen> getWarmTip(
            @Query("routeID") String routeID
    );

    *//**
     * 获取酒店推荐
     * @param routeID
     * @return
     *//*
    @GET("route/hotels")
    Observable<HotelRecBeen> getRouteBeen(
            @Query("routeID") String routeID
    );

    *//**
     * 线路的语音列表
     * @param routeID
     * @return
     *//*
    @GET("route/audio")
    Observable<AudioListBeen> getAudioListBeen(
            @Query("routeID") String routeID
    );

    *//**
     * 最佳观看角度
     * @param routeID
     * @return
     *//*
    @GET("route/scenes")
    Observable<HomeSceneBeen> getSceneListBeen(
            @Query("routeID") String routeID
    );

    *//**
     * 上传头像
     * @param
     * @return
     *//*
    @Multipart
    @POST("user/editUserInfo")
    Observable<Object> getUpLoadFile(
            @PartMap Map<String, RequestBody> params,
            @Part MultipartBody.Part file
    );

    *//**
     * 多文件上传
     * @param
     * @return
     *//*
    @Multipart
    @POST("v3/quora/images")
    Observable<Object> getMoreUpLoadFile(
            @PartMap Map<String, String> params,
            @Part List<MultipartBody.Part> parts
    );

    *//**
     * 获取券
     * @param
     * @return
     *//*
    @GET("event20171001/myCoupons")
    Observable<CardBeen> getCardBeen(
            @Query("page") String page,
            @Query("type") String type
    );

    *//**
     * 当日是否抽过奖
     *//*
    @GET("event20171001/drawnToday")
    Observable<DrawnTodayBean> getDrawnToday();

    *//**
     * 兑换
     *//*
    @FormUrlEncoded
    @POST("event20171001/cash")
    Observable<ResultDuihuan> codeChange(
            @Field("token") String token,
            @Field("id") String id);

    *//**
     * 抽奖
     * @return
     *//*
    @FormUrlEncoded
    @POST("event20171001/draw")
    Observable<DrawBean> postDraw(@Field("lat") String lat, @Field("lng") String lng, @Field("token") String token);

    *//**
     * 提交电话号码和微信号码
     * @return
     *//*
    @FormUrlEncoded
    @POST("event20171001/contactInfo")
    Observable<ContactInfoBean> postContactInfo(@Field("phone") String phone, @Field("wechat") String wechat, @Field("token") String token);*/

}
