package com.updis.erpclient.connector;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:27 PM
 *
 * @author: shrek.zhou
 */
public interface Connector {
    Object send(String serviceName, Object... params) throws Exception;
    Object send(String serviceName, List params) throws Exception;
}
