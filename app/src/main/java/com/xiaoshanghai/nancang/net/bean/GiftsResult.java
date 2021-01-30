package com.xiaoshanghai.nancang.net.bean;

public class GiftsResult extends BaseResult {
    /**
     * id : 2
     * giftName : 独角兽
     * giftStaticUrl : http://192.168.0.12:8080/imgs/gift/1
     * giftUrl : http://192.168.0.12:8080/imgs/gift/1
     * giftType : 1
     * giftPriceType : 2
     * giftPrice : 66
     * nobleId : 0
     * createTime : 2020-07-11 12:07:04
     * active : 1
     * getTotal : 2
     */

    private String id;
    private String giftName;
    private String giftStaticUrl;
    private String giftUrl;
    private int giftType;
    private int giftPriceType;
    private int giftPrice;
    private String nobleId;
    private String createTime;
    private int active;
    private int getTotal;
    private int giftCont;
    private int boxType;

    public int getBoxType() {
        return boxType;
    }

    public void setBoxType(int boxType) {
        this.boxType = boxType;
    }

    public int getGiftCont() {
        return giftCont;
    }

    public void setGiftCont(int giftCont) {
        this.giftCont = giftCont;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    public int getGiftPriceType() {
        return giftPriceType;
    }

    public void setGiftPriceType(int giftPriceType) {
        this.giftPriceType = giftPriceType;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getNobleId() {
        return nobleId;
    }

    public void setNobleId(String nobleId) {
        this.nobleId = nobleId;
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

    public int getGetTotal() {
        return getTotal;
    }

    public void setGetTotal(int getTotal) {
        this.getTotal = getTotal;
    }
}
