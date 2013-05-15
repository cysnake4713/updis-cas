package com.updis.rest;

import com.updis.common.CategoryTypeEnum;
import com.updis.entity.MessageDetail;
import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.object.MessageDetailERPObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 */
@Controller
@RequestMapping("/messages")
public class MessageResource extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(MessageResource.class);

    @Autowired
    private MessageDetailERPObjectService messageDetailService;
    @Autowired
    private ERPConfig erpConfig;
    @Autowired
    private ObjectService objectService;

    @RequestMapping("/fetchListData")
    @ResponseBody
    public Map<String, Object> fetchMessageList(@RequestParam("uuid") String uuid,
                                                @RequestParam("categoryType") Integer categoryType,
                                                @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            int offset = (currentPage - 1) * pageSize;

            if (offset < 0) {
                objectMap.put("data",null);
                objectMap.put("errorMessage", "currentPage cannot be zero or negative");
                return objectMap;
            }

            List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
            List<Criteria> criterias = new ArrayList<Criteria>();

            String categoryName = CategoryTypeEnum.getByCategoryTypeId(categoryType).getName();
            criterias.add(new Criteria("category_id_name", "=", categoryName));
            int pageNum = (int) Math.ceil(messageDetailService.count(criterias, null) / Double.valueOf(pageSize));
            objectMap.put("total_page", pageNum);
            messageDetails = messageDetailService.find(criterias, null, offset, pageSize, null, false, getResourceDir(), getContextPath(),
                    "name", "create_uid", "create_date_display", "image", "department_id", "category_id_name", "message_meta_display");
            objectMap.put("data", messageDetails);
            return objectMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            objectMap.put("data", null);
            objectMap.put("total_page", 0);
            return objectMap;
        }
    }

    @RequestMapping("/fetchDetail")
    @ResponseBody
    public Map<String, Object> fetchDetail(@RequestParam("uuid") String uuid, @RequestParam("contentId") Integer contentId) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        MessageDetail messageDetail = (MessageDetail) messageDetailService.getById(contentId, getResourceDir(), getContextPath(),
                "name", "create_uid", "fbbm", "create_date_display", "image", "read_times", "message_ids", "content");
        data.put("content", messageDetail);
        data.put("comment", messageDetail.getComments());
        objectMap.put("data", data);
        return objectMap;
    }

    @RequestMapping("/fetchLatest")
    @ResponseBody
    public Map<String, Object> fetchLatest() {
        Map<String, Object> objectMap = new HashMap<String, Object>();

        Date f = new Date(new Date().getTime() - 5 * 60 * 1000);
        Date t = new Date();
        List<MessageDetail> messageDetails = messageDetailService.getPushableMesagesBetweenDate(f, t);
        objectMap.put("data", messageDetails);
        return objectMap;
    }

    @Override
    protected String getResourceFolderName() {
        return "message";
    }
}
