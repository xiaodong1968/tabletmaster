package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.MyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName MyDeskHandler
 * @Description 我的桌面
 * @Author wujian
 * @Date 2022/5/26 16:39
 * @Version 1.0
 **/
@Controller
public class MyDeskHandler {
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private HttpSession httpSession;
    /**
     * @Description 我的桌面
     * @Author wujian
     * @Date 16:40 2022/5/26
     * @Params [model]
     * @Return        
     **/
    @GetMapping(path="/myDesk")
    public String myDesk(Model model){
        User u=(User)this.httpSession.getAttribute("userinfo");
        model.addAttribute("tasks",this.myTaskService.queryTop5Task(u));
        return "pages/mydesk/mydesk";
    }
}
