package com.xiaoshanghai.nancang.net.bean;

public class StartRecommendResult extends BaseResult {


    /**
     * id : null
     * clanNumber : 9469
     * clanName : 红花会
     * clanPicture : http://192.168.0.12:8080/imgs/clan/1b167e5a-5fcb-4a59-841d-78a2aa81c613
     * coins : null
     * clanId : 1285842603413327873
     * member : 1
     */

    private Object id;
    private int clanNumber;
    private String clanName;
    private String clanPicture;
    private double coins;
    private String clanId;
    private int member;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public int getClanNumber() {
        return clanNumber;
    }

    public void setClanNumber(int clanNumber) {
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

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }
}
