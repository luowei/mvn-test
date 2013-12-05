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
@Table(name = "user")
//@AttributeOverride( name="id", column = @Column(name="id") )
public class User extends IdEntity{

    public User() {
    }

    public User(Integer id,String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @javax.persistence.Column(name = "username", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Column(name = "password", nullable = false, insertable = true, updatable = true, length = 92, precision = 0)
    @Basic
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @javax.persistence.Column(name = "email", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}






















