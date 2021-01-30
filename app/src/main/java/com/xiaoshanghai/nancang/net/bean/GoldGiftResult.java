package com.xiaoshanghai.nancang.net.bean;


public class GoldGiftResult extends BaseResult {


    /**
     * date : 2020-07-31
     * time : 17:59:53
     * giftName : 大板子
     * giftStaticUrl : http://192.168.0.12:8080/imgs/gift/453ed952-d812-41c0-a08e-45434ba148b3
     * jewelNumber : 67.2
     * price : 112
     * number : 1
     * userName : Chris 郑
     * userPicture : http://192.168.0.12:8080/imgs/portrait/33372a82-6294-46bd-8eb8-450690756afc
     */

    private String date;
    private String time;
    private String giftName;
    private String giftStaticUrl;
    private double jewelNumber;
    private int price;
    private String number;
    private String userName;
    private String userPicture;

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

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftStaticUrl() {
        return giftStaticUrl;
    }

    public void setGiftStaticUrl(String giftStaticUrl) {
        this.giftStaticUrl = giftStaticUrl;
    }

    public double getJewelNumber() {
        return jewelNumber;
    }

    public void setJewelNumber(double jewelNumber) {
        this.jewelNumber = jewelNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}
