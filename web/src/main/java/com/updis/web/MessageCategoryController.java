package com.updis.web;

import com.updis.erpclient.ObjectService;
import com.updis.erpclient.criteria.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 3/13/13
 * Time: 1:55 PM
 *
 * @author: shrek.zhou
 */
@Controller
@RequestMapping(value = "/message_categories")
public class MessageCategoryController {
    @Autowired
    private ObjectService objectService;
//    @Autowired
//    private SearchDomainService searchDomainService;

    @RequestMapping(value = "list")
    public List<Map<String, Object>> messageCategoryList() throws Exception {
        List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
        List<Map<String, Object>> ret = objectService.searchRead(searchCriterias);
        return ret;
    }
}
