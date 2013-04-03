package com.updis.entity;

import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/3/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageFactory {
    public static Message createMessage(Map<String, Object> params) {
        Message message = new Message();
        message.setTitle((String) params.get("name"));
//        message.setAuthor(params.get("create_uid")[1]);
        message.setContentId((Integer) params.get("id"));
        message.setDatetime((Date) params.get("write_date"));
        return message;
    }
}
