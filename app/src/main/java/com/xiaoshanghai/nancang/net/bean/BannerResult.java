package com.xiaoshanghai.nancang.net.bean;

public class BannerResult extends BaseResult {

    /**
     * id : 1
     * pictureUrl : http://39.101.164.232/imgs/banner/hfasupfoa;
     * linkUrl : http://39.101.164.232/imgs/banner/1q2jrofijashn
     * bannerOrder : 1
     * createTime : 2020-06-24 12:18:35
     * active : 1
     */

    private String id;
    private String pictureUrl;
    private String linkUrl;
    private int bannerOrder;
    private String createTime;
    private int active;
    private String bannerName;

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public int getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(int bannerOrder) {
        this.bannerOrder = bannerOrder;
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
