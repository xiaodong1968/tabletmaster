package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.util.MyNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName FailureHandler
 * @Description 登录失败处理
 * @Author wujian
 * @Date 2022/4/20 16:40
 * @Version 1.0
 **/
@Service
public class FailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger("loginLog");
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String IP= MyNetUtils.getRemoteHost(request);
        log.warn("IP:"+IP+",用户名或密码错误！");
        request.getSession().setAttribute("errorMsg", "用户名或密码错误！");
        redirectStrategy.sendRedirect(request, response, "/signIn");
    }
}
