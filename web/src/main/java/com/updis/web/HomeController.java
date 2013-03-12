package com.updis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/12/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String home(){
        return "home";
    }
}
