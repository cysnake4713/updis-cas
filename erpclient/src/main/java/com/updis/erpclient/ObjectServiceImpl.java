package com.updis.erpclient;

import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.erpclient.criteria.CriteriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Logger logger = LoggerFactory.getLogger(ObjectServiceImpl.class);

    @Autowired
    private CriteriaService criteriaService;

    @Override
    String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    public List<Integer> search(ERPConfig config, List<Criteria> domain, Integer offset, Integer limit, String order, Map context, boolean count) throws Exception {
        Object[] ids = (Object[]) this.execute(config, "search", criteriaService.toDomains(domain), offset, limit, order, context, count);
        List<Integer> ret = new ArrayList<Integer>(ids.length);
        for (Object id : ids) {
            ret.add((Integer) id);
        }
        return ret;
    }

    @Override
    public List<Integer> search(ERPConfig config, List<Criteria> domain) throws Exception {
        return this.search(config, domain, 0, 20, null, null, false);
    }

    @Override
    public List<Map<String, Object>> read(ERPConfig config, List<Integer> ids, String... fields) throws Exception {
        Object[] results = (Object[])execute(config, "read", ids, fields);
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>(results.length);
        for (Object obj : results) {
            ret.add((Map<String, Object>) obj);
        }
        return ret;
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
    public List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain, Integer offset, Integer limit, String order, Map context, boolean count, String... fields) throws Exception {
        return this.read(config, this.search(config, domain, offset, limit, order, context, count), fields);
    }

    @Override
    public Boolean update(ERPConfig config, List<Integer> ids, Map<String, Object> vals) throws Exception {
        return (Boolean)execute(config, "write", ids, vals);
    }

    @Override
    public Integer create(ERPConfig config, Map<String, Object> vals) throws Exception {
        return (Integer)execute(config, "create", vals);
    }

    private Object execute(ERPConfig config, String method, Object... params) throws Exception {
        List<Object> p = new ArrayList<Object>();
        p.add(config.getDb());
        p.add(config.getUid());
        p.add(config.getPassword());
        p.add(config.getModelName());
        p.add(method);
        p.addAll(Arrays.asList(params));

        try {
            StringBuffer paramsSB = new StringBuffer();
            for(Object param : params) {
                if (param instanceof Object[]) {
                    Object[] arrayParam = (Object[])param;
                    for (Object o : arrayParam) {
                        if (o != null && (o instanceof Object[])) { // criterias
                            Object[] criteriaTuple = (Object[])o;
                            paramsSB.append("['").append(criteriaTuple[0].toString()).append("', '")
                                    .append(criteriaTuple[1].toString()).append("', '")
                                    .append(criteriaTuple[2].toString()).append("'] ");
                        } else {
                            paramsSB.append(o == null ? "null" : o.toString() + " ");
                        }
                    }
                } else if (param != null && (param instanceof List)) { // List<Integer>
                    paramsSB.append("/*ids for short*/ ");
                } else {
                    paramsSB.append(param == null ? "null " : param.toString() + " ");
                }
            }
            logger.debug("ERP xmlrpc params:" + config.getDb() + " " + config.getUid() + " " + config.getPassword() + " " + config.getModelName() + " "
                    + method + " " + paramsSB.toString());
        }catch (Exception e) {
            // ignore exceptions.
            logger.warn(e.getMessage(), e);
        }

        return this.getConnector().send("execute", p);
    }

    private Object exec_workflow(String signal, Object[] params) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
