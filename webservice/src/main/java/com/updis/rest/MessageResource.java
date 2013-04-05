package com.updis.rest;

import com.updis.common.CategoryTypeEnum;
import com.updis.entity.Message;
import com.updis.entity.MessageDetail;
import com.updis.erpclient.ObjectService;
import com.updis.erpclient.config.ERPConfig;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.ERPObjectConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class MessageResource {
    private Logger logger = LoggerFactory.getLogger(MessageResource.class);
    @Autowired
    private ERPConfig erpConfig;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
//    @Qualifier("messageConverter")
    private ERPObjectConverter messageConverter;
    @Autowired
    private ERPObjectConverter messageDetailConverter;

    @RequestMapping("/fetchListData")
    @ResponseBody
    public Map<String, List<Message>> fetchMessageList(@RequestParam("uuid") String uuid,
                                                       @RequestParam("categoryType") Integer categoryType,
                                                       @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                                                       @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        Map<String, List<Message>> ret = new HashMap<String, List<Message>>();
        List<Message> messages = new ArrayList<Message>();
        int offset = currentPage * pageSize;
        try {
            Integer categoryId = this.getCategoryId(categoryType);
            List<Message> messages1 = messageConverter.convertList(
                    readMessages(Arrays.asList(new Criteria[]{new Criteria("category_id", "=", categoryId)}), offset, pageSize, "name", "create_uid", "write_date", "image"),
                    getMessageResourceDir(),
                    getContextPath());
            messages.addAll(messages1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ret.put("data", messages);
        return ret;
    }

    @RequestMapping("/fetchDetail")
    @ResponseBody
    public MessageDetail fetchDetail(@RequestParam("uuid") String uuid, @RequestParam("contentId") Integer contentId) throws Exception {
        List<Map<String, Object>> messages = this.readMessages(Arrays.asList(new Criteria[]{new Criteria("id", "=", contentId)}), 0, 1,
                "name", "create_uid", "fbbm", "write_date", "image", "read_times", "message_ids");

        List<MessageDetail> messageDetails = messageDetailConverter.convertList(messages, getMessageResourceDir(), getContextPath());
        if (messageDetails.size() > 0) {
            MessageDetail messageDetail = messageDetails.get(0);
            List<Integer> messageIds = new ArrayList<Integer>();
            for (Object o : (Object[]) messages.get(0).get("message_ids")) {
                messageIds.add((Integer) o);
            }
            ERPConfig commentConfig = ERPConfig.cloneERPConfig(erpConfig, "mail.message");
            List<Map<String, Object>> comments = objectService.read(commentConfig, messageIds, "subject", "author_id", "type", "body");
            return messageDetail;
        }
        throw new IllegalArgumentException("Can not find message for id" + contentId);
    }

    private Integer getCategoryId(Integer categoryTypeId) throws Exception {
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getByCategoryTypeId(categoryTypeId);
        ERPConfig config = ERPConfig.cloneERPConfig(erpConfig, "message.category");

        List<Integer> ids = objectService.search(config, Arrays.asList(new Criteria[]{new Criteria("name", "=", categoryTypeEnum.getName())}));
        return ids.get(0);
    }

    private List<Map<String, Object>> readMessages(List<Criteria> criterias, Integer offset, Integer pageSize, String... fields) {
        ERPConfig config = ERPConfig.cloneERPConfig(erpConfig, "message.message");

        List<Map<String, Object>> messages = new ArrayList<Map<String, Object>>();
        try {
            messages = objectService.searchRead(
                    config,
                    criterias, offset, pageSize, "write_date desc", null, false,
                    fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return messages;
    }

    private String getContextPath() {
        return httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/resources/images/messages/";
    }

    private String getMessageResourceDir() {
        return servletContext.getRealPath("/resources/images/messages/");
    }
}
