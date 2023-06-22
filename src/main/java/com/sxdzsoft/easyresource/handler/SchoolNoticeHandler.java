package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.ResultVo;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeMapper;
import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.service.DeviceService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.SchoolNoticeService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:17
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: SchoolNoticeHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class SchoolNoticeHandler {

    @Autowired
    private SchoolNoticeService schoolNoticeService;

    @Autowired
    private SchoolNoticeMapper schoolNoticeMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private WebSocket webSocket;

    /**
     * @Description: 跳转校园通知页面
     * @data:[menuId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:22
     */
    @GetMapping(path = "/schoolNotice")
    @MenuButton
    public String schoolNotice(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/schoolNotice/schoolNoticeManage";
    }


    /**
     * @Description: 获取校园通知表单数据
     * @data:[campusNews, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 9:20
     */
    @GetMapping("/querySchoolNoticeTable")
    @ResponseBody
    public DataTableModel<SchoolNotice> querySchoolNoticeTable(SchoolNotice schoolNotice, @RequestParam Integer menuId, DataTableModel<SchoolNotice> table) {
        DataTableModel<SchoolNotice> result = this.schoolNoticeService.querySchoolNoticeTable(schoolNotice, table);
        List<Menu> menus = this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }

    /**
     * @Description: 跳转新增校园通知页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 9:03
     */
    @GetMapping("/addSchoolNoticeDialog")
    public String addSchoolNoticeDialog(){
        return "pages/schoolNotice/addSchoolNoticeDialog";
    }


    /**
     * @Description: 新增校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:43
     */
    @PostMapping("/addSchoolNotice")
    @ResponseBody
    public int addSchoolNotice(SchoolNotice schoolNotice) {
        int res = schoolNoticeService.addSchoolNotice(schoolNotice);
        return res;
    }


    /**
     * @Description: 跳转校园通知修改页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 9:18
     */
    @GetMapping("/editSchoolNoticeDialog")
    public String editSchoolNoticeDialog(Integer id, Model model){
        SchoolNotice schoolNotice = schoolNoticeService.querySchoolNoticeById(id);
        model.addAttribute("schoolNotice", schoolNotice);
        return "pages/schoolNotice/editSchoolNoticeDialog";
    }


    /**
     * @Description: 修改校园通知
     * @data:[schoolNotice]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 9:33
     */
    @PostMapping("/editSchoolNotice")
    @ResponseBody
    public int editSchoolNotice(SchoolNotice schoolNotice){
        int res = schoolNoticeService.editSchoolNotice(schoolNotice);
        return res;
    }


    /**
     * @Description: 获取校园新闻的最新一条
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNotice
     * @Author: YangXiaoDong
     * @Date: 2023/5/31 15:15
     */
    @GetMapping("/schoolNoticeFirst")
    @ResponseBody
    @IPCheck
    public SchoolNotice schoolNoticeFirst(HttpServletRequest request) {
        SchoolNotice schoolNotice = schoolNoticeMapper.findFirst();
        return schoolNotice;
    }

    /**
     * @Description: 查询所有的通知用于展示
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.SchoolNotice>
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 10:30
     */
    @GetMapping("/queryAllNotice")
    @ResponseBody
    @IPCheck
    public List<SchoolNotice> queryAllNotice(HttpServletRequest request){
        List<SchoolNotice> schoolNotices = schoolNoticeService.queryAllNotice();
        return schoolNotices;
    }


    /**
     * @Description: 跳转校园通知推送设备页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 15:37
     */
    @GetMapping("/groupSchoolNoice")
    public String groupSchoolNoice(Model model,String newsId){
        List<Device> devices = deviceService.queryAllDeviceAndUseAndStatu();
        model.addAttribute("newsId",newsId);
        model.addAttribute("devices",devices);
        return "pages/schoolNotice/groupSchoolNotice";
    }


    /**
     * @Description: 数据修改推送
     * @data:[newsId]
     * @return: com.sxdzsoft.easyresource.form.ResultVo
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:33
     */
    @PostMapping("/pushSchoolNotice")
    @ResponseBody
    public ResultVo pushSchoolNotice(String newsId, @RequestParam("members[]") String[] members) {
        String[] split = newsId.split(",");
        for (String member : members) {
            for (String s : split) {
                SchoolNotice schoolNotice = schoolNoticeService.querySchoolNoticeById(Integer.valueOf(s));
                WebsocketVo<SchoolNotice> websocketVo = new WebsocketVo<>();
                webSocket.sendMessage(websocketVo.sendAll("schoolNotice",schoolNotice),member);
            }
        }
        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo;
    }

    /**
     * @Description: 删除校园通知
     * @data:[schoolNoticeId, isUse]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/17 13:21
     */
    @PostMapping("/delSchoolNotice")
    @ResponseBody
    public int delSchoolNotice(Integer id,Integer isUse){
        int res = schoolNoticeService.delSchoolNoticeById(id, isUse);
        return res;
    }
}
