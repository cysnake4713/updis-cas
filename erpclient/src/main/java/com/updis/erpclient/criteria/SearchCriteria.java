package com.updis.erpclient.criteria;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 11:51 AM
 *
 * @author: shrek.zhou
 */
public class SearchCriteria {
    private String field;
    private String operator;
    private Object value;

    public SearchCriteria(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

}
