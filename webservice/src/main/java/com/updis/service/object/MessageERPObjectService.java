package com.updis.service.object;

import com.updis.entity.Message;
import com.updis.service.converter.ERPObjectConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("messageService")
public class MessageERPObjectService extends AbstractERPObjectService<Message> {
    private static final String MODEL_NAME = "message.message";
    @Autowired
    private ERPObjectConvertService messageConverter;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    @Override
    public ERPObjectConvertService getObjectConverter() {
        return messageConverter;
    }
}
