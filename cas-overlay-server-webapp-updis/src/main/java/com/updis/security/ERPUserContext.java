package com.updis.security;

/**
 * Created with IntelliJ IDEA.
 * User: cysnake4713
 * Date: 13-4-24
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */
public interface ERPUserContext {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
}
