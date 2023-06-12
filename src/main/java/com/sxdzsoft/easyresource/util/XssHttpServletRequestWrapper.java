package com.sxdzsoft.easyresource.util;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @ClassName XssHttpServletRequestWrapper
 * @Description 防XSS攻击
 * @Author wujian
 * @Date 2022/4/25 15:35
 * @Version 1.0
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        if("details".equals(name)) {
            return super.getParameterValues(name);
        }
        String[] values = super.getParameterValues(name);
        if(values==null||values.length==0) {
            return  super.getParameterValues(name);
        }
        String[] newValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = HtmlUtils.htmlEscape(values[i]);
        }
        return newValues;
    }
}
