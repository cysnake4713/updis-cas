package com.updis.service.object;

import com.updis.entity.User;
import com.updis.service.converter.ERPObjectConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserERPObjectService extends AbstractERPObjectService<User> {
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
