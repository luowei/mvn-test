package com.rootls.base.view.command;

import com.rootls.base.view.groups.BatchDeleteGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @className:BaseDTO
 * @classDescription:
 * @author:Administrator
 * @createTime:12-4-19
 */
public class BaseCommand {
    private Integer id;
    private String page = "1";
    private String searchKey1;
    private String searchKey2;
    private String orders;
    private Integer[] ids;
    private Date startTime;
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSearchKey1() {
        return searchKey1;
    }

    public void setSearchKey1(String searchKey1) {
        this.searchKey1 = searchKey1;
    }

    public String getSearchKey2() {
        return searchKey2;
    }

    public void setSearchKey2(String searchKey2) {
        this.searchKey2 = searchKey2;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    @NotNull(groups = {BatchDeleteGroup.class})
    @Size(min = 1, groups = {BatchDeleteGroup.class})
    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

