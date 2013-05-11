package com.updis.service.object;

import com.updis.entity.User;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: Comcuter
 * Date: 5/3/13
 * Time: 11:12
 */
@Service("userService")
public class UserERPObjectService extends AbstractERPObjectService<User> {
    private Logger logger = LoggerFactory.getLogger(UserERPObjectService.class);

    private static final String MODEL_NAME = "res.users";
    @Autowired
    private ERPObjectConvertService userConverter;

    @Override
    protected String getModelName() {
        return MODEL_NAME;
    }

    @Override
    protected ERPObjectConvertService getObjectConverter() {
        return userConverter;
    }

    public User findUser(String username, String password) {
        try {
            List<Criteria> criterias = new ArrayList<Criteria>();
            criterias.add(new Criteria("login", "=", username));
            criterias.add(new Criteria("password_crypt", "=", password));

            List<User> users = find(criterias, null, null, (String)null);
            if (users != null & users.size() > 0) {
                return users.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean IsDeviceIdRegisterWithUser(String deviceId, Integer userId) {
        Set<String> deviceRealIds = deviceIdsOfUser(userId);
        if (deviceRealIds == null || deviceRealIds.size() == 0) {
            return false;
        } else {
            return deviceRealIds.contains(deviceId);
        }
    }

    public Set<String> deviceIdsOfUser(Integer userId) {
        try {
            // 1. 找到该用户的 devices 在 device 表中的 ids(序列号).
            erpConfig.setModelName("res.users");
            List<Criteria> criterias = new ArrayList<Criteria>();
            criterias.add(new Criteria("id", "=", userId));
            Map<String, Object> userRecord = objectService.searchRead(erpConfig, criterias, "devices").get(0);

            if (userRecord.get("devices") == null) {
                return null;
            }

            Object[] deviceTableIds = (Object[])userRecord.get("devices");
            List<Integer> deviceTableIdList = new ArrayList<Integer>();
            for (Object o : deviceTableIds) {
                deviceTableIdList.add((Integer) o);
            }

            // 2. 根据 ids 找到真实的 设备IDs;
            erpConfig.setModelName("updis.device");
            List<Map<String, Object>> deviceRecords = objectService.read(erpConfig, deviceTableIdList);
            Set<String> deviceRealIds = new HashSet<String>();
            for (Map<String, Object> deviceRecord : deviceRecords) {
                deviceRealIds.add((String)deviceRecord.get("device_id"));
            }
            return deviceRealIds;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean addDeviceIdToUsersRegisterDevice(String deviceId, Integer userId) {
        // 判断是否已经在设备列表中
        if (IsDeviceIdRegisterWithUser(deviceId, userId)) {
            logger.debug(deviceId + " 已在 userId:" + userId + " 存在");
            return true;
        }
        try {
            List<Integer> ids = Arrays.asList(userId);
            Map<String, Object> vals = new HashMap<String, Object>();
            Map<String, Object> fields = new HashMap<String, Object>();
            fields.put("device_id", deviceId);
            vals.put("devices", new Object[]{new Object[]{0, false, fields}});

            erpConfig.setModelName("res.users");
            return objectService.update(erpConfig, ids, vals);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
