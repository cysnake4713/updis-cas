package com.updis.security.authentication;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/16/13
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ERPUserDetails extends UserDetails {
    Map<String, Object> getERPUserProperties();
}
