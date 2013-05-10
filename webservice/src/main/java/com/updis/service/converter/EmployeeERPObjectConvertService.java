package com.updis.service.converter;

import com.updis.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("employeeConverter")
public class EmployeeERPObjectConvertService extends AbstractERPObjectConvertService<Employee> {
    protected Map<String, String> paramMap = new HashMap<String, String>();
    {
        paramMap.put("id","userId");
        paramMap.put("name_related","name");
        paramMap.put("department_id","dept");
        paramMap.put("birthday","birthday");
        paramMap.put("gender","gender");
        paramMap.put("major","specialty");
        paramMap.put("diploma","degree");
        paramMap.put("enter_date","entryDate");
        paramMap.put("business","rank");
        paramMap.put("title","titles");
        paramMap.put("image_small","iconUrl");
//        paramMap.put("","resume");
    }
    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public Employee createInstance() {
        return new Employee();
    }
}
