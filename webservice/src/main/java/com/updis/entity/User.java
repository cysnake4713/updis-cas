package com.updis.entity;

import com.updis.service.converter.ConvertibleERPObject;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 10:46
 */
public class User implements ConvertibleERPObject {
    private Integer userId;
    private String username;
    private String password;
    private String mobileNumber;
    private Object[] devices;

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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Object[] getDevices() {
        return devices;
    }

    public void setDevices(Object[] devices) {
        this.devices = devices;
    }
}
