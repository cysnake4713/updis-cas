package com.updis.service;

import com.updis.entity.MessageDetail;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("messageDetailConverter")
public class MessageDetailConverter extends MessageConverter {
    @Override
    public MessageDetail createInstance() {
        return new MessageDetail();
    }
}
