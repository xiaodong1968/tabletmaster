package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.ClazzHonorVo;
import com.sxdzsoft.easyresource.form.ClazzShowVo;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.service.*;
import com.sxdzsoft.easyresource.util.MenuButton;
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

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClazzTeacherService clazzTeacherService;

    @Autowired
    private CampusNewsClazzService campusNewsClazzService;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

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
    public String addClazzDialog(Model model) {
        List<Course> courseAll = courseService.getCourseAll();
        model.addAttribute("courseAll", courseAll);
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
    public int addClazz(Clazz clazz, HttpSession session) {
        int res = clazzService.addClazz(clazz);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "新增了班级：" + clazz.getClazzName());
        }
        return res;


    }

//    private Map<String, String> parseCourseValues(String[] courseValuesArray) {
//        Map<String, String> courseValuesMap = new HashMap<>();
//
//        // 假设courseValues参数的值是一个JSON字符串
//        String courseValuesJson = courseValuesArray[0];
//
//        // 解析JSON字符串，并将其转换为Map集合
//        // 使用您喜欢的JSON库进行解析操作
//        // 以下是一个示例使用Jackson库进行解析的代码
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            courseValuesMap = objectMapper.readValue(courseValuesJson, new TypeReference<Map<String, String>>() {
//            });
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return courseValuesMap;
//    }

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
    @IPCheck
    public ClazzShowVo queryClazzById(Integer clazzId) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        List<ClazzroomCourseTeacher> courseTeachers = clazzTeacherService.getByClazzId(clazzId);
        ClazzShowVo clazzShowVo = new ClazzShowVo();
        clazzShowVo.setClazz(clazz);
        clazzShowVo.setCourseTeachers(courseTeachers);
        return clazzShowVo;
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
    public int editClazz(Clazz clazz, HttpSession session) {
        int res = clazzService.editClazz(clazz);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改了班级：" + clazz.getClazzName());
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
                webSocket.sendMessage(WebsocketVo.sendType("clazzUpdate"), device.getMacAddress());
            }
        }
        return res;
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
            if (myFile.getIsUse() == 1) {
                honors.add(myFile);
            }
        }
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("honors", honors);
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
        List<MyFile> miens = new ArrayList<>();
        for (MyFile myFile : sing) {
            if (myFile.getIsUse() == 1) {
                miens.add(myFile);
            }
        }
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("miens", miens);
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
    public int clazzHonorUpdate(ClazzHonorVo clazzHonorVo, HttpSession session) {
        int res = clazzService.clazzHonorUpdate(clazzHonorVo);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改了班级荣誉照片");
        }
        return res;
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
    public int clazzHonorDelete(ClazzHonorVo clazzHonorVo, HttpSession session) {
        int res = clazzService.clazzHonorDelete(clazzHonorVo);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "删除了班级荣誉照片");
        }
        return res;
    }


    /**
     * @Description: 班级风采照片删除
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 17:16
     */
    @PostMapping("/clazzMienDelete")
    @ResponseBody
    public int clazzMienDelete(ClazzHonorVo clazzHonorVo, HttpSession session) {
        int res = clazzService.clazzMienDelete(clazzHonorVo);
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "删除了班级风采照片");
        }
        return res;
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
    public int clazzMienUpdate(ClazzHonorVo clazzHonorVo, HttpSession session) {
        int res = clazzService.clazzMienUpdate(clazzHonorVo);
        Clazz clazz = clazzService.queryClazzById(clazzHonorVo.getClazzId());
        if (res == 1) {
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改了班级风采照片");
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
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
    public String dutyRoster(int clazzId, Model model) {
        model.addAttribute("clazzId", clazzId);
        Clazz clazz = clazzService.queryById(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        if (dutyRoster != null) {
            model.addAttribute("dutyRosterOptions", dutyRoster.getDutyRosterOptions());
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
    public int creatDutyRoster(@RequestParam(value = "formData[]") List<String> data, @RequestParam(value = "clazzId") Integer clazzId, HttpSession session) {
        int res = dutyRosterOptionService.creatDutyRoster(data, clazzId);
        if (res == 1) {
            Clazz clazz = clazzService.queryClazzById(clazzId);
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "为：" +clazz.getClazzName()+"更新了班级值日表");
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
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
    @IPCheck
    public List<DutyRosterOption> getDutyRosterOption(Integer clazzId) {
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
    public String dutyRosterOption(Integer clazzId, Model model) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        List<DutyRosterOption> rosterOptions = dutyRosterOptionService.queryByDutyRosterIdAndGruopBy(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        List<DutyRosterOption> dutyRosterOptions = dutyRoster.getDutyRosterOptions();
        model.addAttribute("dutyRoster", dutyRoster);
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("rosterOptions", rosterOptions);
        model.addAttribute("dutyRosterOptions", dutyRosterOptions);
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
    public int clazzHasDuty(Integer clazzId) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        if (dutyRoster != null) {
            return HttpResponseRebackCode.Ok;
        } else {
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
    public int manualUpdateGroupByClazzId(Integer clazzId, Integer groupId,HttpSession session) {
        int res = dutyRosterService.manualUpdateGroupByClazzId(clazzId, groupId);
        if (res == 1) {
            Clazz clazz = clazzService.queryClazzById(clazzId);
            User user = (User) session.getAttribute("userinfo");
            log.info(user.getUsername() + "修改了:"+clazz.getClazzName()+"值日组");
            List<Device> devices = deviceService.queryDeviceByClazzId(clazz.getId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
                webSocket.sendMessage(WebsocketVo.sendType("updateGruop"), device.getMacAddress());
            }
        }
        return res;
    }

    /**
     * @Description: 跳转任课教师页面
     * @data:[clazzId]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/25 15:09
     */
    @GetMapping("/clazzTeacher")
    public String clazzTeacher(Integer clazzId, Model model) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        List<Course> courseAll = courseService.getCourseAll();
        List<ClazzroomCourseTeacher> courseTeachers = clazzTeacherService.getByClazzId(clazzId);
        model.addAttribute("courseTeachers", courseTeachers);
        model.addAttribute("courseAll", courseAll);
        model.addAttribute("clazz", clazz);
        return "pages/clazzmanage/clazzTeacher";
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
    @IPCheck
    public List<Clazz> queryAllClazzByShow() {
        List<Clazz> clazzes = clazzService.queryAllClazzAndStar();
        return clazzes;
    }

    /**
     * @Description: 查询班级推送新闻
     * @data:[clazzId]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/6/30 22:08
     */
    @GetMapping("/queryClazzNews")
    @ResponseBody
    public DataTableModel<CampusNews> queryClazzNews(CampusNews campusNews, Integer clazzId, DataTableModel<CampusNews> table){
        DataTableModel<CampusNews> result = clazzService.queryClazzNews(campusNews,clazzId,table);
        return result;
    }

    /**
     * @Description: 跳转班级新闻管理页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 8:37
     */
    @GetMapping("/newsManageDialog")
    public String newsManageDialog(Integer clazzId,Model model){
        model.addAttribute("clazzId",clazzId);
        return "pages/clazzmanage/newsManageDialog";
    }
}
