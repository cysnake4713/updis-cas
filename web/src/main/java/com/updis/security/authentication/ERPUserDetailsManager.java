package com.updis.security.authentication;

import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/16/13
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ERPUserDetailsManager implements UserDetailsManager {
    private String db;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private ERPConfig erpConfig;

    @Override
    public void createUser(UserDetails user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateUser(UserDetails user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteUser(String username) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean userExists(String username) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(new Criteria("login", "=", username));
        try {
            List<Map<String, Object>> maps = objectService.searchRead(erpConfig, criterias);
//            List<Map<String, Object>> maps = objectService.searchRead(erpConfig, criterias, Arrays.asList(new String[]{"name", "login", "password"}));
            Map<String, Object> stringObjectMap = maps.get(0);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            erpConfig.setUid(Integer.valueOf((String) stringObjectMap.get("id")));
            erpConfig.setPassword((String) stringObjectMap.get("password"));
            return new ERPUser(username, grantedAuthorities, stringObjectMap, stringObjectMap.get("password").toString());
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }
}
