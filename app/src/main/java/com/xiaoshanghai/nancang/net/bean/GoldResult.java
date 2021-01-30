package com.xiaoshanghai.nancang.net.bean;

public class GoldResult extends BaseResult {

    /**
     * id : 1284069078650245122
     * coinNumber : 1010
     * amount : 0.01
     * isFirst : 0
     * buyOrder : null
     * createTime : 2020-07-29 09:27:36
     * active : 1
     */

    private String id;
    private int coinNumber;
    private int jewelNumber;
    private double amount;
    private int isFirst;
    private Object buyOrder;
    private String createTime;
    private int active;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getJewelNumber() {
        return jewelNumber;
    }

    public void setJewelNumber(int jewelNumber) {
        this.jewelNumber = jewelNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCoinNumber() {
        return coinNumber;
    }

    public void setCoinNumber(int coinNumber) {
        this.coinNumber = coinNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public Object getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Object buyOrder) {
        this.buyOrder = buyOrder;
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
}
