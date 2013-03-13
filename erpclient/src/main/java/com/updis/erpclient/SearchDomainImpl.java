package com.updis.erpclient;

import com.updis.erpclient.criteria.SearchCriteria;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 11:37 AM
 *
 * @author: shrek.zhou
 */
class SearchDomainImpl implements SearchDomain {

    @Override
    public Object[] getDomains(List<SearchCriteria> criterias) {
        Object[] ret = new Object[criterias.size()];
        int index = 0;
        for (SearchCriteria searchCriteria : criterias) {
            ret[index++] = new Object[]{searchCriteria.getField(), searchCriteria.getOperator(), searchCriteria.getValue()};
        }
        return ret;
    }
}
