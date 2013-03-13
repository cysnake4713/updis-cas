package com.updis.erpclient.criteria;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 5:06 PM
 *
 * @author: shrek.zhou
 */
public class Criteria {
    private String field;
    private String operator;
    private Object value;

    public Criteria(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
