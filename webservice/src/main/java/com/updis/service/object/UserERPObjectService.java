package com.updis.service.object;

import com.updis.entity.User;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:40 PM
 */
@Service("userService")
public class UserERPObjectService extends AbstractERPObjectService<User> {
    private Logger logger = LoggerFactory.getLogger(UserERPObjectService.class);

    private static final String MODEL_NAME = "hr.employee";
    @Autowired
    private ERPObjectConvertService userConverter;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    @Override
    public ERPObjectConvertService getObjectConverter() {
        return userConverter;
    }
}