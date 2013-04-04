package com.updis.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/3/13
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageFactory {
    private static Logger logger = LoggerFactory.getLogger(MessageFactory.class);
    private static Map<String, String> paramMap = new HashMap<String, String>();

    static {
        paramMap.put("name", "title");
        paramMap.put("id", "contentId");
        paramMap.put("create_uid", "author");
        paramMap.put("write_date", "datetime");
        paramMap.put("image_small","iconUrl");
    }

    public static Message createMessage(Map<String, Object> params) {
        Message message = new Message();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            setAttribute(message, paramMap.get(entry.getKey()), entry.getValue());
        }
        return message;
    }
    public static List<Message> createMessages(List<Map<String, Object>> params) {
        List<Message> messages = new ArrayList<Message>();
        for(Map<String,Object> param :params){
            messages.add(createMessage(param));
        }
        return messages;
    }


    private static void setAttribute(Message message, String attribute, Object value) {
        Class<?> cls = message.getClass();
        Field field = null;
        if (value instanceof Object[]) {
            Object[] arr = (Object[]) value;
            value = arr[1];
        }
        try {
            field = cls.getDeclaredField(attribute);
            ReflectionUtils.makeAccessible(field);
            field.set(message, value);
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("name", "SHREK");
        stringObjectMap.put("write_date", new Date());
        stringObjectMap.put("create_uid", new Object[]{1, "SHREK"});
        Message message = createMessage(stringObjectMap);
        System.out.println(message);
    }
}
