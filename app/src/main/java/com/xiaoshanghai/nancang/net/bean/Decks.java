package com.xiaoshanghai.nancang.net.bean;

public class Decks extends BaseResult{
    /**
     * id : 1
     * deckType : 2
     * deckName : 法拉利
     * deckStaticUrl : http://192.168.0.12:8080/imgs/gift/1.png
     * deckUrl : http://192.168.0.12:8080/imgs/gift/1.png
     * isLimit : 0
     * costType : 1
     * costNumber : 666
     * useDay : 7
     * createTime : 2020-07-13 12:08:12
     * active : 1
     * validity : 1
     * used : 1
     */

    private String id;
    private int deckType;
    private String deckName;
    private String deckStaticUrl;
    private String deckUrl;
    private int isLimit;
    private int costType;
    private int costNumber;
    private int useDay;
    private String createTime;
    private int active;
    private int validity;
    private int validityDays;
    private int used;
    private boolean isSelect;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeckType() {
        return deckType;
    }

    public void setDeckType(int deckType) {
        this.deckType = deckType;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckStaticUrl() {
        return deckStaticUrl;
    }

    public void setDeckStaticUrl(String deckStaticUrl) {
        this.deckStaticUrl = deckStaticUrl;
    }

    public String getDeckUrl() {
        return deckUrl;
    }

    public void setDeckUrl(String deckUrl) {
        this.deckUrl = deckUrl;
    }

    public int getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(int isLimit) {
        this.isLimit = isLimit;
    }

    public int getCostType() {
        return costType;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public int getCostNumber() {
        return costNumber;
    }

    public void setCostNumber(int costNumber) {
        this.costNumber = costNumber;
    }

    public int getUseDay() {
        return useDay;
    }

    public void setUseDay(int useDay) {
        this.useDay = useDay;
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

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
