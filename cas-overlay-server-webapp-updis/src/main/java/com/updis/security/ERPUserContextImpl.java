package com.updis.security;

/**
 * Created with IntelliJ IDEA.
 * User: cysnake4713
 * Date: 13-4-24
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public class ERPUserContextImpl implements ERPUserContext {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
