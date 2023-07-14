package com.sxdzsoft.easyresource.handler;


import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.echarts.EchartsForLineSmooth;
import com.sxdzsoft.easyresource.echarts.EchartsPieBorderRadius;
import com.sxdzsoft.easyresource.log.LoginLog;
import com.sxdzsoft.easyresource.log.OperationLog;
import com.sxdzsoft.easyresource.service.LogService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.util.MenuButton;
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
 * @ClassName LogManage
 * @Description 日志管理
 * @Author wujian
 * @Date 2023/5/31 18:23
 * @Version 1.0
 **/
@Controller
public class LogManageHandler {
    @Autowired
    private LogService logService;
    @Autowired
    private MenuService menuService;
    /**
     * 日志管理
     *
     * @param menuId
     * @param model
     * @return
     */
    @GetMapping(path = "/logManage")
    @MenuButton
    public String logManage(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        model.addAttribute("operationAmount", this.logService.queryLogAmount(1));
        return "pages/logManage/logManage";
    }

    /**
     * 查询指定类型日志年度逐月变化趋势图
     *
     * @param logType
     * @return
     */
    @GetMapping(path = "/queryEchartsForLineSmoothByYear")
    @ResponseBody
    public EchartsForLineSmooth queryEchartsForLineSmoothByYear(int logType) {
        return this.logService.queryLogAmountForYear(logType);
    }

    /**
     * 查询登录日志、操作日志数量统计饼状图
     *
     * @return
     */
    @GetMapping(path = "/queryEchartsForPieBorderRadiusByTypeMount")
    @ResponseBody
    public List<EchartsPieBorderRadius> queryEchartsForPieBorderRadiusByTypeMount() {
        EchartsPieBorderRadius echartsPieBorderRadius = new EchartsPieBorderRadius();
        echartsPieBorderRadius.setName("登陆日志");
        echartsPieBorderRadius.setValue((int) this.logService.queryLogAmount(0));
        EchartsPieBorderRadius echartsPieBorderRadius2 = new EchartsPieBorderRadius();
        echartsPieBorderRadius2.setName("操作日志");
        echartsPieBorderRadius2.setValue((int) this.logService.queryLogAmount(1));
        List<EchartsPieBorderRadius> results = new ArrayList<>();
        results.add(echartsPieBorderRadius);
        results.add(echartsPieBorderRadius2);
        return results;
    }

    /**
     * 查询操作/登录日志不同级别的日志数量
     * @return
     */
    @GetMapping(path = "/queryEchartsForPieBorderRadiusByLevelAmount")
    @ResponseBody
    public List<EchartsPieBorderRadius> queryEchartsForPieBorderRadiusByLevelAmount(int logType){
        return this.logService.queryLogAmountByLogLevel(logType);
    }
    /**
     * @Description 登录日志管理跳转
     * @Author wujian
     * @Date 14:55 2023/2/21
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/loginLogManage")
    @MenuButton
    public String loginLog(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/logManage/loginLogManage";
    }
    /**
     * @Description 查询登录日志分页表格
     * @Author wujian
     * @Date 14:38 2023/2/21
     * @Params [clazz, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryLoginLogsForTable")
    @ResponseBody
    public DataTableModel<LoginLog> queryLoginLogsForTable(LoginLog loginLog, @RequestParam Integer menuId, DataTableModel<LoginLog> table) {
        DataTableModel<LoginLog> result=this.logService.queryLoginLogsForTable(loginLog,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 操作日志管理跳转
     * @Author wujian
     * @Date 14:37 2023/2/21
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/operationLogManage")
    @MenuButton
    public String operationLog(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/logManage/operationLogManage";
    }
    /**
     * @Description 查询操作日志分页表格
     * @Author wujian
     * @Date 14:38 2023/2/21
     * @Params [clazz, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryOperationLogsForTable")
    @ResponseBody
    public DataTableModel<OperationLog> queryOperationLogsForTable(OperationLog operationLog, @RequestParam Integer menuId, DataTableModel<OperationLog> table) {
        DataTableModel<OperationLog> result=this.logService.queryOperationLogsForTable(operationLog,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 查看操作日志详情
     * @Author wujian
     * @Date 15:30 2023/2/21
     * @Params [dataId, model]
     * @Return
     **/
    @GetMapping(path = "/operationLogInfoDialog")
    public String showOperationLog(int dataId,Model model){
        model.addAttribute("log",this.logService.queryOperationLogById(dataId));
        return "pages/logManage/showLog";
    }
    /**
     * @Description 查看登录日志详情
     * @Author wujian
     * @Date 15:33 2023/2/21
     * @Params [dataId, model]
     * @Return
     **/
    @GetMapping(path = "/loginLogInfoDialog")
    public String showLoginLog(int dataId,Model model){
        model.addAttribute("log",this.logService.queryLoginLogById(dataId));
        return "pages/logManage/showLog";
    }
    /**
     * @Description 清除指定登录日志
     * @Author wujian
     * @Date 16:09 2023/2/21
     * @Params [logId, session]
     * @Return
     **/
    @PostMapping(path = "/delLoginLog")
    @ResponseBody
    public int clearLoginLog(int logId){
        return this.logService.clearLog(1,logId);
    }
    /**
     * @Description 清除所有的登录日志
     * @Author wujian
     * @Date 16:00 2023/2/21
     * @Params [session]
     * @Return
     **/
    @PostMapping(path = "/clearAllLoginLog")
    @ResponseBody
    public int clearAllLoginLog(HttpSession session){
        return  this.logService.clearAllLog(1);
    }
    /**
     * @Description 清除指定操作日志
     * @Author wujian
     * @Date 16:09 2023/2/21
     * @Params [logId, session]
     * @Return
     **/
    @PostMapping(path = "/delOperationLog")
    @ResponseBody
    public int delOperationLog(int logId){
        return this.logService.clearLog(0,logId);
    }
    /**
     * @Description 清除所有的操作日志
     * @Author wujian
     * @Date 16:00 2023/2/21
     * @Params [session]
     * @Return
     **/
    @PostMapping(path = "/clearAllOperationLog")
    @ResponseBody
    public int clearAllLoginLog(){
        return  this.logService.clearAllLog(0);
    }
}
