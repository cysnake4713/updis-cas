package com.updis.service.object;

import com.updis.service.converter.ERPObjectConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/6/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("messageDetailService")
public class MessageDetailERPObjectService extends AbstractERPObjectService {
    private static String MODEL_NAME = "message.message";

    @Autowired
    private ERPObjectConvertService messageDetailConverter;

    @Override
    protected String getModelName() {
        return MODEL_NAME;
    }

    @Override
    protected ERPObjectConvertService getObjectConverter() {
        return messageDetailConverter;
    }
}
