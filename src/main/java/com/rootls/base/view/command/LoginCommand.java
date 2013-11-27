package com.rootls.base.view.command;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @className:LoginFormBean
 * @classDescription:
 * @author:Administrator
 * @createTime:12-5-15
 */
public class LoginCommand {
    private String username;
    private String password;
    private boolean rememberMe;

    @NotBlank
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}

