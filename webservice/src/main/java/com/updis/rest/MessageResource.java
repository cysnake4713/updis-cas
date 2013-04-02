package com.updis.rest;

import com.updis.entity.Message;
import com.updis.erpclient.config.ERPConfig;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
    private ERPConfig erpConfig;

    @RequestMapping("/fetchListData")
    @ResponseBody
    public List<Message> fetchMessageList(@RequestParam("uuid") String uuid, @RequestParam("categoryType") Integer categoryType, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage) {

        return Arrays.asList(new Message[]{new Message("Title", "Content")});
    }
}
