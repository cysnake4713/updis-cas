package com.updis.erpclient;

import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.connector.Connector;
import com.updis.erpclient.connector.XMLRPCConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 9:24 AM
 *
 * @author: shrek.zhou
 */
abstract class ServiceBase {
    @Autowired
    private ERPConfig erpConfig;
    private Connector connector;

    public Connector getConnector() throws MalformedURLException {
        if (connector == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(erpConfig.getUrlAddress());
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
