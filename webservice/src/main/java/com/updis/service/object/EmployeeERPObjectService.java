package com.updis.service.object;

import com.updis.entity.Employee;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}