package com.updis.rest;

import com.updis.common.CategoryTypeEnum;
import com.updis.entity.Comment;
import com.updis.entity.Message;
import com.updis.entity.MessageDetail;
import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import com.updis.service.object.ERPObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
public class MessageResource extends AbstractResource {
    private Logger logger = LoggerFactory.getLogger(MessageResource.class);

    @Autowired
    private ERPObjectService messageDetailService;
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
    public Map<String, Object> fetchDetail(@RequestParam("uuid") String uuid, @RequestParam("contentId") Integer contentId) throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        MessageDetail messageDetail = (MessageDetail) messageDetailService.getById(contentId, getResourceDir(), getContextPath(), "name", "create_uid", "fbbm", "write_date", "image", "read_times", "message_ids");
        data.put("content", messageDetail);
        data.put("comment", messageDetail.getComments());
        objectMap.put("data", data);
        return objectMap;
    }

    private Integer getCategoryId(Integer categoryTypeId) throws Exception {
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getByCategoryTypeId(categoryTypeId);
        ERPConfig config = ERPConfig.cloneERPConfig(erpConfig, "message.category");

        List<Integer> ids = objectService.search(config, Arrays.asList(new Criteria[]{new Criteria("name", "=", categoryTypeEnum.getName())}));
        return ids.get(0);
    }


    @Override
    protected String getResourceFolderName() {
        return "message";
    }

    public static void main(String[] args) {
        double pageSize = 2;
        System.out.println(Math.ceil(5 / pageSize));
    }
}
