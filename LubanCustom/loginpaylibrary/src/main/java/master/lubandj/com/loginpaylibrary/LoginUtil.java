package master.lubandj.com.loginpaylibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

/**
 * Created by ${zhaoshuzhen} on 2018/1/18.
 */

public class LoginUtil {
    private ProgressDialog dialog;
    private Context mcontext;
    private static LoginUtil loginUtil = null;
    private Activity mactivity;
    private boolean isauth = false;
    private GetWeiXinMap getWeiXinMap;
    public static String UNIONID = "unionid";
    public static String OPEN_ID = "openid";
    public static String UID = "uid";
    public static String SCREEN_NAME = "screen_name";
    public static String NAME = "name";
    public static String GENDER = "gender";
    public static String COUNTRY = "country";
    public static String PROVINCE = "province";
    public static String CITY = "city";
    public static String ACCESS_TOKEN = "access_token";
    public static String ACCESSTOKEN = "accessToken";
    public static String REFRESH_TOKEN = "refreshToken";
    public static String PROFILE_IMAGE_URL = "profile_image_url";
    public static String ICON_URL = "iconurl";
    public static String LANGUAGE = "language";

    private LoginUtil(Context context) {
        dialog = new ProgressDialog(context);
        mcontext = context;
    }

    public static void setWeixinConfig(Context context) {
        //微信分享/登录
        UMShareAPI.get(context);
        PlatformConfig.setWeixin("wx2337ad10e3cd3cf1", "24dfb2264b3f86a4bc2afeabc83f0b3c");
    }

    public static LoginUtil getLoginUtil(Context context) {
        if (loginUtil == null) {
            synchronized (LoginUtil.class) {
                if (loginUtil == null) {
                    loginUtil = new LoginUtil(context);
                }
            }
        }
        return loginUtil;
    }

    public void setAuthWeixin(Activity activity, GetWeiXinMap getWeiXinMap) {
        mactivity = activity;
        this.getWeiXinMap = getWeiXinMap;
        boolean install = UMShareAPI.get(mactivity).isInstall(mactivity, SHARE_MEDIA.WEIXIN);
        if (install) {
            isauth = UMShareAPI.get(mactivity).isAuthorize(mactivity, SHARE_MEDIA.WEIXIN);
            if (isauth) {
                getWeiXinInfo(mactivity);
            } else {
                UMShareAPI.get(mactivity).doOauthVerify(activity, SHARE_MEDIA.WEIXIN, authListener);
            }
        } else {
            Toast.makeText(mcontext, "微信未安装", Toast.LENGTH_LONG).show();
        }
    }

    public void getWeiXinInfo(Activity activity) {
        UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            SocializeUtils.safeCloseDialog(dialog);
            if (!isauth) {
                isauth = true;
                Toast.makeText(mcontext, "成功了", Toast.LENGTH_LONG).show();
                getWeiXinInfo(mactivity);
            } else {
                getWeiXinMap.getWeiXinMap(data);
                String temp = "";
                for (String key : data.keySet()) {
                    temp = temp + key + " : " + data.get(key) + "\n";
                }
//                Toast.makeText(mcontext,temp, Toast.LENGTH_LONG).show();
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mcontext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mcontext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public interface GetWeiXinMap {
        void getWeiXinMap(Map<String, String> data);
    }
}
