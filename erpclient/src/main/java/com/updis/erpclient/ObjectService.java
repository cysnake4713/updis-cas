package com.updis.erpclient;

import com.updis.erpclient.criteria.SearchCriteria;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 9:19 AM
 *
 * @author: shrek.zhou
 */
public interface ObjectService extends Service {
    List<Integer> search(List<SearchCriteria> domain) throws Exception;

    List<Map<String, Object>> read(List<Integer> ids, List<String> fields) throws Exception;

    List<Map<String, Object>> read(List<Integer> ids) throws Exception;

    List<Map<String, Object>> searchRead(List<SearchCriteria> domain, List<String> fields) throws Exception;

    List<Map<String, Object>> searchRead(List<SearchCriteria> domain) throws Exception;
}
