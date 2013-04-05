package com.updis.service.converter;

import com.updis.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("commentConverter")
public class CommentERPObjectConvertService extends AbstractERPObjectConvertService<Comment> {
    protected Map<String, String> paramMap = new HashMap<String, String>();

    {
        paramMap.put("subject", "title");
        paramMap.put("id", "contentId");
        paramMap.put("author_id", "author");
        paramMap.put("date", "datetime");
        paramMap.put("body", "content");
        paramMap.put("type", "type");
    }

    @Override
    public String getAttribute(String erpFieldName) {
        return paramMap.get(erpFieldName);
    }

    @Override
    public Comment createInstance() {
        return new Comment();
    }
}
