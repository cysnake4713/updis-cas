package com.updis.erpclient;

import com.updis.erpclient.criteria.SearchCriteria;

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
public class ObjectServiceImpl extends ServiceBase implements ObjectService {
    private static String SERVICE_NAME = "object";
    private String modelName;
    private String dbName;
    private String password;
    private Integer uid;

    /**
     * Object service to use to operate on the OpenERP models.
     *
     * @param modelName
     * @param dbName
     * @param password
     * @param uid
     */
    public ObjectServiceImpl(String modelName, String dbName, String password, Integer uid) {
        this.modelName = modelName;
        this.dbName = dbName;
        this.password = password;
        this.uid = uid;
    }

    @Override
    String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public List<Map<String, Object>> read(List<Integer> ids) throws Exception {
        return this.read(ids, null);
    }

    @Override
    public List<Map<String, Object>> searchRead(List<SearchCriteria> domain, List<String> fields) throws Exception {
        return this.read(this.search(domain), fields);
    }

    @Override
    public List<Map<String, Object>> searchRead(List<SearchCriteria> domain) throws Exception {
        return this.read(this.search(domain));
    }

    @Override
    public List<Integer> search(List<SearchCriteria> domain) throws Exception {
        SearchDomain searchDomain = new SearchDomainImpl();
        Object[] ids = (Object[]) this.execute("search", searchDomain.getDomains(domain));
        List<Integer> ret = new ArrayList<Integer>(ids.length);
        for (Object id : ids) {
            ret.add((Integer) id);
        }
        return ret;
    }

    @Override
    public Integer create(Map<String, Object> vals) throws Exception {
        return (Integer) this.getConnector().send("execute", getDbName(), getUid(), getPassword(), getModelName(), "create", vals);
    }

    @Override
    public List<Map<String, Object>> read(List<Integer> ids, List<String> fields) throws Exception {
        Object[] results = (Object[]) this.getConnector().send("execute", getDbName(), getUid(), getPassword(), getModelName(), "read", ids, fields);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(results.length);
        for (Object obj : results) {
            ret.add((Map<String, Object>) obj);
        }
        return ret;
    }

    public String getDbName() {
        return dbName;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUid() {
        return uid;
    }

    public String getModelName() {
        return modelName;
    }

    private Object execute(String method, Object[] params) throws Exception {
        return this.getConnector().send("execute", getDbName(), getUid(), getPassword(), getModelName(), method, params);
    }

    private Object exec_workflow(String signal, Object[] params) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
