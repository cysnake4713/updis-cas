package com.updis.erpclient;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:37 PM
 *
 * @author: shrek.zhou
 */
public interface CommonService extends Service {
    boolean login(String db, String login, String password) throws Exception;

    Map version() throws Exception;

    boolean authenticate(String db, String login, String password, Map userAgentEnv) throws Exception;
}
