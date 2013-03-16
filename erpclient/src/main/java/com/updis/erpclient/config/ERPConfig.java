package com.updis.erpclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/13/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ERPConfig {
    @Value("${updis.erp.db}")
    private String db;
    private Integer uid;
    private String password;
    private String modelName;

    public ERPConfig() {
    }

    public ERPConfig(Integer uid, String password, String modelName) {
        this.uid = uid;
        this.password = password;
        this.modelName = modelName;
    }

    public ERPConfig(String db, Integer uid, String password, String model) {
        this.db = db;
        this.uid = uid;
        this.password = password;
        this.modelName = model;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
