package com.xiaoshanghai.nancang.net.bean;

import com.google.gson.annotations.SerializedName;

public class BuyGoldResult extends BaseResult {


    /**
     * package : Sign=WXPay
     * appid : wxf6cc75600ec34445
     * sign : C63F7653760BA08F03FF369F23F1805D5551AA97B36AE9F487CDB52163C656F7
     * partnerid : 1586393201
     * prepayid : wx07175944781353471f71e8b6ff17720000
     * noncestr : VocqvSCZj5o7cN75U4kqXWpPqytZFLfF
     * timestamp : 1596794384
     */

    private String orderString;
    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getOrderString() {
        return orderString;
    }

    public void setOrderString(String orderString) {
        this.orderString = orderString;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
