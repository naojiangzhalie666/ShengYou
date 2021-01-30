package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

public class ChiliIncomeHeaderBean extends JSectionEntity implements Serializable {

    public boolean isHeader;

    public String date;

    public ChiliIncomeResult result;

    public ChiliIncomeHeaderBean(boolean isHeader, String date, ChiliIncomeResult result) {
        this.isHeader = isHeader;
        this.date = date;
        this.result = result;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
