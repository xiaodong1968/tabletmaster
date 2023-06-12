package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.service.UserService;
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
    private HttpSession httpSession;

    @Autowired
    private UserService userService;


   /**
    * @Description: 我的桌面
    * @data:[model]
    * @return: java.lang.String
    * @Author: YangXiaoDong
    * @Date: 2023/2/21 15:17
    */
    @GetMapping(path="/myDesk")
    public String myDesk(Model model){
        return "pages/mydesk/mydesk";
    }

}
