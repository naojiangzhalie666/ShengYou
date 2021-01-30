package com.xiaoshanghai.nancang.net.bean;

public class HomeSortResult extends BaseResult {

    /**
     * id : 1
     * roomTypeName : 测试类型1
     * roomTypeColor : #FF69B4
     * typeOrder : 1
     * createTime : 2020-06-22 16:38:43
     * active : 1
     */

    private String id;
    private String roomTypeName;
    private String roomTypeColor;
    private String typeOrder;
    private String createTime;
    private int active;
    private boolean isSelect;

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

    public String getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
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
