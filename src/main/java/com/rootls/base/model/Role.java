package com.rootls.base.model;

import javax.persistence.*;
import java.util.Date;
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
@Table(name = "role")
//@AttributeOverride( name="id", column = @Column(name="id") )
public class Role extends IdEntity {

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @javax.persistence.Column(name = "role_name", nullable = false, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Column(name = "role_description", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<User>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany
    @JoinTable(name = "roles_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<Permission>();

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
