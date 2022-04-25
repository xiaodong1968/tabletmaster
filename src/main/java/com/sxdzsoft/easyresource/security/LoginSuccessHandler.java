package com.sxdzsoft.easyresource.security;
import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.util.MyNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName LoginSuccessHandler
 * @Description 登录成功后处理
 * @Author wujian
 * @Date 2022/4/20 16:27
 * @Version 1.0
 **/
@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger log = LoggerFactory.getLogger("loginLog");
    @Autowired
    private MenuService menuService;
    /**
     * @Description TODO
     * @Author 登录成功后处理
     * @Date 16:37 2022/4/20
     * @Params [request, response, authentication]
     * @Return
     **/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获取当前登录用户
        User user = (User) authentication.getPrincipal();
        //将用户信息存储到session
        HttpSession session = request.getSession();
        session.setAttribute("userinfo", user);
        //日志记录当前用户的登录
        String ip= MyNetUtils.getRemoteHost(request);
        log.info(user.getUsername()+"登录，登录IP:"+ip);
        //获取用户的导航菜单
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(0, 1, 1);
        session.setAttribute("menus", menus);
        //重定向至系统主页
        response.sendRedirect("home");
    }
}
