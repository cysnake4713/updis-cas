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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> queryUser(
            @RequestParam("flag") Integer flag,
            @RequestParam(value = "userid", required = false) Integer userId,
            @RequestParam(value = "deptid", required = false) Integer deptId,
            @RequestParam(value = "specialtyid", required = false) Integer specialtyId) {
        Map<String,Object> objectMap = new HashMap<String, Object>();
        switch (flag) {
            case 1:
                 objectMap.put("data",findUsers(deptId));
            case 2:
                objectMap.put("data",findUser(userId));
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

    @Override
    protected String getResourceFolderName() {
        return "user";
    }
}
