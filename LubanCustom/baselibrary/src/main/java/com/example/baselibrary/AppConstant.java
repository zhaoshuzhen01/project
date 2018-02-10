package com.example.baselibrary;

/**
 * Created by xts on 2017/3/9.
 * 常量接口
 */
public class AppConstant {

    /**
     * 客户端文件根目录
     */
    public static final String root = "/Banmi/";
    /**
     * 图片缓存的路径
     */
    public static final String imageCachePath = root + "cache/";
    /**
     * 视频文件缓存路径
     */
    public static final String videoCachePath = root + ".video/";
    /**
     * 临时文件后缀名
     */
    public static final String tempFileExtension = ".cache";

    /**
     * 错误日志文件
     */
    public static final String log = "log.txt";
    /**
     * 网页JS调用本地JS对象的名字
     */
    public static final String javascriptInterfaceName = "native";


    public static final int LIST_DATA_SIZE = 15;


    // 第三方平台标记
    public static final String providerQQ ="QQ";
    public static final String providerWechat = "wechat";
    public static final String providerWeibo = "weibo";


    //系统标识
    public static final String system_Android = "android";
    public static final String system_Ios = "ios";



    /**
     * 发布最大图片张数
     */
    public static final int IMG_COUNT_MAX = 9;
    /**
     * 说说最大发布发布数
     */
    public static final int CONTENT_SIZE_MAX = 800;
    /**
     * 图片压缩最大宽度
     */
    public static final int IMG_ZOOM_WIDTH_MAX = 640;
    /**
     * 图片压缩最大高度
     */
    public static final int IMG_ZOOM_HEIGHT_MAX = 800;
    /**
     * 图片压缩质量
     */
    public static final int IMG_ZOOM_QUALITY = 80;

    public static final int IO_BUFFER_SIZE = 100 * 100;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    /**
     * 地图界面消息toggle
     */
    public static final String MESSAGE_TOGGLE = "message_toggle";
    /**
     * 最后一次访问过的路线id
     */
    public static final String LAST_ROUTE_ID = "last_route_id";
    /**
     * 是否为初次进入地图,用于判断是否显示引导遮层
     */
    public static final String IS_FIRST_IN_MAP = "is_first_in_map";
    /**
     * 是否为初次进入问题详情,用于判断是否显示引导遮层
     */
    public static final String IS_FIRST_IN_QUESTION_DETAIL = "is_first_in_question_detail";

    /**
     * Screen on/off
     */
    public static final String IS_SCREEN_ON = "is_screen_on";


    public static final boolean DEBUG = Boolean.parseBoolean("true");
    public static final String APPLICATION_ID = "com.just.library";
    public static final String BUILD_TYPE = "debug";
    public static final String FLAVOR = "";
    public static final int VERSION_CODE = 1;
    public static final String VERSION_NAME = "1.0";
    public static final String OS = "android";

    public static int TOKEN_EXPIRE_CODE = 20170707;
    public static final String APP_ID = "wx2281f6dda3069029";
    //支付webview所需参数
    public static final String PAY = "pay";
    //h5抽奖参数
    public static final String LOTTERY = "lottery";
    public static final String NET_ERROR = "网络连接失败！";
    //抽奖活动弹窗限制次数
    public static final int LOTTERY_DIALOG_LIMIT_COUNT = 2;

    /**
     * umeng
     */
    //语音播放点击量
    public static  final String KEY_ROUTE_DETAIL_AUDIO = "point_route_audio_play_click";
//线路规划
    public static  final String KEY_ROUTE_DETAIL_TIMELINE = "point_route_gain_timeline_click";
    //智能地图
    public static  final String KEY_ROUTE_DETAIL_MAP = "point_route_gain_map_click";
    //景点语音
    public static  final String KEY_ROUTE_DETAIL_VIEWSPOT = "point_route_gain_viewspot_voice_click";
    //绝佳拍摄角度点击数
    public static  final String KEY_ROUTE_DETAIL_SHOOT_ANGLE= "point_route_gain_shoot_angle_click";
    //区分线路规划
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_TIMELINE = "point_route_unlock_before_timeline_click";
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_TIMELINE_AFTER = "point_route_unlock_after_timeline_click";
    //绝佳拍摄地
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_SHOOT = "point_route_unlock_before_shoot_angle_click";
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_SHOOT_AFTER = "point_route_unlock_after_shoot_angle_click";
    //线路语音
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_VIEWSPOT= "point_route_unlock_before_viewspot_voice_click";
    public static  final String KEY_ROUTE_DETAIL_UNLOCK_VIEWSPOT_AFTER= "point_route_unlock_after_viewspot_voice_click";
    //全部评论点击数
    public static  final String KEY_ALL_COMMENT= "point_route_all_comment_click";
    /*时间轴top
    * */
    public static  final String KEY_TIMELINE_SHOOT= "point_timeline_top_shoot_angle_click";

    public static  final String KEY_TIMELINE_HOTEL= "point_timeline_top_hotel_click";

    public static  final String KEY_TIMELINE_FOOD = "point_timeline_top_food_click";

    public static  final String KEY_TIMELINE_VOICE= "point_timeline_top_voice_click";

    public static  final String KEY_TIMELINE_TIPS= "point_timeline_top_tips_click";

    public static  final String KEY_TIMELINE_MAP= "point_timeline_top_map_click";


    /**
     * 问路卡点击数	区分不同线路、poi	N	point_timeline_where_click
     到这去点击数	区分不同线路、poi	N	point_timeline_navigator_click
     写评价点击数	区分线路	N	point_timeline_bottom_addcomment_click
     全部评论点击数	区分线路	N	point_timeline_bottom_allcomment_click
     分享按钮点击数	区分线路	N	point_timeline_bottom_share_click
     打分统计	区分线路、poi	N	point_timeline_viewspot_grade_click
     关联线路推荐点击数	区分线路	N	point_timeline_bottom_recommend_route_click
     景点地图点击数	区分线路	N	point_timeline_bottom_map_click

     */

    public static  final String KEY_TIMELINE_WHERE= "point_timeline_where_click";

    public static  final String KEY_TIMELINE_NAV= "point_timeline_navigator_click";

    public static  final String KEY_TIMELINE_BOTTOM_ADDCOMMENT= "point_timeline_bottom_addcomment_click";

    public static  final String KEY_TIMELINE_ALLCOMMENT= "point_timeline_bottom_allcomment_click";

    public static  final String KEY_TIMELINE_SHARE = "point_timeline_bottom_share_click";

    public static  final String KEY_TIMELINE_VIEWSPOT= "point_timeline_viewspot_grade_click";

    public static  final String KEY_TIMELINE_BOTTOM_RECOMMEND= "point_timeline_bottom_recommend_route_click";

    public static  final String KEY_TIMELINE_BOTTTOM_MAP= "point_timeline_bottom_map_click";

    /**
     * 语音播放数	区分线路、poi	N	point_map_viewspot_voice_click
     我在哪点击数	区分线路	N	point_map_location_click
     头像点击数	区分线路	N	point_map_userlogo_click
     温馨提示按钮点击数	区分线路	N	point_map_userlogo_tips_click
     卡片到这去点击数	区分线路	N	point_map_popcard_navigator_click
     */
    public static  final String KEY_MAP_VIEWSPOT= "point_map_viewspot_voice_click";

    public static  final String KEY_MAP_LOCATION = "point_map_location_click";

    public static  final String KEY_MAP_USER= "point_map_userlogo_click";

    public static  final String KEY_MAP_TIPS= "point_map_userlogo_tips_click";

    public static  final String KEY_MAP_NAV= "point_map_popcard_navigator_click";

    /**
     * 去完善点击数		N	point_my_gain_reward_additional_info_click
     去评价点击数		N	point_my_gain_reward_comment_click
     去讨论点击数		N	point_my_gain_reward_topic_click

     */
    public static  final String KEY_POINT_REWARD= "point_my_gain_reward_additional_info_click";

    public static  final String KEY_POINT_GAIN_COMMENT= "point_my_gain_reward_comment_click";

    public static  final String KEY_POINT_TOPIC= "point_my_gain_reward_topic_click";

    /**
     * 顶部标签点击量	最新、热门、线路、伴米	N	"point_topic_category_recent_click
     point_topic_category_hot_click
     point_topic_category_route_click
     point_topic_category_banmi_click"
     搜索按钮点击数		N	point_topic_search_text_click
     发布话题按钮点击数			point_topic_add_button_click
     */
    public static  final String KEY_TOPIC_RECENT= "point_topic_category_recent_click";

    public static  final String KEY_TOPIC_HOT= "point_topic_category_hot_click";

    public static  final String KEY_TOPIC_ROUT = "point_topic_category_route_click";

    public static  final String KEY_TOPIC_BANMI= "point_topic_category_banmi_click";

    public static  final String KEY_TOPIC_SEARCH= "point_topic_search_text_click";

    public static  final String KEY_TOPIC_ADD_BUTTON= "point_topic_add_button_click";

    /**
     * 记录访问量pv:point_comment_entry_pv_click
     * 记录访问量uv:point_comment_entry_uv_click
     * 全部评价
     */
    public static  final String KEY_COMMENT_PV= "point_comment_entry_pv_click";
    public static  final String KEY_COMMENT_UV= "point_comment_entry_uv_click";
    public static int REQUEST_CODE = 10010;
    public static final String LOGIN_SUCCESS = "login_success";
}
