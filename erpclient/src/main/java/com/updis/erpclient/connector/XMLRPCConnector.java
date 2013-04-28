package com.updis.erpclient.connector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:48 PM
 *
 * @author: shrek.zhou
 */
public class XMLRPCConnector implements Connector {
    private XmlRpcClient client;

    public XMLRPCConnector(String serverUrl) throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(serverUrl));
        config.setEnabledForExceptions(true);
        config.setEnabledForExtensions(true);
        client = new XmlRpcClient();
        client.setConfig(config);
    }

    @Override
    public Object send(String serviceName, Object[] params) throws XmlRpcException {
        return client.execute(serviceName, params);
    }

    @Override
    public Object send(String serviceName, List params) throws Exception {
        return client.execute(serviceName, params);
    }
}

