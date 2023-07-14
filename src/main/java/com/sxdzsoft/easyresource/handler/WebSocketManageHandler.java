package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.Device;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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


    private static final Logger log = LoggerFactory.getLogger("operationLog");



    /**
     * @Description: 向指定设备发送关机指令
     * @data:[macAddress]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 8:59
     */
    @PostMapping("/shutDown")
    @ResponseBody
    public int shutDown(String macAddress, HttpSession session) {
        webSocket.sendMessage(WebsocketVo.sendType("shutDown"),macAddress);
        //变更设备状态
        deviceService.changeFrequency(macAddress,2);
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将：" + macAddress + " 设备关机");
        return HttpResponseRebackCode.Ok;
    }

    /**
     * @Description: 在线设备一键关机
     * @data:[session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 15:53
     */
    @PostMapping("/oneKeyDeviceOff")
    @ResponseBody
    public int oneKeyDeviceOff(HttpSession session) {
        webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("shutDown"));
        for (Device device : deviceService.queryAllDeviceAndUseAndStatu()) {
            //变更设备状态
            deviceService.changeFrequency(device.getMacAddress(), 2);
        }
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将在线设备关机");
        return HttpResponseRebackCode.Ok;
    }


    /**
     * @Description: 向指定设备发送重启指令
     * @data:[macAddress]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 8:59
     */
    @PostMapping("/deviceRestar")
    @ResponseBody
    public int deviceRestar(String macAddress, HttpSession session) {
        webSocket.sendMessage(WebsocketVo.sendType("restar"),macAddress);
        //变更设备状态
        deviceService.changeFrequency(macAddress,2);
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将：" + macAddress + " 设备重启");
        return HttpResponseRebackCode.Ok;
    }

    /**
     * @Description: 在线设备一键重启
     * @data:[session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 15:39
     */
    @PostMapping("/oneKeyDeviceRestar")
    @ResponseBody
    public int oneKeyDeviceRestar(HttpSession session) {
        webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("restar"));
        for (Device device : deviceService.queryAllDeviceAndUseAndStatu()) {
            //变更设备状态
            deviceService.changeFrequency(device.getMacAddress(), 2);
        }
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将在线设备重启");
        return HttpResponseRebackCode.Ok;
    }

    /**
     * @Description: 设备刷新
     * @data:[macAddress, session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/12 16:18
     */
    @GetMapping("/deviceRefresh")
    @ResponseBody
    public int deviceRefresh(String macAddress, HttpSession session) {
        webSocket.sendMessage(WebsocketVo.sendType("refresh"),macAddress);
        //变更设备状态
        deviceService.changeFrequency(macAddress,1);
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将：" + macAddress + " 设备刷新");
        return HttpResponseRebackCode.Ok;
    }

    /**
     * @Description: 一键变更灰色主题
     * @data:[session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 16:08
     */
    @PostMapping("/oneKeyAlterStyle")
    @ResponseBody
    public int oneKeyAlterStyle(Integer alterStyle,HttpSession session) {
        List<Device> devices = deviceService.queryAllDeviceAndUseAndStatu();
        for (Device device : devices) {
            device.setAlterStyle(alterStyle);
            device.setFrequency(device.getFrequency()+1);
            deviceService.changeNumber(device);
            WebsocketVo<Device> websocketVo = new WebsocketVo<>();
            webSocket.sendMessage(websocketVo.sendAll("styleSwitch",device), device.getMacAddress());
        }
        User user = (User) session.getAttribute("userinfo");
        log.info(user.getUsername() + "将在线设备变更为灰色主题");
        return HttpResponseRebackCode.Ok;
    }
}
