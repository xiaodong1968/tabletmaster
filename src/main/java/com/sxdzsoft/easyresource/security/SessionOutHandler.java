package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.util.MyNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SessionOutHandler
 * @Description Session超时处理
 * @Author wujian
 * @Date 2022/4/20 16:22
 * @Version 1.0
 **/
@Service
public class SessionOutHandler implements InvalidSessionStrategy {
    private static final Logger log = LoggerFactory.getLogger("loginLog");
    /**
     * @Description TODO
     * @Author 处理session超时
     * @Date 16:22 2022/4/20
     * @Params [request, response]
     * @Return
     **/
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String IP= MyNetUtils.getRemoteHost(request);
        log.warn(IP+":登录超时！");
        //session 超时后，清理浏览器缓存
        for(Cookie ck:request.getCookies()) {
            System.out.println(ck.getName()+"/"+ck.getPath()+"/"+ck.getValue()+"/"+ck.getMaxAge());
            ck.setMaxAge(0);
            ck.setPath("/");
            response.addCookie(ck);
        }
        if(request.getHeader("X-Requested-With")!=null&&request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
            response.sendError(408, "登陆超时，请重新登陆！");
        }
        else {
            response.sendRedirect("signIn");
        }
    }
}
