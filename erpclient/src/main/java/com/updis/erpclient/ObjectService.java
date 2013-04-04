package com.updis.erpclient;

import com.updis.erpclient.config.ERPConfig;
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
     * def search(self, cr, user, args, offset=0, limit=None, order=None, context=None, count=False):
     *
     * @param domain
     * @return
     * @throws Exception
     */
    List<Integer> search(ERPConfig config, List<Criteria> domain, Integer offset, Integer limit, String order, Map context, boolean count) throws Exception;

    List<Integer> search(ERPConfig config, List<Criteria> domain) throws Exception;

    /**
     * Read specified fields for given resources.
     *
     * @param ids
     * @param fields
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> read(ERPConfig config, List<Integer> ids, String... fields) throws Exception;

    /**
     * Read all fields for given resources.
     *
     * @param ids
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> read(ERPConfig config, List<Integer> ids) throws Exception;

    /**
     * Search and read the given fields from objects.
     *
     * @param domain
     * @param fields
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain, String... fields) throws Exception;

    /**
     * Search and read all fields from objects.
     *
     * @param domain
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain) throws Exception;

    List<Map<String, Object>> searchRead(ERPConfig config, List<Criteria> domain, Integer offset, Integer limit, String order, Map context, boolean count, String... fields) throws Exception;

    /**
     * Create resource for a object.
     *
     * @param vals
     * @return
     * @throws Exception
     */
    Integer create(ERPConfig config, Map<String, Object> vals) throws Exception;
}
