package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class GoldGiftHeader {

    private String date;

    private List<GoldGiftResult> result;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<GoldGiftResult> getResult() {
        return result;
    }

    public void setResult(List<GoldGiftResult> result) {
        this.result = result;
    }
}
