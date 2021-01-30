package com.xiaoshanghai.nancang.net.bean;

public class MikesBean extends BaseResult{
    /**
     * id : 1288045514108616706
     * roomId : 2
     * userId : 1281782368759336962
     * mikeOrder : 0
     * mikeStatus : 1
     * createTime : 2020-07-28 17:35:56
     * active : 1
     * userName : 大侠快走
     * userSex : 1
     * userPicture : http://192.168.0.12:8080/imgs/portrait/9ee762b5-862d-4b86-a8fd-a42a7b5fb975
     * headwear :
     * noble : 0
     * giftCoinCount : 6090
     */

    private String id;
    private String roomId;
    private String userId;
    private int mikeOrder;
    private int mikeStatus;
    private String createTime;
    private int active;
    private String userName;
    private int userSex;
    private String userPicture;
//    private CarAndHeadwearResult headwear;
    private String headwear;
    private int noble;
    private int giftCoinCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMikeOrder() {
        return mikeOrder;
    }

    public void setMikeOrder(int mikeOrder) {
        this.mikeOrder = mikeOrder;
    }

    public int getMikeStatus() {
        return mikeStatus;
    }

    public void setMikeStatus(int mikeStatus) {
        this.mikeStatus = mikeStatus;
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

    public String getHeadwear() {
        return headwear;
    }

    public void setHeadwear(String headwear) {
        this.headwear = headwear;
    }

    public int getNoble() {
        return noble;
    }

    public void setNoble(int noble) {
        this.noble = noble;
    }

    public int getGiftCoinCount() {
        return giftCoinCount;
    }

    public void setGiftCoinCount(int giftCoinCount) {
        this.giftCoinCount = giftCoinCount;
    }
}