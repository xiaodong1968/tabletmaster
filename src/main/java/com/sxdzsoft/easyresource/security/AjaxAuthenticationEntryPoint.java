package com.sxdzsoft.easyresource.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AjaxAuthenticationEntryPoint
 * @Description TODO
 * @Author wujian
 * @Date 2022/4/20 16:42
 * @Version 1.0
 **/
public class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public AjaxAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (request.getHeader("X-Requested-With")!=null&&request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
            response.sendError(408, "登陆超时请重新登陆！");
        }else {
            super.commence(request, response, authException);
        }
    }
}
