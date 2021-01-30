package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class ChiliIncomeHeaderResult extends BaseResult {

    private String date;
    private List<ChiliIncomeResult> result;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ChiliIncomeResult> getResult() {
        return result;
    }

    public void setResult(List<ChiliIncomeResult> result) {
        this.result = result;
    }
}
