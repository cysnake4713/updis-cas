package com.updis.erpclient.criteria;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 11:51 AM
 *
 * @author: shrek.zhou
 */
@Component
public class CriteriaServiceImpl implements CriteriaService {

    @Override
    public Object[] toDomains(List<Criteria> criteriaList) {
        Object[] ret = new Object[criteriaList.size()];
        int index = 0;
        for (Criteria criteria : criteriaList) {
            ret[index++] = new Object[]{criteria.getField(), criteria.getOperator(), criteria.getValue()};
        }
        return ret;
    }
}
