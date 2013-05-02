package com.updis.service.converter;

import com.updis.entity.MessageDetail;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("messageDetailConverter")
public class MessageDetailERPObjectConvertService extends AbstractERPObjectConvertService<MessageDetail> {
    protected Map<String, String> paramMap = new HashMap<String, String>();
    {
        paramMap.put("name", "title");
        paramMap.put("id", "contentId");
        paramMap.put("create_uid", "author");
        paramMap.put("write_date", "datetime");
        paramMap.put("image_small", "iconUrl");
        paramMap.put("image", "iconUrl");
        paramMap.put("fbbm", "fbbm");
        paramMap.put("read_times", "readCount");
        paramMap.put("content", "content");
    }

    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public MessageDetail createInstance() {
        return new MessageDetail();
    }
}
