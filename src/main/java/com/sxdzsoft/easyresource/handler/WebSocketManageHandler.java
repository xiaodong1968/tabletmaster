package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/5 9:49
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: WebSocketManageHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class WebSocketManageHandler {

    @GetMapping("/shutDown")
    @ResponseBody
    public int shutDown(String userId) {
        WebSocket.shutDown(userId);
        return HttpResponseRebackCode.Ok;
    }


    @GetMapping("/device-manager")
    public String deviceManager(Model model) {
        // 从后端获取设备列表
        List<String> allUser = WebSocket.getAllUser();

        // 将设备列表传递给Thymeleaf模板
        model.addAttribute("devices", allUser);

        // 返回设备管理模板名称
        return "device-manager";
    }

}
