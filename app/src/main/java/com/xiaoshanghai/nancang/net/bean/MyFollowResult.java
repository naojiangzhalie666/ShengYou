package com.xiaoshanghai.nancang.net.bean;

public class MyFollowResult extends BaseResult {


    /**
     * userId : 10001
     * userNumber : 8888888
     * userPictureUrl : http://192.168.0.12:8080/imgs/portrait/52183efa-5207-4a9e-b021-416cd0a711cf
     * userName : 袁老师
     * userIntroduce : 简单介绍：我是袁正宜
     * userSex : 1
     * userNoble : 4
     * userLevel : 12
     * charmLevel : 19
     * followEach : null
     * inRoom : 1
     */

    private String userId;
    private String userNumber;
    private String userPictureUrl;
    private String userName;
    private String userIntroduce;
    private int userSex;
    private String userNoble;
    private int userLevel;
    private int charmLevel;
    private String followEach;
    private int inRoom;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIntroduce() {
        return userIntroduce;
    }

    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
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

    public String getFollowEach() {
        return followEach;
    }

    public void setFollowEach(String followEach) {
        this.followEach = followEach;
    }

    public int getInRoom() {
        return inRoom;
    }

    public void setInRoom(int inRoom) {
        this.inRoom = inRoom;
    }
}
