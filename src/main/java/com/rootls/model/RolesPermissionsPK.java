package com.rootls.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:22
 * To change this template use File | Settings | File Templates.
 */
public class RolesPermissionsPK implements Serializable {
    private long roleId;

    @Id
    @Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    private long permissionsId;

    @Id
    @Column(name = "permissions_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    public long getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(long permissionsId) {
        this.permissionsId = permissionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesPermissionsPK that = (RolesPermissionsPK) o;

        if (permissionsId != that.permissionsId) return false;
        if (roleId != that.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (int) (permissionsId ^ (permissionsId >>> 32));
        return result;
    }
}
