package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.util.MyNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAccessDeniedHandler
 * @Description 投票器未通过时，处理访问被拒绝
 * @Author wujian
 * @Date 2022/4/20 16:04
 * @Version 1.0
 **/
@Service
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger("loginLog");
    private static final Logger log2 = LoggerFactory.getLogger("operationLog");
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication au= SecurityContextHolder.getContext().getAuthentication();//获取当前的登录用户
        //如果当前请求是一个Ajax请求
        if(accessDeniedException!=null)
            if (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
                if (au == null) {
                    String IP = MyNetUtils.getRemoteHost(request);
                    log.warn(IP + "：登录超时！");
                    response.sendError(408, "登陆超时请重新登陆！");
                } else {
                    log2.warn(au.getPrincipal() + ":未授权访问被拒！");
                    response.sendError(403, "对不起！你没有足够的操作权限");

                }
            }else{
                if(au!=null) {
                    User u=(User)au.getPrincipal();
                    log2.warn(u.getUsername()+":未授权访问被拒！");
                }
                request.getRequestDispatcher("/noAu").forward(request, response);
            }
    }
}
