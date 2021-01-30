package com.xiaoshanghai.nancang.net.bean;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.io.Serializable;

public class IncomeHeaderBean extends JSectionEntity implements Serializable {

    public IncomeHeaderBean(boolean isHeader, String date, IncomeResult result) {
        this.isHeader = isHeader;
        this.date = date;
        this.result = result;
    }

    public boolean isHeader;

    public String date;

    public IncomeResult result;

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
