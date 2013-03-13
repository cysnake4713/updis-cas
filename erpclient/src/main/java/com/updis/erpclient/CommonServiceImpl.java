package com.updis.erpclient;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:45 PM
 *
 * @author: shrek.zhou
 */
public class CommonServiceImpl extends ServiceBase implements CommonService {
    private static String SERVICE_NAME = "common";

    @Override
    public Integer login(String db, String login, String password) throws Exception {
        return (Integer) this.getConnector().send("login", db, login, password);
    }

    @Override
    public Map version() throws Exception {
        return (Map) this.getConnector().send("version");
    }

    @Override
    public Integer authenticate(String db, String login, String password, Map userAgentEnv) throws Exception {
        return (Integer) this.getConnector().send("authenticate", db, login, password, userAgentEnv);
    }

    @Override
    public String about() throws Exception {
        return (String) this.getConnector().send("about");
    }

    @Override
    String getServiceName() {
        return SERVICE_NAME;
    }

}
