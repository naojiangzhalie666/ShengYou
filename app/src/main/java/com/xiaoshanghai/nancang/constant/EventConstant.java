package com.xiaoshanghai.nancang.constant;

/**
 * Created by lxy on 2019/11/8.
 */

/**
 * EventBus 的事件编号
 */
public class EventConstant {
    //登录成功
    public static final int LOGIN_SUCCESS = 0x111111;

    //修改房间公告
    public static final int ROOM_NOTIY = 0x111112;

    //修改房间名
    public static final int ROOM_NAME = 0x100001;

    //修改房间密码
    public static final int ROOM_PSD = 0x100010;

    //修改房间标签
    public static final int ROOM_TYPE = 0x100011;

    //移除管理员
    public static final int ROOM_REMOVE_MANAGER = 0x100100;

    //房间背景
    public static final int ROOM_BG = 0x100101;

    //房间特效开关
    public static final int ROOM_GIFT = 0x100110;

    //房间公屏开关
    public static final int ROOM_PUBLIC = 0x100111;

    //抽奖大于4999金币 发送 自定义消息
    public static final int LOTTER_PRICE = 0x101000;

    //打开礼物弹窗
    public static final int OPEN_GIFT_WINDOWS = 0x101001;

    //打开或关闭排麦模式
    public static final int ROOM_PICK = 0x101010;

    //关闭ChatWithAct
    public static final int FINIS_CHAT_WITH = 0x101011;

    //修改手机号码
    public static final int CHANGE_PHONE_SUCCESS = 0x101100;

    //随机进入嗨聊房
    public static final int ENTER_HI_CHAT_ROOM = 0x101101;

    //后台房间
    public static final int BACKSTAGE_ROOM = 0x101110;

    //踢出房间
    public static final int OUT_ROOM = 0x110000;

    //退出小弹窗
    public static final int FLOAT_DISMISS = 0x110001;

    //销毁房间
    public static final int ROOM_DESTROY = 0x110010;

}
