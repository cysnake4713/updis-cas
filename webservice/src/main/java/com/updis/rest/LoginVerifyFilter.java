package com.updis.rest;

import com.updis.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Comcuter
 * Date: 5/1/13
 * Time: 19:52
 */
public class LoginVerifyFilter implements Filter{

    private Logger logger = LoggerFactory.getLogger(LoginVerifyFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        String[] whiteList = {"/users/login", "/users/logout", "/users/deviceVerify", "/users/resendVerifyCode"};
        boolean isPathInWhileList = false;
        for (String s : whiteList) {
            if (path.replace(contextPath, "").startsWith(s)) {
                isPathInWhileList = true;
                break;
            }
        }
        if (isPathInWhileList) {   // 当前路径在白名单中.
          filterChain.doFilter(servletRequest, servletResponse);
        } else {
            User user = (User) request.getSession(true).getAttribute("UPDIS_USER");
            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sessionTimeout", 1);
                String mapJsonString = new ObjectMapper().writeValueAsString(map);
                servletResponse.getWriter().println(mapJsonString);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
