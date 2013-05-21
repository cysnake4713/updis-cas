package com.updis.service.object;

import com.updis.entity.Employee;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

/**
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:40 PM
 */
@Service("employeeService")
public class EmployeeERPObjectService extends AbstractERPObjectService<Employee> {
    private Logger logger = LoggerFactory.getLogger(EmployeeERPObjectService.class);

    private static final String MODEL_NAME = "hr.employee";
    @Autowired
    private ERPObjectConvertService employeeConverter;

    @Override
    public String getModelName() {
        return MODEL_NAME;
    }

    @Override
    public ERPObjectConvertService getObjectConverter() {
        return employeeConverter;
    }

    public List<Object> personDepartments() {
        List<Object> result = new ArrayList<Object>();
        try {
            erpConfig.setModelName("hr.department");
            List<Map<String, Object>> erpDepartments =  objectService.searchRead(erpConfig, new ArrayList<Criteria>(), 0, null, null, null, false, "name", "id");

            // 过滤掉重复的.
            Set<String> departmentNames = new HashSet<String>();
            for (Map<String, Object> erpDepartment : erpDepartments) {
                departmentNames.add(erpDepartment.get("name") + "");
            }

            // 排序
            Locale loc = Locale.SIMPLIFIED_CHINESE;
            Collator collator = Collator.getInstance(loc);
            List<String> departmentNameList = new ArrayList<String>(departmentNames);
            Collections.sort(departmentNameList, collator);

            // 生成为 department 对象
            for(String departmentName : departmentNameList) {
                Map<String, String> department = new HashMap<String, String>();
                department.put("deptname", departmentName);
                result.add(department);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Object> personMajors() {
        List<Object> result = new ArrayList<Object>();
        try {
            erpConfig.setModelName("hr.employee");
            List<Map<String, Object>> erpMajors = objectService.searchRead(erpConfig, null, 0, null, null, null, false, "major");

            // 过滤掉重复的.
            Set<String> majorNames = new HashSet<String>();
            for (Map<String, Object> major : erpMajors) {
                String erpMajorName = major.get("major") + "";
                if (!"false".equals(erpMajorName.toLowerCase())) {
                    majorNames.add(erpMajorName);
                }
            }

            // 排序
            Locale loc = Locale.SIMPLIFIED_CHINESE;
            Collator collator = Collator.getInstance(loc);
            List<String> majorNameList = new ArrayList<String>(majorNames);
            Collections.sort(majorNameList, collator);

            // 生成为 major 对象.
            for (String majorName : majorNameList) {
                Map<String, String> major = new HashMap<String, String>();
                major.put("subjectname", majorName);
                result.add(major);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}