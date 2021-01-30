package com.xiaoshanghai.nancang.net.bean;

public class CheckVersionBean {
    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getAndroidQrUrl() {
        return androidQrUrl;
    }

    public void setAndroidQrUrl(String androidQrUrl) {
        this.androidQrUrl = androidQrUrl;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getIosQrUrl() {
        return iosQrUrl;
    }

    public void setIosQrUrl(String iosQrUrl) {
        this.iosQrUrl = iosQrUrl;
    }

    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    private String versionNumber;
    private String androidUrl;
    private String androidQrUrl;
    private String iosUrl;
    private String iosQrUrl;
    private String versionComment;
    private String active;

}
