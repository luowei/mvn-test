package com.rootls.base.view.command;

/**
 * @className:UserCommand
 * @classDescription:
 * @author:luowei
 * @createTime:12-5-18
 */
public class UserCommand extends BaseCommand{
    private String username;
    private String email;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

