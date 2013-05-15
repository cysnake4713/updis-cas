package com.updis.rest;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractResource {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ServletContext servletContext;

    protected abstract String getResourceFolderName();

    protected String getContextPath() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(httpServletRequest.getScheme());
        stringBuffer.append("://");
        stringBuffer.append(httpServletRequest.getServerName());
        stringBuffer.append(":");
        stringBuffer.append(httpServletRequest.getServerPort());
        stringBuffer.append(httpServletRequest.getContextPath());
        stringBuffer.append("/resources/images/");
        stringBuffer.append(getResourceFolderName());
        stringBuffer.append("/");
        return stringBuffer.toString();
    }

    protected String getResourceDir() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/resources/images/");
        stringBuffer.append(getResourceFolderName());
        stringBuffer.append("/");
        return servletContext.getRealPath(stringBuffer.toString());
    }
}
