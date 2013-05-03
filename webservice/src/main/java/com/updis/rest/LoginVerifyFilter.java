package com.updis.rest;

import com.updis.entity.LoginUser;
import com.updis.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        logger.debug(path);
        if (path.startsWith("/users/login")) {   // 当前是登录路径,则不做验证.
          filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LoginUser user = (LoginUser) request.getSession(true).getAttribute("user");
            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("success", "0");
                String mapJsonString = new ObjectMapper().writeValueAsString(map);
                servletResponse.getWriter().println(mapJsonString);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
