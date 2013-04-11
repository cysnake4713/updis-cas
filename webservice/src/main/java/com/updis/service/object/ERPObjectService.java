package com.updis.service.object;

import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ConvertibleERPObject;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ERPObjectService<T extends ConvertibleERPObject> {
    T getById(Integer id, String serverPath, String contextPath, String... fields);

    List<T> find(List<Criteria> criterias, String serverPath, String contextPath, String... fields);

    List<T> find(List<Criteria> criterias, String order, Integer offset, Integer limit, Map context, boolean count, String serverPath, String contextPath, String... fields);

    int count(List<Criteria> criterias, Map context);

}

