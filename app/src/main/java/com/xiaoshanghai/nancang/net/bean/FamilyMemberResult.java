package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class FamilyMemberResult extends BaseResult implements MultiItemEntity {

    private int itemType = 1;

    private String id;                  //用户id
    private int userNumber;             //用户编号 展示所用
    private String userName;            //用户名
    private int userSex;                //用户性别
    private String userPicture;         //用户图片
    private String isPatriarch;         //是否族长
    private int userLevel;              //用户等级
    private int charmLevel;             //魅力等级
    private double coins;               //收入


    @Override
    public int getItemType() {
        return itemType;
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

    public String getIsPatriarch() {
        return isPatriarch;
    }

    public void setIsPatriarch(String isPatriarch) {
        this.isPatriarch = isPatriarch;
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

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }
}
