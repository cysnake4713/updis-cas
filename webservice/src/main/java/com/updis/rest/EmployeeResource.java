package com.updis.rest;

import com.updis.entity.Employee;
import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.object.ERPObjectService;
import com.updis.service.object.UserERPObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/users")
public class EmployeeResource extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(EmployeeResource.class);
    @Autowired
    private ERPObjectService employeeService;
    @Autowired
    private UserERPObjectService userService;

    @RequestMapping("/queryPerson")
    @ResponseBody
    public Map<String, Object> queryUser(
            @RequestParam("flag") Integer flag,
            @RequestParam(value = "userid", required = false) Integer userId,
            @RequestParam(value = "deptid", required = false) Integer deptId,
            @RequestParam(value = "specialtyid", required = false) Integer specialtyId) {
        Map<String,Object> objectMap = new HashMap<String, Object>();
        switch (flag) {
            case 1:
                objectMap.put("data",findUsers(deptId));
                break;
            case 2:
                objectMap.put("data",findUser(userId));
                break;
        }
        return objectMap;
    }

    private List<Employee> findUsers(Integer deptId) {
        Criteria criteria = new Criteria("department_id", "=", deptId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<Employee> employees = employeeService.find(criterias, "name_related", "image_small");
        return employees;
    }

    private Employee findUser(Integer userId) {
        Criteria criteria = new Criteria("id", "=", userId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<Employee> employees = employeeService.find(criterias, getResourceDir(), getContextPath());
        return employees.get(0);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username") String username,
                                     @RequestParam("pwd") String password,
                                     @RequestParam("mac") String mac, HttpSession session) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        User user = userService.findUser(username, password.toLowerCase());
        if (user != null) {
            stringObjectMap.put("success", "1");
            session.setAttribute("user", user);
        } else {
            stringObjectMap.put("success", "0");
        }

        return stringObjectMap;
    }

    @Override
    protected String getResourceFolderName() {
        return "user";
    }
}
