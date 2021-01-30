package com.xiaoshanghai.nancang.net.bean;

public class FamilyRankResult extends BaseResult {

    /**
     *
     * id
     * [string]
     * 是	家族表主键id，查询家族传参
     * clanNumber
     * [object]
     * 是	家族编号
     * clanName
     * [string]
     * 是	家族名称
     * clanPicture
     * [string]
     * 是	图片地址
     * coins
     * [double]
     * 是	消费金币
     * clanId
     * [string]
     * 是	家族id
     * member
     * [string]
     * 是	家族成员数
     *
     */

    private String id;
    private String clanNumber;
    private String clanName;
    private String clanPicture;
    private double coins;
    private String clanId;
    private String member;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClanNumber() {
        return clanNumber;
    }

    public void setClanNumber(String clanNumber) {
        this.clanNumber = clanNumber;
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

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getClanId() {
        return clanId;
    }

    public void setClanId(String clanId) {
        this.clanId = clanId;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}
