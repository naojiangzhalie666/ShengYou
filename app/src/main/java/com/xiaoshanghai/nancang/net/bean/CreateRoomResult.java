package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class CreateRoomResult extends BaseResult {


    /**
     * id : 2
     * roomTypeId : 1
     * roomName : 测试房间2
     * createUserId : 10001
     * roomOwnerId : 10001
     * roomLock : 1
     * roomPassword : 1234
     * roomSpecial : 1
     * roomPublic : 1
     * roomExclusion : 1
     * roomStatus : 1
     * roomNoticeTitle : null
     * roomNoticeComment : null
     * roomNumber : 10001
     * createTime : 2020-06-28 15:43:08
     * endTime : null
     * active : 1
     * typeName : 测试类型1
     * mikes : [{"id":"1288045514108616706","roomId":"2","userId":"1281782368759336962","mikeOrder":0,"mikeStatus":1,"createTime":"2020-07-28 17:35:56","active":1,"userName":"大侠快走","userSex":1,"userPicture":"http://192.168.0.12:8080/imgs/portrait/9ee762b5-862d-4b86-a8fd-a42a7b5fb975","headwear":"","noble":0,"giftCoinCount":6090}]
     * mikeSets : [{"id":null,"roomId":"2","mikeOrder":0,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":1,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":2,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":3,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":4,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":5,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":6,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":7,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1},{"id":null,"roomId":"2","mikeOrder":8,"mikeLock":1,"mikeOpen":1,"createTime":"2020-08-12 16:06:28","active":1}]
     * roomBgPicture : null
     * personCount : 3
     * userKind : 1
     */

    private String id;
    private String roomTypeId;
    private String roomName;
    private String createUserId;
    private String roomOwnerId;
    private int roomLock;
    private String roomPassword;
    private int roomSpecial;
    private int roomPublic;
    private int roomExclusion;
    private int roomStatus;
    private String roomNoticeTitle;
    private String roomNoticeComment;
    private String roomNumber;
    private String createTime;
    private Object endTime;
    private int active;
    private String typeName;
    private String roomBgPicture;
    private int personCount;
    private int userKind;
    private List<MikesBean> mikes;
    private List<MikeSetsBean> mikeSets;
    private int giftValue;
    private String roomTypeColor;
    private String coverUrl;
    private int roomOwnerNumber;
    private int isBeautiful;

    public int getIsBeautiful() {
        return isBeautiful;
    }

    public void setIsBeautiful(int isBeautiful) {
        this.isBeautiful = isBeautiful;
    }

    public int getRoomOwnerNumber() {
        return roomOwnerNumber;
    }

    public void setRoomOwnerNumber(int roomOwnerNumber) {
        this.roomOwnerNumber = roomOwnerNumber;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getRoomTypeColor() {
        return roomTypeColor;
    }

    public void setRoomTypeColor(String roomTypeColor) {
        this.roomTypeColor = roomTypeColor;
    }

    public int getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(int giftValue) {
        this.giftValue = giftValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getRoomOwnerId() {
        return roomOwnerId;
    }

    public void setRoomOwnerId(String roomOwnerId) {
        this.roomOwnerId = roomOwnerId;
    }

    public int getRoomLock() {
        return roomLock;
    }

    public void setRoomLock(int roomLock) {
        this.roomLock = roomLock;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public int getRoomSpecial() {
        return roomSpecial;
    }

    public void setRoomSpecial(int roomSpecial) {
        this.roomSpecial = roomSpecial;
    }

    public int getRoomPublic() {
        return roomPublic;
    }

    public void setRoomPublic(int roomPublic) {
        this.roomPublic = roomPublic;
    }

    public int getRoomExclusion() {
        return roomExclusion;
    }

    public void setRoomExclusion(int roomExclusion) {
        this.roomExclusion = roomExclusion;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomNoticeTitle() {
        return roomNoticeTitle;
    }

    public void setRoomNoticeTitle(String roomNoticeTitle) {
        this.roomNoticeTitle = roomNoticeTitle;
    }

    public String getRoomNoticeComment() {
        return roomNoticeComment;
    }

    public void setRoomNoticeComment(String roomNoticeComment) {
        this.roomNoticeComment = roomNoticeComment;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRoomBgPicture() {
        return roomBgPicture;
    }

    public void setRoomBgPicture(String roomBgPicture) {
        this.roomBgPicture = roomBgPicture;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getUserKind() {
        return userKind;
    }

    public void setUserKind(int userKind) {
        this.userKind = userKind;
    }

    public List<MikesBean> getMikes() {
        return mikes;
    }

    public void setMikes(List<MikesBean> mikes) {
        this.mikes = mikes;
    }

    public List<MikeSetsBean> getMikeSets() {
        return mikeSets;
    }

    public void setMikeSets(List<MikeSetsBean> mikeSets) {
        this.mikeSets = mikeSets;
    }
}
