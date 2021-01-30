package com.xiaoshanghai.nancang.net.bean;

public class MikeSetsBean extends BaseResult {
    /**
     * id : null
     * roomId : 2
     * mikeOrder : 0
     * mikeLock : 1
     * mikeOpen : 1
     * createTime : 2020-08-12 16:06:28
     * active : 1
     */

    private Object id;
    private String roomId;
    private int mikeOrder;
    private int mikeLock;
    private int mikeOpen;
    private String createTime;
    private int active;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getMikeOrder() {
        return mikeOrder;
    }

    public void setMikeOrder(int mikeOrder) {
        this.mikeOrder = mikeOrder;
    }

    public int getMikeLock() {
        return mikeLock;
    }

    public void setMikeLock(int mikeLock) {
        this.mikeLock = mikeLock;
    }

    public int getMikeOpen() {
        return mikeOpen;
    }

    public void setMikeOpen(int mikeOpen) {
        this.mikeOpen = mikeOpen;
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