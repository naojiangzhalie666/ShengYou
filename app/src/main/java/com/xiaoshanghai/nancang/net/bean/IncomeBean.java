package com.xiaoshanghai.nancang.net.bean;

import java.util.List;

public class IncomeBean extends BaseResult{

    private String date;

    private List<IncomeResult> incomeResults;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<IncomeResult> getIncomeResults() {
        return incomeResults;
    }

    public void setIncomeResults(List<IncomeResult> incomeResults) {
        this.incomeResults = incomeResults;
    }
}
