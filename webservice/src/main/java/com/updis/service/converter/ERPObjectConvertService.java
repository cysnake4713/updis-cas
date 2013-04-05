package com.updis.service.converter;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ERPObjectConvertService<T extends ConvertibleERPObject> {
    /**
     * Convert ERP business object to domain object , with image convert and save support.
     *
     * @param params
     * @param serverPath
     * @param contextPath
     * @return
     */
    T convert(Map<String, Object> params, String serverPath, String contextPath);

    /**
     * Convert list of ERP business object to domain object, with image convert and save support.
     *
     * @param params
     * @param serverPath
     * @param contextPath
     * @return
     */
    List<T> convertList(List<Map<String, Object>> params, String serverPath, String contextPath);

//    /**
//     * Convert ERP business object to domain object , without image convert and save support.
//     *
//     * @param params
//     * @return
//     */
//    T convert(Map<String, Object> params);
//
//    /**
//     * Convert list of ERP business object to domain object, without image convert and save support.
//     *
//     * @param params
//     * @return
//     */
//    List<T> convertList(List<Map<String, Object>> params);
}
