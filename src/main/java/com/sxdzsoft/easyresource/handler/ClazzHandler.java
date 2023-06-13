package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.ClazzHonorVo;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.service.*;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/3/10 10:36
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: ClazzHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class ClazzHandler {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private MenuService menuService;


    @Autowired
    private DutyRosterOptionService dutyRosterOptionService;

    @Autowired
    private DutyRosterService dutyRosterService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private DeviceService deviceService;
    /**
     * @Description: 班级管理跳转
     * @data:[menuId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/3/10 11:08
     */
    @GetMapping(path = "clazzManage")
    @MenuButton
    public String jumpClazzManage(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/clazzmanage/clazzManage";
    }


    /**
     * @Description:
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 16:05
     */
    @GetMapping(path = "addClazzDialog")
    public String addClazzDialog() {
        return "pages/clazzmanage/addClazzDialog";
    }

    /**
     * @Description: 新增班级
     * @data:[clazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 16:05
     */
    @PostMapping(path = "/addClazz")
    @ResponseBody
    public int addClazz(Clazz clazz) {
        int res = clazzService.addClazz(clazz);
        return res;
    }

    /**
     * @Description: 查询班级分页表格
     * @data:[clazz, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.Clazz>
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:08
     */
    @GetMapping(path = "/queryClazzForTable")
    @ResponseBody
    public DataTableModel<Clazz> queryClazzForTable(Clazz clazz, @RequestParam Integer menuId, DataTableModel<Clazz> table) {
        DataTableModel<Clazz> result = this.clazzService.queryClazzForTable(clazz, table);
        List<Menu> menus = this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }

    /**
     * @Description: 根基id查询班级信息
     * @data:[clazzId]
     * @return: com.sxdzsoft.easyresource.domain.Clazz
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 14:19
     */
    @GetMapping("/queryClazzById")
    @ResponseBody
    public Clazz queryClazzById(Integer clazzId) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        return clazz;
    }

    /**
     * @Description: 跳转编辑班级页面
     * @data:[clazzId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:26
     */
    @GetMapping(path = "/editClazzDialog")
    public String editClazzDialog(int clazzId, Model model) {
        model.addAttribute("clazz", this.clazzService.queryById(clazzId));
        return "pages/clazzmanage/editClazzDialog";
    }


    /**
     * @Description: 编辑班级
     * @data:[clazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:26
     */
    @PostMapping(path = "/editClazz")
    @ResponseBody
    public int editClazz(Clazz clazz) {
        int i = clazzService.editClazz(clazz);
        if (i==1){
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
                webSocket.sendMessage(WebsocketVo.sendType("clazzUpdate"),device.getMacAddress());
            }
        }
        return i;
    }

    /**
     * @Description: 班级荣誉
     * @data:[clazzId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/18 10:15
     */
    @GetMapping(path = "/clazzHonorDialog")
    public String clazzHonorDialog(int clazzId, Model model) {
        Clazz clazz = this.clazzService.queryById(clazzId);
        List<MyFile> sing = clazz.getHonor();
        List<MyFile> honors = new ArrayList<>();
        for (MyFile myFile : sing) {
            if (myFile.getIsUse()==1){
                honors.add(myFile);
            }
        }
        model.addAttribute("clazzId",clazzId);
        model.addAttribute("honors",honors);
        return "pages/clazzmanage/clazzHonor";
    }


    /**
     * @Description: 班级风采
     * @data:[clazzId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/21 20:46
     */
    @GetMapping(path = "/clazzMienDialog")
    public String clazzMienDialog(int clazzId, Model model) {
        Clazz clazz = this.clazzService.queryById(clazzId);
        List<MyFile> sing = clazz.getMien();
        List<MyFile> miens  = new ArrayList<>();
        for (MyFile myFile : sing) {
            if (myFile.getIsUse()==1){
                miens.add(myFile);
            }
        }
        model.addAttribute("clazzId",clazzId);
        model.addAttribute("miens",miens);
        return "pages/clazzmanage/clazzMien";
    }

    /**
     * @Description: 班级荣誉照片更改
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 16:04
     */
    @PostMapping("/clazzHonorUpdate")
    @ResponseBody
    public int clazzHonorUpdate(ClazzHonorVo clazzHonorVo){
        int i = clazzService.clazzHonorUpdate(clazzHonorVo);
        return i;
    }

    /**
     * @Description: 班级荣誉照片删除
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 17:16
     */
    @PostMapping("/clazzHonorDelete")
    @ResponseBody
    public int clazzHonorDelete(ClazzHonorVo clazzHonorVo){
        int i = clazzService.clazzHonorDelete(clazzHonorVo);
        return i;
    }


    /**
     * @Description: 班级荣誉照片删除
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 17:16
     */
    @PostMapping("/clazzMienDelete")
    @ResponseBody
    public int clazzMienDelete(ClazzHonorVo clazzHonorVo){
        int i = clazzService.clazzMienDelete(clazzHonorVo);
        return i;
    }

    /**
     * @Description: 班级风采照片更改
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/21 21:53
     */
    @PostMapping("/clazzMienUpdate")
    @ResponseBody
    public int clazzMienUpdate(ClazzHonorVo clazzHonorVo){
        int res = clazzService.clazzMienUpdate(clazzHonorVo);
        Clazz clazz = clazzService.queryClazzById(clazzHonorVo.getClazzId());
        if (res==1){
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
                webSocket.sendMessage(WebsocketVo.sendType("clazzMienUpdate"), device.getMacAddress());
            }
        }
        return res;
    }

    /**
     * @Description: 跳转班级值日表页面
     * @data:[clazzId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 10:17
     */
    @GetMapping("/dutyRoster")
    public String dutyRoster(int clazzId,Model model){
        model.addAttribute("clazzId", clazzId);
        Clazz clazz = clazzService.queryById(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        if (dutyRoster!=null){
            model.addAttribute("dutyRosterOptions",dutyRoster.getDutyRosterOptions());
        }
        return "pages/clazzmanage/dutyRoster";
    }

    /**
     * @Description: 新建值日表
     * @data:[data, clazzId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 13:54
     */
    @PostMapping("/creatDutyRoster")
    @ResponseBody
    public int creatDutyRoster(@RequestParam(value = "formData[]") List<String> data,@RequestParam(value = "clazzId") Integer clazzId ){
        int res = dutyRosterOptionService.creatDutyRoster(data, clazzId);
        if (res==1){
            Clazz clazz = clazzService.queryClazzById(clazzId);
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
                webSocket.sendMessage(WebsocketVo.sendType("updateGruop"), device.getMacAddress());
            }
        }
        return res;
    }

    /**
     * @Description: 获取班级当日值日表
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.DutyRosterOption>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:18
     */
    @GetMapping("/getDutyRosterOption")
    @ResponseBody
    public List<DutyRosterOption> getDutyRosterOption(Integer clazzId){
        List<DutyRosterOption> dutyRosterOptions = dutyRosterOptionService.getDutyRosterOption(clazzId);
        return dutyRosterOptions;
    }


    /**
     * @Description: 跳转历史值日表页面
     * @data:[clazzId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 9:19
     */
    @GetMapping("/dutyRosterOption")
    public String dutyRosterOption(Integer clazzId,Model model){
        Clazz clazz = clazzService.queryClazzById(clazzId);
        List<DutyRosterOption> rosterOptions = dutyRosterOptionService.queryByDutyRosterIdAndGruopBy(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        List<DutyRosterOption> dutyRosterOptions = dutyRoster.getDutyRosterOptions();
        model.addAttribute("dutyRoster",dutyRoster);
        model.addAttribute("clazzId",clazzId);
        model.addAttribute("rosterOptions",rosterOptions);
        model.addAttribute("dutyRosterOptions",dutyRosterOptions);
        return "pages/clazzmanage/dutyRosterOption";
    }

    /**
     * @Description: 判断班级是否存在历史值日表
     * @data:[clazzId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 9:46
     */
    @GetMapping("/clazzHasDuty")
    @ResponseBody
    public int clazzHasDuty(Integer clazzId){
        Clazz clazz = clazzService.queryClazzById(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        if (dutyRoster!=null){
            return HttpResponseRebackCode.Ok;
        }else {
            return HttpResponseRebackCode.Fail;
        }
    }

    /**
     * @Description: 手动更新指定组别值日表
     * @data:[clazzId, groupId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 13:33
     */
    @GetMapping("/manualUpdateGroupByClazzId")
    @ResponseBody
    public int manualUpdateGroupByClazzId(Integer clazzId,Integer groupId){
        int res = dutyRosterService.manualUpdateGroupByClazzId(clazzId, groupId);
        if (res==1){
            Clazz clazz = clazzService.queryClazzById(clazzId);
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
                webSocket.sendMessage(WebsocketVo.sendType("updateGruop"), device.getMacAddress());
            }
        }
        return res;
    }

    /**
     * @Description: 查询已经启用的班级(用于外屏的班级选择)
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Clazz>
     * @Author: YangXiaoDong
     * @Date: 2023/6/7 13:58
     */
    @GetMapping("/queryAllClazzByShow")
    @ResponseBody
    public List<Clazz> queryAllClazzByShow(){
        List<Clazz> clazzes = clazzService.queryAllClazzAndStar();
        return clazzes;
    }
}
