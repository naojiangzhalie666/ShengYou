package com.xiaoshanghai.nancang.net.bean;

public class NewGoldResult extends BaseResult {

    /**
     * id : 7
     * jewelNumber : 500000
     * amount : 50000
     * isFirst : 0
     * createTime : 2020-12-14 21:44:15
     * active : 1
     */

    private String id;
    private int jewelNumber;
    private int amount;
    private int isFirst;
    private String createTime;
    private int active;
    private boolean isSelect;
    private int charge;

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getJewelNumber() {
        return jewelNumber;
    }

    public void setJewelNumber(int jewelNumber) {
        this.jewelNumber = jewelNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
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
