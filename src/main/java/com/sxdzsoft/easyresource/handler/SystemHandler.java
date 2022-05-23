package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.MySoftInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName SystemHandler
 * @Description 系统相关控制器
 * @Author wujian
 * @Date 2022/4/25 15:26
 * @Version 1.0
 **/
@Controller
public class SystemHandler {
    @Autowired
    private MySoftInfo mySoftInfo;
    /**
     * @Description 登录跳转
     * @Author wujian
     * @Date 15:28 2022/4/25
     * @Params []
     * @Return
     **/
    @GetMapping(path="/signIn")
    public String signIn(){
        return "signIn";
    }
    /**
     * @Description 跳转到主页
     * @Author wujian
     * @Date 16:54 2022/5/6
     * @Params []
     * @Return
     **/
    @GetMapping(path="/home")
    public String home(){
        return "pages/home";
    }
    /**
     * @Description 显示平台软件信息
     * @Author wujian
     * @Date 13:50 2022/5/9
     * @Params []
     * @Return
     **/
    @GetMapping(path="/showSoftInfo")
    public String showSoftInfo(Model model){
        model.addAttribute("softInfo",this.mySoftInfo);
        return "pages/showSoftInfo";
    }
}
