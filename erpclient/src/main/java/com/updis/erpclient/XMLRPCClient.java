package com.updis.erpclient;

import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.erpclient.criteria.CriteriaService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/12/13
 * Time: 2:28 PM
 *
 * @author: shrek.zhou
 */
public class XMLRPCClient {


    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ctx.scan("com.updis.erpclient", "com.updis.erpclient.criteria");
        ObjectService objectService = ctx.getBean(ObjectService.class);

        ctx.getBean(CriteriaService.class);
        List<Criteria> criterias = new ArrayList<Criteria>();
        ERPConfig erpConfig = new ERPConfig("updis",1,"Freeborders#1","res.partner");
        List<Integer> integers = objectService.search(erpConfig, criterias);
        System.out.println(integers);

        CommonService commonService = ctx.getBean(CommonService.class);
        Integer uid = commonService.login("updis","admin","Freeborders#1");
        System.out.println(uid);
    }
}
