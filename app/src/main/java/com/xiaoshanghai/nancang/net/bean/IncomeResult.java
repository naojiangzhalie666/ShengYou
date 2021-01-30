package com.xiaoshanghai.nancang.net.bean;

public class IncomeResult extends BaseResult {


    /**
     * date : 2020-07-31
     * time : 17:59:53
     * number : 67.2
     * type : 1
     * targetId : 1289138703628410881
     * gift : {"id":"1","giftName":"大板子","giftStaticUrl":"http://192.168.0.12:8080/imgs/gift/453ed952-d812-41c0-a08e-45434ba148b3","giftUrl":"http://192.168.0.12:8080/imgs/gift/b9f7c8d0-ba20-43f5-9098-5728707239bc","giftType":1,"giftPriceType":2,"giftStatus":0,"giftPrice":112,"nobleId":"2","createTime":"2020-07-17 19:57:10","active":0}
     */

    private String date;
    private String time;
    private double number;
    private int type;
    private String targetId;
    private GiftsResult gift;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public GiftsResult getGift() {
        return gift;
    }

    public void setGift(GiftsResult gift) {
        this.gift = gift;
    }
}
