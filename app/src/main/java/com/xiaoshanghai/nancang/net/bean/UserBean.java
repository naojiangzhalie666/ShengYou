package com.xiaoshanghai.nancang.net.bean;

public  class UserBean extends BaseResult{
    /**
     * id : 10001
     * userNumber : 8888888
     * userName : 袁正宜
     * userPhone : 18811398512
     * userPassword :
     * wechatOpenid : null
     * userSalt :
     * userBirthday : 2000-06-20 00:00:00
     * userSex : 1
     * userPicture : http://39.101.164.232/imgs/user/1001.png
     * userIntroduce : 简单介绍：我是袁正宜
     * userVoice : http://39.101.164.232/voices/user/1001.mp3
     * userStatus : 1
     * userType : 1
     * createTime : 2020-06-20 16:16:54
     * active : 1
     * isClanElder : 0
     * openTeenager : 0
     */

    private String id;
    private int userNumber;
    private String userName;
    private String userPhone;
    private String userPassword;
    private Object wechatOpenid;
    private int isInvisible;
    private int isBeautiful;
    private String userSalt;
    private String userBirthday;
    private int userSex;
    private String userPicture;
    private String userIntroduce;
    private String userVoice;
    private int userStatus;
    private int userType;
    private String createTime;
    private int active;
    private int isClanElder;
    private int openTeenager;

    public int getIsInvisible() {
        return isInvisible;
    }

    public void setIsInvisible(int isInvisible) {
        this.isInvisible = isInvisible;
    }

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

    public Object getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(Object wechatOpenid) {
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

    public int getIsClanElder() {
        return isClanElder;
    }

    public void setIsClanElder(int isClanElder) {
        this.isClanElder = isClanElder;
    }

    public int getOpenTeenager() {
        return openTeenager;
    }

    public void setOpenTeenager(int openTeenager) {
        this.openTeenager = openTeenager;
    }
}
