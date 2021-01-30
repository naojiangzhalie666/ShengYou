package com.xiaoshanghai.nancang.helper;

import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

/**
 * 自定义消息的bean实体，用来与json的相互转化
 */
public class CustomHelloMessage {
    public String businessID = TUIKitConstants.BUSINESS_ID_CUSTOM_HELLO;
    public String text = "欢迎加入云通信IM大家庭！";
    public String link = "https://cloud.tencent.com/document/product/269/3794";
    public String img = "http://192.168.0.12:8080/imgs/portrait/9ee762b5-862d-4b86-a8fd-a42a7b5fb975";

    public int version = TUIKitConstants.JSON_VERSION_UNKNOWN;
}
