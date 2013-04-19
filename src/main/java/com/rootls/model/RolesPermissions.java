package com.rootls.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 上午1:22
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.IdClass(com.rootls.model.RolesPermissionsPK.class)
@javax.persistence.Table(name = "roles_permissions", schema = "", catalog = "db_test")
@Entity
public class RolesPermissions {
    private long roleId;

    @javax.persistence.Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    private long permissionsId;

    @javax.persistence.Column(name = "permissions_id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
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

        RolesPermissions that = (RolesPermissions) o;

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
