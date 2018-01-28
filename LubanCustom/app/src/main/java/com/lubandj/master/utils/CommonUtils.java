package com.lubandj.master.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.ActUtils;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.customer.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * function:
 * author:yangjinjian
 * date: 2017-12-15
 * company:九州宏图
 */

public class CommonUtils {
    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }


    public static String getUniqueCode() {
        String androidID = Settings.Secure.getString(TApplication.context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

    /**
     * 获取deviceid
     *
     * @return
     */
    public static String getDeviceid() {
        String deviceid = SpfUtil.instance().getString(Canstance.DEVIED_ID);
        return TextUtils.isEmpty(deviceid) ? CommonUtils.getUniqueCode() : deviceid;
    }

    public static BaseResponseBean generateEntityByGson(Context context, String result, BaseResponseBean rb) {
        BaseResponseBean bean;
        try {
            bean = new Gson().fromJson(result, rb.getClass());
            if (bean != null) {//解析未错
                if (bean.code != 0) {
                    if (bean.code == 104) {
                        CommonUtils.tokenNullDeal(context);
//                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        Toast.makeText(context, bean.message, Toast.LENGTH_SHORT).show();
                        bean = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(context, "返回数据解析出错:" + result);
            bean = null;
        }
        return bean;
    }

    public static void fastShowError(Context context, VolleyError volleyError) {
        try{
            if (volleyError != null) {
                if (volleyError.networkResponse != null) {
                    String format = String.format(context.getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                    ToastUtils.showShort(context, format);
                    Logger.e(volleyError.getMessage());
                }
            }

        }catch (Exception e){
            Logger.e(e.toString());
        }

    }

    public static int getUid() {
        return SPUtils.getInstance().getInt(Canstance.UID);
    }

    public static void setUid(int uid) {
        SPUtils.getInstance().put(Canstance.UID, uid);
    }

    public static String getToken() {
        return SPUtils.getInstance().getString(Canstance.TOKEN);
    }

    public static void setToken(String token) {
        SPUtils.getInstance().put(Canstance.TOKEN, token);
    }

    public static int getMsgCount() {
        return SPUtils.getInstance().getInt(Canstance.MSG);
    }

    public static void setMsgCount(int msgCount) {
        int mCount = getMsgCount();
        mCount = mCount + msgCount;
        if (msgCount == 0)
            mCount = 0;
        SPUtils.getInstance().put(Canstance.MSG, mCount);
    }

    public static String getPhone() {
        return SPUtils.getInstance().getString(Canstance.KEY_SP_PHONE_NUM);
    }

    public static void setPhone(String phone) {
        SPUtils.getInstance().put(Canstance.KEY_SP_PHONE_NUM, phone);
    }


    /**
     * token失效时处理
     */
    public static void tokenNullDeal(Context context) {
        ToastUtils.showShort(context, "您的登录信息已失效，请重新登录");
        logOut(context);
    }

    /**
     * 登出
     */
    public static void logOut(Context context) {
        PushManager.getInstance().unBindAlias(context, CommonUtils.getUid() + "", true, CommonUtils.getUid() + "");
//        TApplication.context.setGetuiTag(-1);
        CommonUtils.setToken("");
        CommonUtils.setUid(-1);
        ActUtils.getInstance().finishAllALiveAcitity();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}