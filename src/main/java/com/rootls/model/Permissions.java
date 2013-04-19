package com.rootls.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:22
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Permissions {
    private long id;

    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String permission;

    @javax.persistence.Column(name = "permission", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    private String description;

    @javax.persistence.Column(name = "description", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private long permissionGroupId;

    @javax.persistence.Column(name = "permission_group_id", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public long getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(long permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permissions that = (Permissions) o;

        if (id != that.id) return false;
        if (permissionGroupId != that.permissionGroupId) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (permission != null ? !permission.equals(that.permission) : that.permission != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (permissionGroupId ^ (permissionGroupId >>> 32));
        return result;
    }
}
