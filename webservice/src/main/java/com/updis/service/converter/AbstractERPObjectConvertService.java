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

    @Override
    public T convert(Map<String, Object> params) {
        return convert(params, null, null);
    }

    @Override
    public List<T> convertList(List<Map<String, Object>> params) {
        return convertList(params,null,null);
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
            if (isImageField(attr) && !(value instanceof Boolean)) {
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
        Field field = null;
        if (value instanceof Object[]) {
            Object[] arr = (Object[]) value;
            value = arr[1];
        }
        try {
            field = cls.getDeclaredField(attribute);
            ReflectionUtils.makeAccessible(field);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
