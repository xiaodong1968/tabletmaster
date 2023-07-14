package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.service.*;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.hibernate.boot.model.source.internal.hbm.EntityHierarchyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/6 9:45
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: DeviceHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class DeviceHandler {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private WhiteListService whiteListService;

    @Autowired
    private BlackListService blackListService;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    /**
     * @Description: 跳转设备管理页面
     * @data:[menuId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 9:21
     */
    @GetMapping("/deviceManagement")
    @MenuButton
    public String deviceManager(Integer menuId, Model model) {
        WhiteList whites = whiteListService.queryWhite();
        model.addAttribute("whites", whites);
        model.addAttribute("menuId", menuId);
        return "pages/deviceManagement/device";
    }


    /**
     * @Description: 获取设备展示表格
     * @data:[campusNews, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 10:21
     */
    @GetMapping("/queryDevices")
    @ResponseBody
    public DataTableModel<Device> queryDevices(Device device, @RequestParam Integer menuId, DataTableModel<Device> table) {
        DataTableModel<Device> result = this.deviceService.queryDeviceForTable(device, table);
        List<Menu> menus = this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }


    /**
     * @Description: 跳转新增设备页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 17:48
     */
    @GetMapping("/insertDeviceDialog")
    public String insertDeviceDialog(Model model) {
        List<Clazz> clazzes = clazzService.queryAllClazzAndStar();
        model.addAttribute("clazzes", clazzes);
        return "pages/deviceManagement/insertDeviceDialog";
    }


    /**
     * @Description: 新增设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:03
     */
    @PostMapping("/addEquipment")
    @ResponseBody
    public int addEquipment(Device device) {
        return deviceService.changeNumber(device);
    }

    /**
     * @Description: 跳转编辑设备页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:04
     */
    @GetMapping("/editDeviceDialog")
    public String editDeviceDialog(Integer devicId, Model model) {
        Device device = deviceService.queryById(devicId);
        List<Clazz> clazzes = clazzService.queryAllClazzAndStar();
        model.addAttribute("clazzes", clazzes);
        model.addAttribute("device", device);
        return "pages/deviceManagement/editDeviceDialog";
    }

    /**
     * @Description: 设备是否展示
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:11
     */
    @PostMapping("/deviceIsDisplay")
    @ResponseBody
    public int deviceIsDisplay(Device device) {
        int res = deviceService.deviceIsDisplay(device);
        return res;
    }

    /**
     * @Description: 更改设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 15:28
     */
    @PostMapping("/editDeviceShow")
    @ResponseBody
    public int editDeviceShow(Device device) {
        int res = deviceService.editDeviceShow(device);
        return res;
    }


    /**
     * @Description: 删除设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:35
     */
    @PostMapping("/delDevice")
    @ResponseBody
    public int delDevice(Device device) {
        int res = deviceService.delDevice(device);
        return res;
    }


    /**
     * @Description: 查询已启用的设备
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/11 18:25
     */
    @GetMapping("/queryAllDeviceByShow")
    @ResponseBody
    @IPCheck
    public List<Device> queryAllDeviceByShow() {
        return deviceService.queryAllDeviceAndUse();
    }

    /**
     * @Description: 跳转设置白名单页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 15:29
     */
    @GetMapping("/whiteListDialog")
    public String whiteListDialog(Model model) {
        WhiteList whiteList = whiteListService.queryWhite();
        if (whiteList != null) {
            String ipRanges1 = whiteList.getIpRanges();
            String[] split = ipRanges1.split(";");

            StringBuilder sb = new StringBuilder();

            // 将每个白名单区间或地址添加到StringBuilder中
            for (String ipRange : split) {
                sb.append(ipRange).append(System.lineSeparator());
            }

            String whiteListString = sb.toString();

            // 将whiteListString设置到页面的textarea标签中
            model.addAttribute("whiteListString", whiteListString);
        }

        return "pages/deviceManagement/whiteListDialog";
    }


    /**
     * @Description: 跳转设置黑名单页面
     * @data:[model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 14:10
     */
    @GetMapping("/blackListDialog")
    public String blackListDialog(Model model) {
        BlackList blackList = blackListService.queryBlack();
        if (blackList != null) {
            String ipRanges1 = blackList.getIpRanges();
            String[] split = ipRanges1.split(";");

            StringBuilder sb = new StringBuilder();

            // 将每个白名单区间或地址添加到StringBuilder中
            for (String ipRange : split) {
                sb.append(ipRange).append(System.lineSeparator());
            }

            String blackListString = sb.toString();

            // 将whiteListString设置到页面的textarea标签中
            model.addAttribute("blackListString", blackListString);
        }

        return "pages/deviceManagement/blackListDialog";
    }

    /**
     * @Description: 变更白名单
     * @data:[whiteList]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 16:31
     */
    @PostMapping("/changewhiteList")
    @ResponseBody
    public int changewhiteList(String ipRanges, HttpSession session) {

        String[] lines = ipRanges.trim().split("\\r?\\n");

        String str = null;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            stringBuilder.append(lines[i]);

            if (i < lines.length - 1) {
                stringBuilder.append(";");
            }
        }

        str = stringBuilder.toString();

        WhiteList whiteList = new WhiteList();
        whiteList.setIpRanges(str);

        int res = whiteListService.changewhiteList(whiteList);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改了白名单区间");
        }

        return res;
    }


    /**
     * @Description: 变更黑名单
     * @data:[ipRanges, session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 14:12
     */
    @PostMapping("/changeblackList")
    @ResponseBody
    public int changeblackList(String ipRanges, HttpSession session) {

        String[] lines = ipRanges.trim().split("\\r?\\n");

        String str = null;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            stringBuilder.append(lines[i]);

            if (i < lines.length - 1) {
                stringBuilder.append(";");
            }
        }

        str = stringBuilder.toString();

        BlackList blackList = new BlackList();
        blackList.setIpRanges(str);

        int res = blackListService.changeBlackList(blackList);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改黑名单区间");
        }

        return res;
    }

    /**
     * @Description: 变更设备主题
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/5 11:18
     */
    @PostMapping("/alterStyle")
    @ResponseBody
    public int alterStyle(Device device) {
        int res = deviceService.alterStyle(device);
        return res;
    }
}
