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
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ERPObjectConvertService messageConverter;
    @Autowired
    private ERPObjectConvertService messageDetailConverter;
    @Autowired
    private ERPObjectConvertService commentConverter;

    @RequestMapping("/fetchListData")
    @ResponseBody
    public List<MessageDetail> fetchMessageList(@RequestParam("uuid") String uuid,
                                                @RequestParam("categoryType") Integer categoryType,
                                                @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
        int offset = currentPage * pageSize;
        try {
            List<Criteria> criterias = new ArrayList<Criteria>();
            criterias.add(new Criteria("category_id", "=", getCategoryId(categoryType)));
            messageDetails = messageDetailService.find(criterias, "write_date", offset, pageSize, null, false, getResourceDir(), getContextPath(), "name", "create_uid", "write_date", "image");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return messageDetails;
    }

    @RequestMapping("/fetchDetail")
    @ResponseBody
    public MessageDetail fetchDetail(@RequestParam("uuid") String uuid, @RequestParam("contentId") Integer contentId) throws Exception {
        return (MessageDetail) messageDetailService.getById(contentId, getResourceDir(), getContextPath(), "name", "create_uid", "fbbm", "write_date", "image", "read_times", "message_ids");
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
}
