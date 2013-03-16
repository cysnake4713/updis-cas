package com.updis.security.authentication.handler;

import org.jasig.cas.authentication.handler.AuthenticationException;
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
    @Value("${updis.erp.server.url}")
    private String updisERPServerUrl;

    @Override
    protected boolean authenticateUsernamePasswordInternal(UsernamePasswordCredentials credentials) throws AuthenticationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUpdisERPServerUrl() {
        return updisERPServerUrl;
    }

    public void setUpdisERPServerUrl(String updisERPServerUrl) {
        this.updisERPServerUrl = updisERPServerUrl;
    }
}
