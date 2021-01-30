package com.tencent.liteav.trtcvoiceroom.ui.base;

import java.io.Serializable;

public class VoiceRoomSeatEntity implements Serializable{

    public String userId;      //用户唯一识别码
    public String userName;    //用户昵称
    public String userAvatar;  //用户头像

    public boolean isUsed;      //座位是否使用

    public boolean isClose;     //座位是否关闭

    public boolean isMute;      //座位是否静音

    /// 【字段含义】用户附加信息
    public UserInfo userInfo;


    public static class UserInfo implements Serializable {

//        /// 【字段含义】用户唯一标识
//        public String userId;
//        /// 【字段含义】用户昵称
//        public String userName;
//        /// 【字段含义】用户头像
//        public String userAvatar;
//        /// 【字段含义】用户性别
//        public int userSex = -1;
//        /// 【字段含义】 用户当前身份 1房主 2管理员 3普通人
//        public int userKind;
//        /// 【字段含义】用户爵位  0平民 1男爵 2子爵 3伯爵 4侯爵 5公爵 6国王 7皇帝
//        public int noble = -1;
//        /// 【字段含义】收到的礼物金币总数
//        public int giftCoinCount;
//        /// 【字段含义】头饰地址
//        public String headwear;

        public String id;
        public int userNumber;
        public String userPicture;
        public String userName;
        public int userSex;
        public int userLevel;
        public int charmLevel;
        public String userBirthday;
        public String clanId;
        public String clanName;
        public String headwear;
        public String car;
        public String noble;
        public int userKind;
        public int giftCoinCount;
        public int seatIndex;
        public String message;
        public int isBlock;
        public String roomId;
        public int isInvisible;
        public int isBeautiful;

    }

}
