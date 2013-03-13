package com.updis.erpclient;

import com.updis.erpclient.criteria.Criteria;

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
    /**
     * Search objects according to given domain.
     *
     * @param modelName
     * @param domain
     * @return
     * @throws Exception
     */
    List<Integer> search(String db, Integer uid, String password, String modelName, List<Criteria> domain) throws Exception;

    /**
     * Read specified fields for given resources.
     *
     * @param ids
     * @param fields
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> read(String db, Integer uid, String password, String modelName, List<Integer> ids, List<String> fields) throws Exception;

    /**
     * Read all fields for given resources.
     *
     * @param ids
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> read(String db, Integer uid, String password, String modelName, List<Integer> ids) throws Exception;

    /**
     * Search and read the given fields from objects.
     *
     * @param domain
     * @param fields
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchRead(String db, Integer uid, String password, String modelName, List<Criteria> domain, List<String> fields) throws Exception;

    /**
     * Search and read all fields from objects.
     *
     * @param domain
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchRead(String db, Integer uid, String password, String modelName, List<Criteria> domain) throws Exception;

    /**
     * Create resource for a object.
     *
     * @param vals
     * @return
     * @throws Exception
     */
    Integer create(String db, Integer uid, String password, String modelName, Map<String, Object> vals) throws Exception;
}
