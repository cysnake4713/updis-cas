package com.updis.erpclient;

import java.net.MalformedURLException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:45 PM
 *
 * @author: shrek.zhou
 */
public class CommonServiceImpl implements CommonService {
    private static String SERVER_URL = "http://localhost:8069/xmlrpc/common";
    private Connector connector;

    public CommonServiceImpl() throws MalformedURLException {
        this.connector = new XMLRPCConnector(SERVER_URL);
    }

    @Override
    public boolean login(String db, String login, String password) throws Exception {
        return (Boolean) this.connector.send("login", db, login, password);
    }

    @Override
    public Map version() throws Exception {
        return (Map) this.connector.send("version");
    }

    @Override
    public boolean authenticate(String db, String login, String password, Map userAgentEnv) throws Exception {
        return (Boolean) this.connector.send("authenticate", db, login, password, userAgentEnv);
    }
}
