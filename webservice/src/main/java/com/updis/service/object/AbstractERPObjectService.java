package com.updis.service.object;

import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ConvertibleERPObject;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:36 PM
 */
public abstract class AbstractERPObjectService<T extends ConvertibleERPObject> implements ERPObjectService<T> {
    private Logger logger = LoggerFactory.getLogger(AbstractERPObjectService.class);
    @Autowired
    protected ObjectService objectService;
    @Autowired
    protected ERPConfig erpConfig;

    protected abstract String getModelName();

    protected abstract ERPObjectConvertService getObjectConverter();

    @Override
    public T getById(Integer id, String serverPath, String contextPath, String... fields) {
        List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
        Criteria criteria = new Criteria("id", "=", id);
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(criteria);
        try {
            if (fields != null) {
                messages = objectService.searchRead(
                        getERPConfig(),
                        criterias, 0, 1, null, null, false, fields);
            } else {
                messages = objectService.searchRead(
                        getERPConfig(),
                        criterias, 0, 1, null, null, false);
            }
            List<T> list = getObjectConverter().convertList(messages, serverPath, contextPath);
            return list.get(0);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        throw new IllegalArgumentException("Can not find object with given id");
    }

    @Override
    public List<T> find(List<Criteria> criterias, String serverPath, String contextPath, String... fields) {
        List<T> list = new ArrayList<T>();
        try {
            list = getObjectConverter().convertList(objectService.searchRead(getERPConfig(), criterias, fields), serverPath, contextPath);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<T> find(List<Criteria> criterias, String order, Integer offset, Integer limit, Map context, boolean count, String serverPath, String contextPath, String... fields) {
        List<T> list = new ArrayList<T>();
        try {
            list = getObjectConverter().convertList(objectService.searchRead(getERPConfig(), criterias, offset, limit, order, context, count, fields), serverPath, contextPath);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public int count(List<Criteria> criterias, Map context) {
        try {
            List<Integer> integers = objectService.search(getERPConfig(), criterias, 0, null, null, null, false);
            return integers.size();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    private ERPConfig getERPConfig() {
        erpConfig.setModelName(getModelName());
        return erpConfig;
    }
}
