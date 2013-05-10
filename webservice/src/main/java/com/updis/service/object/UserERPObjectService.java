package com.updis.service.object;

import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 11:12
 */
@Service("userService")
public class UserERPObjectService extends AbstractERPObjectService<User> {
    private Logger logger = LoggerFactory.getLogger(UserERPObjectService.class);

    private static final String MODEL_NAME = "res.users";
    @Autowired
    private ERPObjectConvertService userConverter;

    @Override
    protected String getModelName() {
        return MODEL_NAME;
    }

    @Override
    protected ERPObjectConvertService getObjectConverter() {
        return userConverter;
    }

    public User findUser(String username, String password) {
        try {
            List<Criteria> criterias = new ArrayList<Criteria>();
            criterias.add(new Criteria("login", "=", username));
            criterias.add(new Criteria("password_crypt", "=", password));

            List<User> users = find(criterias, null, null, (String)null);
            if (users != null & users.size() > 0) {
                return users.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
