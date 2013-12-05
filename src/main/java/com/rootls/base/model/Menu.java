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
//@AttributeOverride( name="id", column = @Column(name="id") )
public class Menu extends IdEntity {

    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Column(name = "url", nullable = true, insertable = true, updatable = true, length = 200, precision = 0)
    @Basic
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @javax.persistence.Column(name = "create_time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @javax.persistence.Column(name = "sequence", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String sequence;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @javax.persistence.Column(name = "parent_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Transient
    private List<Menu> subMenus = new ArrayList<Menu>();

    public List<Menu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<Menu> subMenus) {
        this.subMenus = subMenus;
    }

}
