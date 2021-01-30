package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ClanResult extends BaseResult implements MultiItemEntity {

    private String clanId;                      //	家族id
    private String clanName;                //	家族名称
    private String clanPicture;             //	家族图片
    private int member;                 //	家族人数
    private String userId;                  //	族长id
    private String userName;                //	族长名称
    private String userPicture;             //	族长图像
    private int clanNumber;                 //  显示用的家族ID
    private int isClanMember;               //判断自己是否为家族成员 0. 不是 1. 是
    private int itemType = 0;

    public int getIsClanMember() {
        return isClanMember;
    }

    public void setIsClanMember(int isClanMember) {
        this.isClanMember = isClanMember;
    }

    public int getClanNumber() {
        return clanNumber;
    }

    public void setClanNumber(int clanNumber) {
        this.clanNumber = clanNumber;
    }

    public String getClanId() {
        return clanId;
    }

    public void setClanId(String clanId) {
        this.clanId = clanId;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public String getClanPicture() {
        return clanPicture;
    }

    public void setClanPicture(String clanPicture) {
        this.clanPicture = clanPicture;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
