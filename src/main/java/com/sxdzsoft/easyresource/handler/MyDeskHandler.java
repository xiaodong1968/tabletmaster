package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.Device;
import com.sxdzsoft.easyresource.service.DeviceService;
import com.sxdzsoft.easyresource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    private DeviceService deviceService;


   /**
    * @Description: 我的桌面
    * @data:[model]
    * @return: java.lang.String
    * @Author: YangXiaoDong
    * @Date: 2023/2/21 15:17
    */
    @GetMapping(path="/myDesk")
    public String myDesk(Model model){
        List<Device> devices = deviceService.queryDevicesAndIsuse();
        int onLine = 0;
        int offLine = 0;
        for (Device device : devices) {
            Integer statu = device.getStatu();
            switch (statu){
                case 1:
                    onLine++;
                    break;
                case 2:
                    offLine++;
                    break;
                default:
                    break;
            }
        }
        model.addAttribute("devices",devices);
        model.addAttribute("onLine",onLine);
        model.addAttribute("offLine",offLine);
        return "pages/mydesk/mydesk";
    }

}
