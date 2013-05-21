package com.updis.service.converter;

import com.updis.entity.User;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 10:51
 */
@Service("userConverter")
public class UserERPObjectConvertService extends AbstractERPObjectConvertService<User> {
    private Map<String, String> paramMap = new HashMap<String, String>();
    {
        paramMap.put("id", "userId");
        paramMap.put("login", "username");
        paramMap.put("password_crypt", "password");
        paramMap.put("mobile_phone", "mobileNumber");
    }

    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public User createInstance() {
        return new User();
    }
}
