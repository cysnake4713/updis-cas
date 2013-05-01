package com.updis.service.object;

import com.updis.entity.MessageDetail;
import com.updis.erpclient.criteria.Criteria;
import com.updis.service.converter.ERPObjectConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//        List<MessageDetail> messageDetails;
//
//        List<Criteria> criterias = new ArrayList<Criteria>();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//        criterias.add(new Criteria("write_date", ">", formatter.format(fromDate)));
//        criterias.add(new Criteria("write_date", "<", formatter.format(toDate)));
//        messageDetails = find(criterias, null, null, (String)null);

        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
        MessageDetail m = new MessageDetail();
        m.setContentId(10135);
        m.setTitle("测试用标题,这是个好人");
        messageDetails.add(m);
        return messageDetails;
    }

}
