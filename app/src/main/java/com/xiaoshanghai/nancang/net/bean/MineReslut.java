package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class MineReslut extends BaseResult {


    private String id;                      //	用户id，不可给用户看
    private int userNumber;                 //  用户编号，用户看的id
    private String userName;                //	用户昵称
    private String userPhone;               //	用户手机号
    private String userPassword;            //	用户密码，此处无意义
    private String wechatOpenid;            //	用户微信openid，此处无意义
    private String userSalt;                //	用户密码盐，此处无意义
    private String userBirthday;            //	用户生日
    private int userSex;                    //	用户性别，1男，0女
    private String userPicture;             //  用户头像
    private String userIntroduce;           //  用户简介
    private String userVoice;               //	此处无意义
    private int userStatus;                 //	此处无意义
    private int userType;                   //	0机器人 1普通用户
    private String createTime;              //	账号创建时间
    private int active;                     //	此处无意义
    private String userNoble;               //	用户贵族 0无 1男爵 2子爵 3伯爵 4侯爵 5公爵 6国王 7皇帝
    private int userLevel;                  //	用户等级
    private int charmLevel;                 //	魅力等级
    private int followTotal;                //	关注人数
    private int fanTotal;                   //  粉丝人数
    private String roomId;                  //	TA所在房间id,没有则为空
    private String ownerRoomId;             //  TA所创建房间id,没有则为空
    private Decks headwear;        //	当前用户头饰，若没有，为空
    private Decks car;                  //当前用户座驾，若没有，为空
    private List<UserPicturesResult> userPictures;  //	用户相册
    private int isBeautiful;

    public int getIsBeautiful() {
        return isBeautiful;
    }

    public void setIsBeautiful(int isBeautiful) {
        this.isBeautiful = isBeautiful;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    public String getUserVoice() {
        return userVoice;
    }

    public void setUserVoice(String userVoice) {
        this.userVoice = userVoice;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUserNoble() {
        return userNoble;
    }

    public void setUserNoble(String userNoble) {
        this.userNoble = userNoble;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getCharmLevel() {
        return charmLevel;
    }

    public void setCharmLevel(int charmLevel) {
        this.charmLevel = charmLevel;
    }

    public int getFollowTotal() {
        return followTotal;
    }

    public void setFollowTotal(int followTotal) {
        this.followTotal = followTotal;
    }

    public int getFanTotal() {
        return fanTotal;
    }

    public void setFanTotal(int fanTotal) {
        this.fanTotal = fanTotal;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getOwnerRoomId() {
        return ownerRoomId;
    }

    public void setOwnerRoomId(String ownerRoomId) {
        this.ownerRoomId = ownerRoomId;
    }

    public Decks getHeadwear() {
        return headwear;
    }

    public void setHeadwear(Decks headwear) {
        this.headwear = headwear;
    }

    public Decks getCar() {
        return car;
    }

    public void setCar(Decks car) {
        this.car = car;
    }

    public List<UserPicturesResult> getUserPictures() {
        return userPictures;
    }

    public void setUserPictures(List<UserPicturesResult> userPictures) {
        this.userPictures = userPictures;
    }
}
