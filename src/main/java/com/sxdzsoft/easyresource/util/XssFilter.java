package com.sxdzsoft.easyresource.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName XssFilter
 * @Description 防XXL攻击
 * @Author wujian
 * @Date 2022/4/25 15:34
 * @Version 1.0
 **/
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssFilterWrapper = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(xssFilterWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
