package com.updis.erpclient;

import com.updis.erpclient.connector.Connector;
import com.updis.erpclient.connector.XMLRPCConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 9:24 AM
 *
 * @author: shrek.zhou
 */
abstract class ServiceBase {
    //    private static String SERVER_URL = "http://localhost:8069/xmlrpc/";
    private static String SERVER_URL_PROPERTY = "updis.erp.server.url";
    private Connector connector;
    @Autowired
    private Environment environment;

    public Connector getConnector() throws MalformedURLException {
        if (connector == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(environment.getProperty(SERVER_URL_PROPERTY));
            stringBuffer.append(getServiceName());
            connector = new XMLRPCConnector(stringBuffer.toString());
        }
        return connector;
    }

    /**
     * Subclass must give the server url to implements this.
     *
     * @return
     */
    abstract String getServiceName();
}
