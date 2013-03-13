package com.updis.erpclient;

import com.updis.erpclient.criteria.SearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 11:43 AM
 *
 * @author: shrek.zhou
 */
public interface SearchDomainService {
    Object[] getDomains(List<SearchCriteria> criterias);
}
