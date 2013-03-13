package com.updis.erpclient.config;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/13/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ERPConfig {
    private String db;
    private Integer uid;
    private String password;
    private String modelName;

    public ERPConfig(String db, Integer uid, String password, String model) {
        this.db = db;
        this.uid = uid;
        this.password = password;
        this.modelName = model;
    }

    public String getDb() {
        return db;
    }

    public Integer getUid() {
        return uid;
    }

    public String getModelName() {
        return modelName;
    }

    public String getPassword() {
        return password;
    }
}
