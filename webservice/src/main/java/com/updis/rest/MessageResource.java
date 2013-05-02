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
        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
        int offset = currentPage * pageSize;
        int categoryId = 0;
        List<Criteria> criterias = new ArrayList<Criteria>();
        try {
            categoryId = getCategoryId(categoryType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        criterias.add(new Criteria("category_id", "=", categoryId));
        try {
            messageDetails = messageDetailService.find(criterias, "write_date", offset, pageSize, null, false, getResourceDir(), getContextPath(), "name", "create_uid", "write_date", "image");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        objectMap.put("data", messageDetails);

        int pageNum = (int) Math.ceil(messageDetailService.count(criterias, null) / Double.valueOf(pageSize));
        objectMap.put("total_page", pageNum);
        return objectMap;
    }

    @RequestMapping("/fetchDetail")
    @ResponseBody
    public Map<String, Object> fetchDetail(@RequestParam("uuid") String uuid, @RequestParam("contentId") Integer contentId) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        MessageDetail messageDetail = (MessageDetail) messageDetailService.getById(contentId, getResourceDir(), getContextPath(), "name", "create_uid", "fbbm", "write_date", "image", "read_times", "message_ids", "content");
        data.put("content", messageDetail);
        data.put("comment", messageDetail.getComments());
        objectMap.put("data", data);
        return objectMap;
    }

    private Integer getCategoryId(Integer categoryTypeId) throws Exception {
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getByCategoryTypeId(categoryTypeId);
        erpConfig.setModelName("message.category");
        List<Integer> ids = objectService.search(erpConfig, Arrays.asList(new Criteria[]{new Criteria("name", "=", categoryTypeEnum.getName())}));
        return ids.get(0);
    }

    @RequestMapping("/fetchLatest")
    @ResponseBody
    public Map<String, Object> fetchLatest() {
        Map<String, Object> objectMap = new HashMap<String, Object>();

        @SuppressWarnings("deprecation")
        Date d = new Date(113, 3, 23, 23, 59, 59);
        List<MessageDetail> messageDetails = messageDetailService.getMesagesBetweenDate(d, new Date());
        objectMap.put("data", messageDetails);
        objectMap.put("success", "0");
        return objectMap;
    }

    @Override
    protected String getResourceFolderName() {
        return "message";
    }
}
