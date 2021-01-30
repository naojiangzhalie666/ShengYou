package com.xiaoshanghai.nancang.net.bean;

public class RoomResult extends BaseResult {


    /**
     * roomId : 6
     * roomName : 测试房间6
     * roomTypeName : 测试类型1
     * roomTypeColor : #FF69B4
     * personCount : 5
     * userId : 1277088118586638338
     * userPictureUrl : http://192.168.0.12:8080/imgs/portrait/288a3019-e963-473e-b44c-2b7a70a69955
     * userName : 爱吃骨头的蛇
     * roomLock : 0
     * roomPassword : null
     */

    private String roomId;
    private String roomName;
    private String roomTypeName;
    private String roomTypeColor;
    private int personCount;
    private String userId;
    private String userPictureUrl;
    private String userName;
    private String roomLock;
    private String roomPassword;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeColor() {
        return roomTypeColor;
    }

    public void setRoomTypeColor(String roomTypeColor) {
        this.roomTypeColor = roomTypeColor;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPictureUrl() {
        return userPictureUrl;
    }

    public void setUserPictureUrl(String userPictureUrl) {
        this.userPictureUrl = userPictureUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomLock() {
        return roomLock;
    }

    public void setRoomLock(String roomLock) {
        this.roomLock = roomLock;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }
}
