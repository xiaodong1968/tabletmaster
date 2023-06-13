package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.DeviceService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.WhiteListService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<WhiteList> whites = whiteListService.queryWhite();
        model.addAttribute("whites",whites);
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
        return deviceService.addEquipment(device);
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
    public int editDeviceShow(Device device){
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
    public List<Device> queryAllDeviceByShow(){
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
    public String whiteListDialog(){
        return "pages/deviceManagement/whiteListDialog";
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
    public int changewhiteList(WhiteList whiteList){
        int res = whiteListService.changewhiteList(whiteList);
        return res;
    }
}
