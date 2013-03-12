package com.updis.erpclient;

import org.apache.xmlrpc.XmlRpcException;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:27 PM
 *
 * @author: shrek.zhou
 */
public interface Connector {
    Object send(String serviceName, Object ... params) throws Exception;
}
