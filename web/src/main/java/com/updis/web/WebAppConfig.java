package com.updis.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created with IntelliJ IDEA.
 * User: Shrek
 * Date: 3/17/13
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource({"classpath:updis.properties","classpath:updis_web.properties"})
public class WebAppConfig {
}
