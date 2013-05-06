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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    public List<MessageDetail> getMesagesBetweenDate(Date fromDate, Date toDate) {
        List<MessageDetail> messageDetails;

        List<Criteria> criterias = new ArrayList<Criteria>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        criterias.add(new Criteria("create_date_display", ">", formatter.format(fromDate)));
        criterias.add(new Criteria("create_date_display", "<", formatter.format(toDate)));
        messageDetails = find(criterias, null, null, "name", "id", "create_date_display");

        // 有时候ERP会抽风返回一大堆数据,具体原因不明,只能暂时这样做一下过滤.
        if (messageDetails != null) {
            SimpleDateFormat createDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Iterator<MessageDetail> iter = messageDetails.iterator(); iter.hasNext();) {
                MessageDetail message = iter.next();
                try {
                    Date messageCreateDate = createDateFormatter.parse(message.getDatetime());
                    if (messageCreateDate.getTime() < fromDate.getTime() ||
                            messageCreateDate.getTime() > toDate.getTime()) {
                        iter.remove();
                    }
                } catch (ParseException e) {
                    // ignore the error;
                    logger.debug("the create date format of message is not correct");
                }
            }
        }

//        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
//        MessageDetail m = new MessageDetail();
//        m.setContentId(10135);
//        m.setTitle("测试用标题,这是个好人");
//        messageDetails.add(m);
        return messageDetails;
    }
}
