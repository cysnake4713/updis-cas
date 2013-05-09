package com.updis.service.object;

import com.updis.entity.MessageDetail;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/6/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("messageDetailService")
public class MessageDetailERPObjectService extends AbstractERPObjectService {
    private static String MODEL_NAME = "message.message";
    private Logger logger = LoggerFactory.getLogger(MessageDetailERPObjectService.class);
    // 目前只发送四种类型的消息,通知,招投标信息,畅所欲言和业余生活
    private Set<String> pushableMessageCategoryNameSet = new HashSet<String>();
    {
        pushableMessageCategoryNameSet.add("通知");
        pushableMessageCategoryNameSet.add("招投标信息");
        pushableMessageCategoryNameSet.add("畅所欲言");
        pushableMessageCategoryNameSet.add("业余生活");
    }

    @Autowired
    private ERPObjectConvertService messageDetailConverter;

    @Override
    protected String getModelName() {
        return MODEL_NAME;
    }

    @Override
    protected ERPObjectConvertService getObjectConverter() {
        return messageDetailConverter;
    }

    public List<MessageDetail> getPushableMesagesBetweenDate(Date fromDate, Date toDate) {
        List<MessageDetail> messageDetails;

        List<Criteria> criterias = new ArrayList<Criteria>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        criterias.add(new Criteria("create_date_display", ">", formatter.format(fromDate)));
        criterias.add(new Criteria("create_date_display", "<", formatter.format(toDate)));
        messageDetails = find(criterias, "id desc", 0, 40, null, false, null, null, "name", "id", "create_date_display", "category_id_name");

        // 有时候ERP会抽风返回一大堆数据,具体原因不明,只能暂时这样做一下过滤.
        if (messageDetails != null) {
            SimpleDateFormat createDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Iterator<MessageDetail> iter = messageDetails.iterator(); iter.hasNext();) {
                MessageDetail message = iter.next();
                try {
                    Date messageCreateDate = createDateFormatter.parse(message.getDatetime());
                    if (messageCreateDate.getTime() < fromDate.getTime() ||
                            messageCreateDate.getTime() > toDate.getTime()) {
                        logger.debug("取推送消息时,返回了不符合条件的数据,createDate:" + createDateFormatter.format(messageCreateDate));
                        iter.remove();
                        continue;
                    }
                } catch (ParseException e) {
                    // ignore the error;
                    logger.debug("the create date format of message is not correct");
                }

                // 过滤不符合推送类型的消息.
                if (!pushableMessageCategoryNameSet.contains(message.getCategory())) {
                    iter.remove();
                    continue;
                }
            }
        }

        return messageDetails;
    }
}
