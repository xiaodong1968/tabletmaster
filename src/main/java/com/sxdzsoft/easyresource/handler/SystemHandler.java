package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.MySoftInfo;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserService userService;

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
    public String home(HttpServletRequest request){

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
        MySoftInfo mySoftInfo1 = this.mySoftInfo;
        model.addAttribute("softInfo",this.mySoftInfo);
        return "pages/showSoftInfo";
    }

    /**
     * @Description 显示个人信息
     * @Author wujian
     * @Date 19:26 2022/6/11
     * @Params []
     * @Return
     **/
    @GetMapping(path="/personInfo")
    public String personInfo(Model model){
        User u=(User)this.httpSession.getAttribute("userinfo");
        User user = userService.queryUserById(u.getId());
        model.addAttribute("user",user);
        return "pages/personInfo";
    }

    /**
     * @Description 修改当前用户的密码
     * @Author wujian
     * @Date 19:51 2022/6/11
     * @Params [passwd]
     * @Return
     **/
    @PostMapping(path="/changeCurrentUserPass")
    @ResponseBody
    public int changeCurrentUserPass(String passwd){
        User u=(User)this.httpSession.getAttribute("userinfo");
        return  this.userService.changeCurrentUserPass(passwd,u);
    }
}
