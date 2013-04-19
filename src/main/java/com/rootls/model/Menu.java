package com.rootls.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:22
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Menu {
    private int id;

    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    private Timestamp createTime;

    @javax.persistence.Column(name = "create_time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
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

    private int parentId;

    @javax.persistence.Column(name = "parent_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        if (parentId != menu.parentId) return false;
        if (createTime != null ? !createTime.equals(menu.createTime) : menu.createTime != null) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (sequence != null ? !sequence.equals(menu.sequence) : menu.sequence != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        result = 31 * result + parentId;
        return result;
    }
}
