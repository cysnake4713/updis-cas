package com.updis.service.converter;

import com.updis.entity.MessageDetail;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("messageDetailConverter")
public class MessageDetailERPObjectConvertService extends MessageERPObjectConvertService {
    @Override
    public MessageDetail createInstance() {
        return new MessageDetail();
    }
}
