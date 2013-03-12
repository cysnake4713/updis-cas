package com.updis.erpclient;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:28 PM
 *
 * @author: shrek.zhou
 */
public class XMLRPCClient {


    public static void main(String[] args) {
        try {
            CommonService service = new CommonServiceImpl();
            Map version = service.version();
            System.out.println(version);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
