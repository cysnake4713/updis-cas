package com.updis.service.converter;

import com.updis.entity.LoginUser;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 10:51
 */
@Service("loginUserConverter")
public class LoginUserERPObjectConvertService extends AbstractERPObjectConvertService<LoginUser> {
    private Map<String, String> paramMap = new HashMap<String, String>();
    {
        paramMap.put("id", "userId");
        paramMap.put("login", "username");
        paramMap.put("password_crypt", "password");
    }

    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public LoginUser createInstance() {
        return new LoginUser();
    }
}
