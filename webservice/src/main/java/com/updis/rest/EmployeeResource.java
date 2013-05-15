package com.updis.rest;

import com.updis.common.SendSMSJob;
import com.updis.entity.Employee;
import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.object.ERPObjectService;
import com.updis.service.object.EmployeeERPObjectService;
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
 */
@Controller
@RequestMapping("/users")
public class EmployeeResource extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(EmployeeResource.class);
    @Autowired
    private EmployeeERPObjectService employeeService;
    @Autowired
    private UserERPObjectService userService;

    @RequestMapping("/queryPerson")
    @ResponseBody
    public Map<String, Object> queryUser(
            @RequestParam("flag") Integer flag,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "deptName", required = false) String deptName,
            @RequestParam(value = "specialtyName", required = false) String specialtyName,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        Map<String,Object> objectMap = new HashMap<String, Object>();
        switch (flag) {
            case 1:
                if (currentPage <= 0 || pageSize <= 0) {
                    objectMap.put("data", null);
                    objectMap.put("errorMsg", "Invalid pageNumber or invalid pageSize");
                    return objectMap;
                }

                int offset = (currentPage - 1) * pageSize;

                List<Criteria> criterias = new ArrayList<Criteria>();
                if (userName != null) {
                    criterias.add(new Criteria("name_related", "like", userName));
                }
                if (deptName != null) {
                    criterias.add(new Criteria("department_id", "=", deptName));
                }
                if (specialtyName != null) {
                    criterias.add(new Criteria("major", "=", specialtyName));
                }
                objectMap.put("data",findUsersByCriterias(criterias, offset, pageSize));
                int totalPage = (int) Math.ceil(employeeService.count(criterias, null) / Double.valueOf(pageSize));
                System.out.println(employeeService.count(criterias, null));
                objectMap.put("total_page", totalPage);
                break;
            case 2:
                if (userId <= 0) {
                    objectMap.put("data", "Invalid userId");
                } else {
                    objectMap.put("data",findUser(userId));
                }
                break;
            default:
                objectMap.put("data", "Invalid flag");
                break;
        }
        return objectMap;
    }

    private List<Employee> findUsersByCriterias(List<Criteria> criterias, Integer offset, Integer pageSize) {
        List<Employee> employees = employeeService.find(criterias, null, offset, pageSize, null, false, getResourceDir(), getContextPath());
        return employees;
    }

    private Employee findUser(Integer userId) {
        Criteria criteria = new Criteria("id", "=", userId);
        List<Criteria> criterias = Arrays.asList(new Criteria[]{criteria});
        List<Employee> employees = employeeService.find(criterias, getResourceDir(), getContextPath());
        return employees.get(0);
    }

    @RequestMapping("/fetchDicData")
    @ResponseBody
    public Map<String, Object> personDictData() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("dept", employeeService.personDepartments());
        dataMap.put("subject", employeeService.personMajors());
        resultMap.put("data", dataMap);
        return resultMap;
    }

    @RequestMapping("/deviceVerify")
    @ResponseBody
    public Map<String, Object> deviceVerify(@RequestParam("verificationCode") String verifyCode,
                                            HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String sessionVerifyCode = (String)session.getAttribute("verifyCode");
        if (sessionVerifyCode == null) {
            resultMap.put("success", 0);
            resultMap.put("msg", "You have not logged yet.");
        } else if (!sessionVerifyCode.equals(verifyCode)) {
            resultMap.put("success", 0);
            resultMap.put("msg", "Your veifyCode is wrong");
        } else {
            User user = (User)session.getAttribute("notVerifiedUser");
            String mac = (String)session.getAttribute("mac");
            boolean addResult =  userService.addDeviceIdToUsersRegisterDevice(mac, user.getUserId());
            if (addResult) {
                resultMap.put("success", 1);
                resultMap.put("msg", "Verify succeed");
                session.setAttribute("UPDIS_USER", user);
            } else {
                resultMap.put("success", 0);
                resultMap.put("msg", "Unknow Internal error, please try again");
            }
        }

        return resultMap;
    }

    @RequestMapping("/resendVerifyCode")
    @ResponseBody
    public Map<String, Object> resendVerifyCode(HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String userPhoneNumber = (String)session.getAttribute("userPhoneNumber");
        if (userPhoneNumber == null) {
            resultMap.put("success", 0);
        } else {
            sendVerifyCode(userPhoneNumber, session);
            resultMap.put("success", 1);
        }

        return resultMap;
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username") String username,
                                     @RequestParam("pwd") String password,
                                     @RequestParam("mac") String mac, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = userService.findUser(username, password.toLowerCase());
        if (user != null) {             // 用户存在.
            resultMap.put("success", 1);
            resultMap.put("msg", "login succeed");
            resultMap.put("userid", user.getUserId());
            // 判断 mac 是否在用户的设备列表中.
            boolean isDeviceRegistered = userService.IsDeviceIdRegisterWithUser(mac.toLowerCase(), user.getUserId());
            if (isDeviceRegistered) {   // 已存在,登录成功, session 中设置 UPDIS_USER.
                resultMap.put("registered", 1);
                resultMap.put("phonenum", user.getMobileNumber());
                session.setAttribute("UPDIS_USER", user);
                return resultMap;
            } else {                    // 需要发短信,进行验证.
                String userMobileNumber = user.getMobileNumber();
                // 去填手机号吧.
                if (userMobileNumber == null || userMobileNumber.length() != 11) {
                    resultMap.put("registered", 99);
                    resultMap.put("phonenum", null);
                    return resultMap;
                }

                resultMap.put("registered", 0);
                resultMap.put("phonenum", userMobileNumber);
                session.setAttribute("notVerifiedUser", user); // 还未验证过的 user 对象.
                session.setAttribute("mac", mac);
                // 发短信进行验证.session 中设置发送的验证码号码,手机号,及发送的时间(可用作有效期).
                sendVerifyCode(userMobileNumber, session);
                return resultMap;
            }
        } else {                    // 用户不存在.
            resultMap.put("success", 0);
            resultMap.put("msg", "login failed");
            resultMap.put("userid", -1);
            resultMap.put("registered", 0);
            resultMap.put("phonenum", null);
            return resultMap;
        }
    }

    private void sendVerifyCode(String userMobileNumber, HttpSession session) {
        String verifyCode = (new Random().nextInt(999999 - 100000 + 1) + 100000) + "";
        session.setAttribute("verifyCode", verifyCode);
        session.setAttribute("userPhoneNumber", userMobileNumber);
        session.setAttribute("sendDate", new Date());
        //SendSMSJob.sendSMS(userMobileNumber, verifyCode);
        String verifyMessage = "验证码：" + verifyCode + "，请不要把此验证码泄露给任何人，如非本人使用请及时修改登录密码。";
        SendSMSJob.sendSMS("18682118793", verifyMessage);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        session.removeAttribute("UPDIS_USER");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", "1");
        return result;
    }

    @RequestMapping("/fetchDeviceIds")
    @ResponseBody
    public Map<String, Object> queryDevice(@RequestParam("userId") Integer userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Set<String> deviceIds = userService.deviceIdsOfUser(userId);
            resultMap.put("devices", deviceIds);
            return resultMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.put("devices", null);
            resultMap.put("error", "An internal error happens.");
            return resultMap;
        }
    }

    @RequestMapping("/addDevice")
    @ResponseBody
    public Map<String, Object> addDevice(@RequestParam("userId") Integer userId, @RequestParam("deviceId") String deviceId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            boolean result = userService.addDeviceIdToUsersRegisterDevice(deviceId, userId);
            resultMap.put("result", result);
            return resultMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.put("result", false);
            resultMap.put("error", "An internal error happens.");
            return resultMap;
        }

    }

    @Override
    protected String getResourceFolderName() {
        return "user";
    }
}
