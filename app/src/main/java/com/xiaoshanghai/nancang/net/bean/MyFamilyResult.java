package com.xiaoshanghai.nancang.net.bean;

public class MyFamilyResult extends BaseResult {
    private String clanId;                  //	家族id
    private String clanName;                //	家族名称
    private String clanPicture;             //	家族图片
    private String clanStatus;              //	家族状态
    private String createTime;              //	创建时间
    private String active;                  //	家族状态
    private int member;                     //	家族人数
    private int clanNumber;                 //  家族显示ID

    public String getClanId() {
        return clanId;
    }

    public void setClanId(String clanId) {
        this.clanId = clanId;
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

    public String getClanStatus() {
        return clanStatus;
    }

    public void setClanStatus(String clanStatus) {
        this.clanStatus = clanStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public int getClanNumber() {
        return clanNumber;
    }

    public void setClanNumber(int clanNumber) {
        this.clanNumber = clanNumber;
    }
}
