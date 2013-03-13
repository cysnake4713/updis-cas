package com.updis.erpclient;

import com.updis.erpclient.criteria.SearchCriteria;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
//            CommonService service = new CommonServiceImpl();
//            Map version = service.version();
//            System.out.println(version);
//            System.out.println(service.about());
//            System.out.println(service.login("updis","admin","Freeborders#1"));
//            System.out.println(service.authenticate("updis","admin","Freeborders#1",new HashMap()));

            ObjectService objectService = new ObjectServiceImpl("res.partner", "updis", "admin", 1);
//            Object[] results = (Object[]) objectService.execute("search", new Object[]{
//                    new Object[]{"name", "=", "Agrolait"}
//            }, new HashMap());
//            System.out.println(results.length);
//            System.out.println(objectService.read(Arrays.asList(new Integer[]{6})));
            List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
            searchCriterias.add(new SearchCriteria("mobile", "=", false));
            List<Integer> results = objectService.search(searchCriterias);
            List<Map<String, Object>> objects = objectService.searchRead(searchCriterias, Arrays.asList(new String[]{"name"}));
//            System.out.println(results);
            System.out.println(objects);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//        try {
//            config.setServerURL(new URL("http://113.108.103.13:8069/xmlrpc"));
//            XmlRpcClient rpcClient = new XmlRpcClient();
//            rpcClient.setConfig(config);
//            ClientFactory clientFactory = new ClientFactory(rpcClient);
//            CommonService commonService = (CommonService) clientFactory.newInstance(CommonService.class);
//            commonService.version();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


    }
}
