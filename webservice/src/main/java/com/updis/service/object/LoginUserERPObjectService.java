package com.updis.service.object;

import com.updis.entity.LoginUser;
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
@Service("loginUserService")
public class LoginUserERPObjectService extends AbstractERPObjectService<LoginUser> {
    private Logger logger = LoggerFactory.getLogger(LoginUserERPObjectService.class);

    private static final String MODEL_NAME = "res.users";
    @Autowired
    private ERPObjectConvertService loginUserConverter;

    @Override
    protected String getModelName() {
        return MODEL_NAME;
    }

    @Override
    protected ERPObjectConvertService getObjectConverter() {
        return loginUserConverter;
    }

    public LoginUser findUser(String username, String password) {
        try {
            List<Criteria> criterias = new ArrayList<Criteria>();
            criterias.add(new Criteria("login", "=", username));
            criterias.add(new Criteria("password_crypt", "=", password));

            List<LoginUser> users = find(criterias, null, null, (String)null);
            if (users != null & users.size() > 0) {
                return users.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
