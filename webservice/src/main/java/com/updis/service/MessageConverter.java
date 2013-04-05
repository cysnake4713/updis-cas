package com.updis.service;

import com.updis.entity.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("messageConverter")
public class MessageConverter extends AbstractConverter<Message>  {
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
    }

    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public Message createInstance() {
        return new Message();
    }
}
