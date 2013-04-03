package com.updis.erpclient;

import com.updis.erpclient.config.ERPConfig;
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
    public List<Map<String, Object>> read(ERPConfig config, List<Integer> ids) throws Exception {
        return this.read(config, ids, null);
    }

    @Override
    public List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain, String... fields) throws Exception {
        return this.read(config, this.search(config, domain), fields);
    }

    @Override
    public List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain) throws Exception {
        return this.read(config, this.search(config, domain));
    }

    @Override
    public List<Integer> search(ERPConfig config, List<Criteria> domain, Integer offset, Integer limit, String order, Map context,boolean count) throws Exception {
        Object[] ids = (Object[]) this.execute(config, "search", criteriaService.toDomains(domain));
        List<Integer> ret = new ArrayList<Integer>(ids.length);
        for (Object id : ids) {
            ret.add((Integer) id);
        }
        return ret;
    }

    public List<Integer> search(ERPConfig config, List<Criteria> domain) throws Exception {
        return this.search(config, domain,0,20,null,null,false);
    }

    @Override
    public Integer create(ERPConfig config, Map<String, Object> vals) throws Exception {
        return (Integer) this.getConnector().send("execute", config.getDb(), config.getUid(), config.getPassword(), config.getModelName(), "create", vals);
    }

    @Override
    public List<Map<String, Object>> read(ERPConfig config, List<Integer> ids, String... fields) throws Exception {
        Object[] results = (Object[]) this.getConnector().send("execute", config.getDb(), config.getUid(), config.getPassword(), config.getModelName(), "read", ids, fields);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(results.length);
        for (Object obj : results) {
            ret.add((Map<String, Object>) obj);
        }
        return ret;
    }


    private Object execute(ERPConfig config, String method, Object[] params) throws Exception {
        return this.getConnector().send("execute", config.getDb(), config.getUid(), config.getPassword(), config.getModelName(), method, params);
    }

    private Object exec_workflow(String signal, Object[] params) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
