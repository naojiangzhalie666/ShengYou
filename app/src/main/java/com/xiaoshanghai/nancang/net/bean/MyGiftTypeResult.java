package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;
import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

public class MyGiftTypeResult extends JSectionEntity implements SectionEntity, Serializable {

    public MyGiftTypeResult(boolean isHeader, String typeName, int giftTotal, GiftsResult gift) {
        this.isHeader = isHeader;
        this.typeName = typeName;
        this.giftTotal = giftTotal;
        this.gift = gift;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    public boolean isHeader;

    public String typeName;

    public int giftTotal;

    public GiftsResult gift;


}
