package com.updis.erpclient;

import com.updis.erpclient.criteria.Criteria;
import com.updis.erpclient.criteria.CriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 9:21 AM
 *
 * @author: shrek.zhou
 */
@Component
public class ObjectServiceImpl extends ServiceBase implements ObjectService {
    private static String SERVICE_NAME = "object";
    @Autowired
    private CriteriaService criteriaService;

    @Override
    String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public List<Map<String, Object>> read(String db, Integer uid, String password, String modelName, List<Integer> ids) throws Exception {
        return this.read(db, uid, password, modelName, ids, null);
    }

    @Override
    public List<Map<String, Object>> searchRead(String db, Integer uid, String password, String modelName, List<Criteria> domain, List<String> fields) throws Exception {
        return this.read(db, uid, password, modelName, this.search(db, uid, password, modelName, domain), fields);
    }

    @Override
    public List<Map<String, Object>> searchRead(String db, Integer uid, String password, String modelName, List<Criteria> domain) throws Exception {
        return this.read(db, uid, password, modelName, this.search(db, uid, password, modelName, domain));
    }

    @Override
    public List<Integer> search(String db, Integer uid, String password, String modelName, List<Criteria> domain) throws Exception {
        Object[] ids = (Object[]) this.execute(db, uid, password, modelName, "search", criteriaService.toDomains(domain));
        List<Integer> ret = new ArrayList<Integer>(ids.length);
        for (Object id : ids) {
            ret.add((Integer) id);
        }
        return ret;
    }

    @Override
    public Integer create(String db, Integer uid, String password, String modelName, Map<String, Object> vals) throws Exception {
        return (Integer) this.getConnector().send("execute", db, uid, password, modelName, "create", vals);
    }

    @Override
    public List<Map<String, Object>> read(String db, Integer uid, String password, String modelName, List<Integer> ids, List<String> fields) throws Exception {
        Object[] results = (Object[]) this.getConnector().send("execute", db, uid, password, modelName, "read", ids, fields);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(results.length);
        for (Object obj : results) {
            ret.add((Map<String, Object>) obj);
        }
        return ret;
    }


    private Object execute(String db, Integer uid, String password, String modelName, String method, Object[] params) throws Exception {
        return this.getConnector().send("execute", db, uid, password, modelName, method, params);
    }

    private Object exec_workflow(String signal, Object[] params) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
