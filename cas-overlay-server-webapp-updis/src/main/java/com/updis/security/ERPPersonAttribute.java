package com.updis.security;

import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.BasePersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/18/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ERPPersonAttribute extends BasePersonAttributeDao {
    private final static Logger logger = LoggerFactory.getLogger(ERPPersonAttribute.class);
    @Autowired
    private ObjectService objectService;
    private String db;
    private Integer erpUID;
    private String password;

    @Override
    public IPersonAttributes getPerson(String uid) {
        final Map<String, List<Object>> erpUserMap = new HashMap<String, List<Object>>();
        IPersonAttributes personAttributes = new AttributeNamedPersonImpl(erpUserMap);
        ERPConfig config = new ERPConfig(db, erpUID, password, "res.users");
        List<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(new Criteria("login", "=", uid));
        try {
            List<Map<String, Object>> maps = objectService.searchRead(config, criterias, Arrays.asList(new String[]{"login","user_email","lang","employee","password"}));
            Set<Map.Entry<String, Object>> entries = maps.get(0).entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                List<Object> value = new ArrayList<Object>();
                value.add(entry.getValue());
                erpUserMap.put(entry.getKey(), value);
            }
            erpUserMap.put("db", Arrays.asList(new Object[]{db}));
            personAttributes = new AttributeNamedPersonImpl(erpUserMap);
        } catch (Exception e) {
            logger.error("Failed to fetch ERP user info", e);
        }
        return personAttributes;
    }

    @Override
    public Set<IPersonAttributes> getPeople(Map<String, Object> query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Set<String> getPossibleUserAttributeNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Set<String> getAvailableQueryAttributes() {
        return Collections.EMPTY_SET;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Integer getErpUID() {
        return erpUID;
    }

    public void setErpUID(Integer erpUID) {
        this.erpUID = erpUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
