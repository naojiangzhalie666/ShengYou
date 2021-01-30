package com.xiaoshanghai.nancang.constant;

import android.os.Environment;

import com.xiaoshanghai.nancang.BuildConfig;

public class Constant {

    public static final String VERSION = "version";

    public static final String WEB_URL = "webUrl";

    public static final String WEB_TITLE = "webTitle";

    //微信分享app_id
//    public static final String APP_ID = "wxf6cc75600ec34445";
    public static final String APP_ID = "wx94e27a54ac4f8b23";
    //腾讯IM appkey
    public static final int IM_APP_KEY = 1400405278;
    //等待短信总时间
    public static final int WAIT_SMS_TIME = 60000;
    //等待短信倒计时间隔时间
    public static final int SMS_INTERVAL = 1000;
    //隐私政策
    public static final String PRIVACY = "0";
    //用户协议
    public static final String USER_AGREEMENT = "1";
    //手机号
    public static final String PHONE_NUM = "phoneNum";
    //照相机
    public static final int CAMERA = 1024;
    //裁剪返回
    public static final int CROP_RESULT = 1025;
    //相册返回
    public static final int GALLERY_RESULT = 1026;
    //注册失败
    public static final String REGISTER_ERROR = "0";
    //注册成功
    public static final String REGISTER_SUCCESS = "1";
    //用户已注册
    public static final String REGISTER_REPEAT = "2";
    //	1验证码正确 6手机号为空 8验证码为空 9验证码超时
    public static final String CODE_SUCCESS = "1";
    // 5验证码错误
    public static final String CODE_ERROR = "5";
    //9验证码超时
    public static final String CODE_TIME_OUT = "9";
    //首页房间type
    public static final String HOME_RADIO_TYPE = "home_radio_type";
    //缓存文件保存路径
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID;
    //朋友圈跳转详情携带对象
    public static final String FRIEND_CIRCLE_RESULT = "friend_circle_result";
    //朋友圈话题ID 跳转详情
    public static final String TOPIC_ID = "topic_id";
    //家族ID key
    public static final String FAMILY_ID = "family_id";

    public static final String FAMILY_RESULT = "family_result";

    public static final String FAMILY_USER = "family_user";
    //titleBarView 右边按钮点击区分
    public static final int RIGHT_CLICK_1 = 1;
    //titleBarView 右边按钮点击区分
    public static final int RIGHT_CLICK_2 = 2;
    //页面调转携带用户ID KEY
    public static final String USER_ID = "userId";
    //页面调转携带用户家族信息 KEY
    public static final String MY_FAMILY = "myFamily";
    //页面跳转用户MineReslut
    public static final String MINE_RESLUT = "mineReslut";
    //跳转修改昵称页面
    public static final int NICK_NAME_REQUEST_CODE = 10001;
    //跳转个人介绍
    public static final int SELF_REQUEST_CODE = 10002;
    //跳转个人相册
    public static final int MY_PHOTOS = 10003;
    //昵称key
    public static final String NICK_NAME = "nickName";
    //个人介绍
    public static final String SELF = "self";
    //试驾点击
    public static final int CAR_CLICK = 1;
    //头饰使用点击
    public static final int HEADWEAR_CLICK = 2;
    //我的礼物-金币-收入
    public static final int MY_GIFT_GOLD_INCOME = 1;
    //我的礼物-辣椒-收入
    public static final int MY_GIFT_CHILI_INCOME = 2;
    //我的礼物-金币-支出
    public static final int MY_GIFT_GOLD_OUTLAY = 3;
    //我的礼物-辣椒-支出
    public static final int MY_GIFT_CHILI_OUTLAY = 4;
    //是否自己
    public static final String IS_SELF = "is_self";
    //礼物对象
    public static final String DECKS = "decks";
    //贵族列表
    public static final String NOBLE_LIST = "noble_list";

    public static final String CHAT_INFO = "chatInfo";
    //房间礼物接口返回值传递
    public static final String ROOM_GIFT = "room_gift";
    //普通礼物传值
    public static final String ROOM_GENERALS = "generals";

    public static final String FRAGMENT_INDEX = "fragment_index";

    public static final String ROOM_ID = "room_id";

    public static final String SELF_NOBLE = "self_noble";

    public static final String GIFT_NOBLE = "gift_noble";

    public static final String ROOM_TITLE = "room_title";

    public static final String ROOM_COMMENT = "ROOM_COMMENT";
    //是否开启特效
    public static final String IS_OPEN_TX = "is_open_tx";
    //是否开启公屏
    public static final String IS_OPEN_GP = "is_open_gp";
    //是否开启礼物值
    public static final String IS_OPEN_LWZ = "is_open_lwz";
    //房间设置
    public static final String ROOM_INFO = "room_info";
    //房间typeid传值
    public static final String ROOM_TYPE_ID = "room_type_id";
    //房间标签
    public static final String ROOM_TYPE = "room_type";
    //房间背景条目
    public static final String ROOM_BG = "room_type";
    //房间用户
    public static final String ROOM_USER = "room_user";
    //自己的角色
    public static final String ROOM_SELF_KIND = "room_self_kind";
    //房间座位信息
    public static final String ROOM_USER_SEAT = "room_user_seat";
    //是否关注
    public static final String ROOM_IS_FOLLOW = "room_is_follow";
    //跳转在线列表标记
    public static final String ROOM_MARK = "room_mark";
    //跳转在线列表 - 抱麦
    public static final int ROOM_PICK = 1;
    //跳转在线列表 - 座位序号
    public static final String ROOM_SEAT_INDEX = "room_seat_index";
    //在线列表会跳到房间携带的信息
    public static final String ON_LINE_USER_INFO = "on_line_user_info";


    //抽奖
    public static final String LOTTERY_TYPE = "lotter_type";

    //抽奖公示(弹幕)价格
    public static final int PUBLICITY_PRICE_1 = 4998;
    //抽奖公示(弹幕+飘屏)价格
    public static final int PUBLICITY_PRICE_2 = 9998;

    //送礼(弹幕+飘屏)价格
    public static final int PUBLICITY_PRICE_3 = 9998;
    //送礼(弹幕+飘屏)价格
    public static final int PUBLICITY_PRICE_4 = 99998;

    //好友选择 -- 状态
    public static final String BUDDY_KEY = "buddy_key";
    //好友选择 -- 分享话题
    public static final String TOPIC = "topic_share";
    //好友选择 -- 分享家族
    public static final String CLAN = "clan_share";
    //公聊大厅at
    public static final String PUBLIC_CHAT = "public_chat";
    //房间分享
    public static final String SHARE_ROOM = "share_room";

    //举报跳转 - key
    public static final String REPOTR_TYPE = "report_type";

    //举报类型 - 房间
    public static final String REPORT_TYPE_ROOM = "1";

    //举报类型 - 个人
    public static final String REPORT_TYPE_USER = "2";
    //被举报对象ID
    public static final String REPORT_ID = "reportId";
    //跳转等级介绍
    public static final String GRADE_KEY = "grade_key";
    //用户等级
    public static final String GRADE_USER_LV = "0";
    //魅力等级
    public static final String GRADE_CHARM_LV = "1";

    public static final String CHAT_TYPE = "chat_type";

    public static final String CHAT_USER = "chatUser";

    public static final String CHAT_GROUP = "chatGroup";

    public static final String PUBLIC_CHAT_GROUP = "public_chat_group";
    //公聊大厅ID
    public static final String PUBLIC_CHAT_GROUP_ID = "20201103";
    //支付结果
    public static final String PAY_RESULT = "pay_result";

    public static final String CASH_KEY = "cash_key";

    public static final String CASH_TYPE = "cash_type";

    public static boolean isAnchorColse = false; //麦克风是不被房主或是管理员封禁，当麦克风被管理员关闭自己不能打开

    public static boolean isMaikStatus = false; //麦克风是否关闭 ，这里为自己操作麦克风的开关 默认麦克风关闭
}
