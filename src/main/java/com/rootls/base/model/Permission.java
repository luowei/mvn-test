package com.rootls.base.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 13-4-20
 * Time: 下午1:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "permission")
//@AttributeOverride( name="id", column = @Column(name="id") )
public class Permission extends IdEntity{

    @Transient
    private boolean checked;
    @Transient
    private boolean open;

    public Permission() {
    }

    public Permission(Integer id, String description, Integer permissionGroupId) {
        this.id = id;
        this.description = description;
        this.permissionGroupId = permissionGroupId;
    }

    public Permission(String description, Integer permissionGroupId) {
        this.description = description;
        this.permissionGroupId = permissionGroupId;
    }

    @javax.persistence.Column(name = "permission", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    private String permission;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @javax.persistence.Column(name = "description", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @javax.persistence.Column(name = "permission_group_id", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer permissionGroupId;

    public Integer getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(Integer permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> users) {
        this.roles = roles;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}
