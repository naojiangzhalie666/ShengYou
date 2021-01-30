package com.xiaoshanghai.nancang.net.bean;

public class HomeRoomResult<T> extends BaseResult {


    /**
     * current : 1
     * pages : 1
     * total : 6
     * size : 10
     * begin : 0
     * records : []
     */

    private int current;
    private int pages;
    private int total;
    private int size;
    private int begin;
    private T records;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public T getRecords() {
        return records;
    }

    public void setRecords(T records) {
        this.records = records;
    }
}
