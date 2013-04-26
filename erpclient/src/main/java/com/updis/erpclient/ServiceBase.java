package com.updis.erpclient;

import com.updis.erpclient.config.ServiceConfig;
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
    @Autowired
    private ServiceConfig serviceConfig;
    private Connector connector;

    public Connector getConnector() throws MalformedURLException {
        if (connector == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(serviceConfig.getUrlAddress());
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
