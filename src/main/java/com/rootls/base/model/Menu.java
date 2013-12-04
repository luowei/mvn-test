package com.rootls.base.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "menu")
public class Menu extends IdEntity {

    private String name;

    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String url;

    @javax.persistence.Column(name = "url", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    @Basic
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Date createTime;

    @javax.persistence.Column(name = "create_time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String sequence;

    @javax.persistence.Column(name = "sequence", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    private Integer parentId;

    @javax.persistence.Column(name = "parent_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    private List<Menu> subMenus = new ArrayList<Menu>();

    @Transient
    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

}
