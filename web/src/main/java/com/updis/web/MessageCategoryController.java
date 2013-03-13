package com.updis.web;

import com.updis.erpclient.ObjectService;
import com.updis.erpclient.criteria.CriteriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        List<CriteriaServiceImpl> searchCriterias = new ArrayList<CriteriaServiceImpl>();
        List<Map<String, Object>> ret = objectService.searchRead(searchCriterias);
        return ret;
    }

    @RequestMapping(value = "messages", method = RequestMethod.GET)
    public List<Map<String, Object>> messages(@PathVariable("category_id") Integer category_id) {
        return
    }

    private List<Map<String, Object>> doSearch(List<CriteriaServiceImpl> searchCriterias) throws Exception {
        return objectService.searchRead(searchCriterias);
    }
}
