package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

public class GoldGiftHeaderResult extends JSectionEntity implements Serializable {

    public boolean isHeader;

    public String date;

    public GoldGiftResult result;

    public GoldGiftHeaderResult(boolean isHeader, String date, GoldGiftResult result) {
        this.isHeader = isHeader;
        this.date = date;
        this.result = result;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
