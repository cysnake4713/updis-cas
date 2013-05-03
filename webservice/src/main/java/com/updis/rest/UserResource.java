package com.updis.rest;

import com.updis.entity.LoginUser;
import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.object.ERPObjectService;
import com.updis.service.object.LoginUserERPObjectService;
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
public class UserResource extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private ERPObjectService userService;
    @Autowired
    private LoginUserERPObjectService loginUserService;

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

    private List<User> findUsers(Integer deptId) {
        Criteria criteria = new Criteria("department_id", "=", deptId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<User> users = userService.find(criterias, "name_related", "image_small");
        return users;
    }

    private User findUser(Integer userId) {
        Criteria criteria = new Criteria("id", "=", userId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<User> users = userService.find(criterias, getResourceDir(), getContextPath());
        return users.get(0);
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username") String username,
                                     @RequestParam("pwd") String password,
                                     @RequestParam("mac") String mac, HttpSession session) {
        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        LoginUser loginUser = loginUserService.findUser(username, password);
        if (loginUser != null) {
            stringObjectMap.put("success", "1");
            session.setAttribute("user", loginUser);
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
