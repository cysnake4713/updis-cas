package com.updis.erpclient.criteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 4:41 PM
 *
 * @author: shrek.zhou
 */
public interface CriteriaService {
    Object[] toDomains(List<Criteria> criteriaList);
}
