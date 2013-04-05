package com.updis.entity;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.*;
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
    private Logger logger = LoggerFactory.getLogger(MessageFactory.class);
    private Map<String, String> paramMap = new HashMap<String, String>();

    private static MessageFactory instance;

    public static MessageFactory getInstance() {
        if (instance == null) {
            instance = new MessageFactory();
        }
        return instance;
    }

    private MessageFactory() {
        paramMap.put("name", "title");
        paramMap.put("id", "contentId");
        paramMap.put("create_uid", "author");
        paramMap.put("write_date", "datetime");
        paramMap.put("image_small", "iconUrl");
        paramMap.put("image", "iconUrl");
        paramMap.put("fbbm", "fbbm");
        paramMap.put("read_times", "readCount");
    }

    public Message createMessage(Map<String, Object> params, String serverPath, String contextPath) {
        Message message = new Message();
        updateFields(message, params, serverPath, contextPath);
        return message;
    }

    public MessageDetail createMessageDetail(Map<String, Object> params, String serverPath, String contextPath) {
        MessageDetail messageDetail = new MessageDetail();
        updateFields(messageDetail, params, serverPath, contextPath);
        return messageDetail;
    }

    public List<Message> createMessages(List<Map<String, Object>> params, String serverPath, String contextPath) {
        List<Message> messages = new ArrayList<Message>();
        for (Map<String, Object> param : params) {
            messages.add(createMessage(param, serverPath, contextPath));
        }
        return messages;
    }

    public List<MessageDetail> createMessageDetails(List<Map<String, Object>> params, String serverPath, String contextPath) {
        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
        for (Map<String, Object> param : params) {
            messageDetails.add(createMessageDetail(param, serverPath, contextPath));
        }
        return messageDetails;
    }

    private void updateFields(Message message, Map<String, Object> params, String serverPath, String contextPath) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            String attr = paramMap.get(entry.getKey());
            if (attr == null) {
                continue;
            }
            if (entry.getKey().startsWith("image") && !(value instanceof Boolean)) {
                try {
                    byte[] img = Base64.decodeBase64((String) value);
                    StringBuffer filePath = new StringBuffer();
                    filePath.append(serverPath);
                    filePath.append(File.separator);
                    String filename = params.get("id").toString() + ".png";
                    filePath.append(filename);
                    File file = new File(filePath.toString());
                    FileUtils.writeByteArrayToFile(file, img);
                    value = contextPath + filename;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            setAttribute(message, attr, value);
        }
    }

    private void setAttribute(Message message, String attribute, Object value) {
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
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        stringObjectMap.put("id", 4321);
        stringObjectMap.put("name", "SHREK");
        stringObjectMap.put("write_date", "2013-03-13");
        stringObjectMap.put("create_uid", new Object[]{1, "SHREK"});
        try {
            String file = FileUtils.readFileToString(new File("C:/users/Zhou Guangwen/97942.txt"));
            ;
            stringObjectMap.put("image", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Message message = MessageFactory.getInstance().createMessage(stringObjectMap, "c:/users/Zhou guangwen/", "http://fdsa.com/fdsa");
        System.out.println(message);
    }
}
