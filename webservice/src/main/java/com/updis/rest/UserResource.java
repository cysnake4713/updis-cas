package com.updis.rest;

import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import com.updis.service.object.ERPObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private ERPObjectService userService;

    @RequestMapping("/queryPerson")
    @ResponseBody
    public List<User> queryUser(@RequestParam("flag") Integer flag, @RequestParam("userid") Integer userId, @RequestParam("deptid") Integer deptId, @RequestParam(value = "specialtyid", required = false) Integer specialtyId) {
        switch (flag) {
            case 1:
                return findUsers(deptId);
            case 2:
                return findUser(userId);
            default:
                throw new IllegalArgumentException("wrong arguments");
        }
    }

    private List<User> findUsers(Integer deptId) {
        Criteria criteria = new Criteria("department_id", "=", deptId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<User> users = userService.find(criterias, "name_related", "image_small");
        return users;
    }

    private List<User> findUser(Integer userId) {
        Criteria criteria = new Criteria("id", "=", userId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<User> users = userService.find(criterias, getResourceDir(), getContextPath());
        return users;
    }

    @Override
    protected String getResourceFolderName() {
        return "user";
    }
}
