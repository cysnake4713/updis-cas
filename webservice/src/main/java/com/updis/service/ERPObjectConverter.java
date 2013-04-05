package com.updis.service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ERPObjectConverter<T extends ConvertableObject> {
    T convert(Map<String, Object> params, String serverPath, String contextPath);
    List<T> convertList(List<Map<String, Object>> params, String serverPath, String contextPath);
}
