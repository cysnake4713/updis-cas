package com.updis.entity;

import com.updis.service.converter.ConvertibleERPObject;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 10:46
 */
public class LoginUser implements ConvertibleERPObject {
    private Integer userId;
    private String username;
    private String password;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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