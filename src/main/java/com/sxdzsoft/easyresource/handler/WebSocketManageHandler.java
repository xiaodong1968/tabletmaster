package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.Device;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private DeviceService deviceService;

    /**
     * @Description: 向指定设备发送关机指令
     * @data:[macAddress]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 8:59
     */
    @PostMapping("/shutDown")
    @ResponseBody
    public int shutDown(String macAddress) {
        webSocket.sendMessage(WebsocketVo.sendType("shutDown"),macAddress);
        //变更设备状态
        deviceService.changeDevice(macAddress,2);
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
