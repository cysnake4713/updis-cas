package com.updis.rest;

import com.updis.common.CategoryTypeEnum;
import com.updis.entity.Message;
import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/27/13
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/messages")
public class MessageResource {
    private Logger logger = LoggerFactory.getLogger(MessageResource.class);
    @Autowired
    private ERPConfig erpConfig;
    @Autowired
    private ObjectService objectService;

    @RequestMapping("/fetchListData")
    @ResponseBody
    public Map<String, List<Message>> fetchMessageList(@RequestParam("uuid") String uuid, @RequestParam("categoryType") Integer categoryType, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage) {
        Map<String, List<Message>> ret = new HashMap<String, List<Message>>();
        List<Message> messages = new ArrayList<Message>();
        try {
            Integer categoryId = this.getCategoryId(categoryType);
            ERPConfig config = ERPConfig.cloneERPConfig(erpConfig, "message.message");
            List<Map<String,Object>> list = objectService.searchRead(
                    config,
//                    Arrays.asList(new Criteria[]{new Criteria("category_id", "=", categoryId)}),
                    new ArrayList<Criteria>(),
                    "name","create_uid","write_date","image_small");

            messages.add(new Message());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ret.put("data", messages);
        return ret;
    }

    private Integer getCategoryId(Integer categoryTypeId) throws Exception {
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getByCategoryTypeId(categoryTypeId);
        ERPConfig config = ERPConfig.cloneERPConfig(erpConfig,"message.category");

        List<Integer> ids = objectService.search(config, Arrays.asList(new Criteria[]{new Criteria("name", "=", categoryTypeEnum.getName())}));
        return ids.get(0);
    }
}
