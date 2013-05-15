package com.updis.service.converter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractERPObjectConvertService<T extends ConvertibleERPObject> implements ERPObjectConvertService<T> {
    private Logger logger = LoggerFactory.getLogger(AbstractERPObjectConvertService.class);

    public abstract String getAttribute(String erpFieldName);

    public abstract T createInstance();


    public T convert(Map<String, Object> params,String imageSavePath, String urlPrefix) {
        T obj = createInstance();
        updateFields(obj, params, imageSavePath, urlPrefix);
        return obj;
    }

    public List<T> convertList(List<Map<String, Object>> params, String imageSavePath, String urlPrefix) {
        List<T> list = new ArrayList<T>(params.size());
        for (Map<String, Object> stringObjectMap : params) {
            list.add(convert(stringObjectMap, imageSavePath, urlPrefix));
        }
        return list;
    }

    protected boolean isImageField(String erpFieldName) {
        return erpFieldName.startsWith("image");
    }
    protected void updateFields(T obj, Map<String, Object> params, String imageSavePath, String urlPrefix) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            String attr = getAttribute(entry.getKey());
            if (attr == null) {
                continue;
            }
            if (isImageField(entry.getKey()) && !(value instanceof Boolean)) {
                try {
                    byte[] img = Base64.decodeBase64((String) value);
                    StringBuffer filePath = new StringBuffer();
                    filePath.append(imageSavePath);
                    filePath.append(File.separator);
                    String filename = params.get("id").toString() + ".png";
                    filePath.append(filename);
                    File file = new File(filePath.toString());
                    FileUtils.writeByteArrayToFile(file, img);
                    value = urlPrefix + filename;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            setAttribute(obj, attr, value);
        }
    }

    protected void setAttribute(T obj, String attribute, Object value) {
        Class<?> cls = obj.getClass();
        try {
            Field field = cls.getDeclaredField(attribute);
            ReflectionUtils.makeAccessible(field);

            if (value.getClass().equals(Boolean.class) && !field.getType().equals(Boolean.class)) { // 处理返回 false 代表空的情况.
                Boolean b = (Boolean)value;
                if (b.equals(Boolean.FALSE)) {
                    field.set(obj, null);
                } else {
                    if (field.getType().equals(String.class)) {
                        field.set(obj, "true");
                    } else {
                        field.set(obj, null);
                    }
                }
            } else if (value instanceof Object[]){ // 处理返回 Object[] 的情况.
                if (field.getType().isArray()) {
                    field.set(obj, value);
                } else {
                    Object[] temp = (Object[])value;
                    field.set(obj, temp[1]);
                }
            } else {
                field.set(obj, value);
            }
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
