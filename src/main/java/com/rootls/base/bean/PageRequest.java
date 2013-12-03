package com.rootls.base.bean;

import java.io.Serializable;

/**
 * @className:PageRequest
 * @classDescription:
 * @author:luowei
 * @createTime:12-4-12
 */
public class PageRequest implements Serializable {
    private int page = 1;
    private int size;
    private String orders;  //排序字段,多个字段用','分隔,格式形如, orders=username:asc,birthday:desc


    public PageRequest(int page, int size) {
        this.page = page < 0 ? 1 : page;
        this.size = size <= 0 ? 10 : size;
    }

    public PageRequest(int page, int size, String orders) {
        this(page, size);
        this.orders = orders;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public int getOffset() {

        return (page - 1) * size;
    }


}

