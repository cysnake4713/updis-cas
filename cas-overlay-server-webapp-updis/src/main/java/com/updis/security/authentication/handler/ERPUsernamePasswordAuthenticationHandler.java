package com.updis.security.authentication.handler;

import com.updis.erpclient.CommonService;
import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.security.ERPUserContext;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.BadCredentialsAuthenticationException;
import org.jasig.cas.authentication.handler.UnknownUsernameAuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/16/13
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ERPUsernamePasswordAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
    private String erpdb;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ERPUserContext erpUserContext;


    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {
        try {
            Integer uid = commonService.login(erpdb, credentials.getUsername(), credentials.getPassword());
            erpUserContext.setPassword(credentials.getPassword());
            erpUserContext.setUsername(credentials.getUsername());
            return true;
        } catch (Exception e) {
            throw new BadCredentialsAuthenticationException();
        }
    }

    public String getErpdb() {
        return erpdb;
    }

    public void setErpdb(String erpdb) {
        this.erpdb = erpdb;
    }
}
