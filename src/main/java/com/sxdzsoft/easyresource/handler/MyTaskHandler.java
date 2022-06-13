package com.sxdzsoft.easyresource.handler;

import com.sun.star.report.Groups;
import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.GroupService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.MyFormService;
import com.sxdzsoft.easyresource.service.MyTaskService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyTaskHandler
 * @Description 我的任务
 * @Author wujian
 * @Date 2022/5/26 10:47
 * @Version 1.0
 **/
@Controller
public class MyTaskHandler {
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MyFormService myFormService;
    @Autowired
    private GroupService groupService;
    /**
     * @Description 我的发布管理跳转
     * @Author wujian
     * @Date 10:48 2022/5/26
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/myPublish")
    @MenuButton
    public String myPublish(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/taskmanage/myPublish";
    }
    /**
     * @Description 查询我的发布分页表格
     * @Author wujian
     * @Date 10:50 2022/5/26
     * @Params [task, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryMyPublishForTable")
    @ResponseBody
    public DataTableModel<MyTask> queryMyPublishForTable(MyTask task, @RequestParam Integer menuId, DataTableModel<MyTask> table) {
        User u=(User)httpSession.getAttribute("userinfo");
        task.setOwner(u);
        DataTableModel<MyTask> result=this.myTaskService.queryMyPublishForTable(task,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 新增任务发布
     * @Author wujian
     * @Date 13:44 2022/5/26
     * @Params [model]
     * @Return
     **/
    @GetMapping(path = "/addPublishDialog")
    public String  addTaskDialog(Model model){
        User u=(User)httpSession.getAttribute("userinfo");
        model.addAttribute("forms",this.myFormService.queryFormByFormType(0));
        model.addAttribute("groups",this.groupService.queryGroups(1));
        return "pages/taskmanage/addPublishDialog";
    }
    /**
     * @Description 新增任务发布
     * @Author wujian
     * @Date 16:08 2022/5/26
     * @Params [myTask, users]
     * @Return
     **/
    @PostMapping(path = "/addPublish")
    @ResponseBody
    public int addPublish(MyTask myTask,int[] users){
        User u=(User)httpSession.getAttribute("userinfo");
        return this.myTaskService.addTask(myTask,users,u);
    }
    /**
     * @Description 编辑我的发布
     * @Author wujian
     * @Date 9:34 2022/5/27
     * @Params [taksId, model]
     * @Return
     **/
    @GetMapping(path = "/editPublishDialog")
    public String editPublishDialog(int taskId,Model model){
        MyTask task =this.myTaskService.queryTaskById(taskId);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        task.setStartTimeStr(format.format(task.getStartTime()));
        task.setEndTimeStr(format.format(task.getEndTime()));
        model.addAttribute("task",task);
        model.addAttribute("forms",this.myFormService.queryFormByFormType(0));
        model.addAttribute("groups",this.groupService.queryGroups(1));
        return  "pages/taskmanage/editPublishDialog";
    }
    /**
     * @Description 编辑发布提交
     * @Author wujian
     * @Date 10:46 2022/5/27
     * @Params [myTask, users]
     * @Return
     **/
    @PostMapping(path = "/editPublish")
    @ResponseBody
    public int editPublish(MyTask myTask,int[] users){
        return this.myTaskService.editTask(myTask,users);
    }
    /**
     * @Description 删除任务
     * @Author wujian
     * @Date 11:42 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    @PostMapping(path = "/delPublish")
    @ResponseBody
    public int delPublish(int taskId){
        return this.myTaskService.delTask(taskId);
    }
    /**
     * @Description 终止任务
     * @Author wujian
     * @Date 11:42 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    @PostMapping(path = "/stopPublish")
    @ResponseBody
    public int stopPublish(int taskId){
        return this.myTaskService.stopTask(taskId);
    }
   /**
    * @Description 我的任务管理跳转
    * @Author wujian
    * @Date 14:12 2022/5/27
    * @Params [menuId, model]
    * @Return
    **/
    @GetMapping(path="/myTask")
    @MenuButton
    public String myTask(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/taskmanage/myTask";
    }
    /**
     * @Description 查询我的任务分页表格
     * @Author wujian
     * @Date 10:50 2022/5/26
     * @Params [task, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryMyTaskForTable")
    @ResponseBody
    public DataTableModel<MyTask> queryMyTaskForTable(MyTask task, @RequestParam Integer menuId, DataTableModel<MyTask> table) {
        User u=(User)httpSession.getAttribute("userinfo");
        DataTableModel<MyTask> result=this.myTaskService.queryMyTaskForTable(task,u,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 群组任务
     * @Author wujian
     * @Date 15:16 2022/5/27
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/groupTask")
    @MenuButton
    public String groupTask(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/taskmanage/groupTask";
    }
    /**
     * @Description 查询群组任务分页表格
     * @Author wujian
     * @Date 10:50 2022/5/26
     * @Params [task, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryGroupTaskForTable")
    @ResponseBody
    public DataTableModel<MyTask> queryGroupTaskForTable(MyTask task, @RequestParam Integer menuId, DataTableModel<MyTask> table) {
        User u=(User)httpSession.getAttribute("userinfo");
        DataTableModel<MyTask> result=this.myTaskService.queryGroupTaskForTable(task,u,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 查看任务表单
     * @Author wujian
     * @Date 16:38 2022/5/27
     * @Params [taskId, seeModel：0只读模式 1编辑模式]
     * @Return
     **/
    @GetMapping(path = "/seeTaskDialog")
    public String seeTaskDialog(@RequestParam(defaultValue = "-1")int groupId,int taskId,@RequestParam(defaultValue = "0") int seeModel,@RequestParam(defaultValue = "-1") int ownerId,Model model){
            MyTask task=this.myTaskService.queryTaskById(taskId);
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            task.setStartTimeStr(format.format(task.getStartTime()));
            task.setEndTimeStr(format.format(task.getEndTime()));
            model.addAttribute("task",task);
            model.addAttribute("seeModel",seeModel);
            MyForm form=null;
            User currentUser=null;
            if(seeModel==0){
                List<Group> groups=this.groupService.queryGroups(1);
                Group currentGroup=null;
                if(groupId==-1){
                     currentGroup=this.groupService.queryByTypeIsAndIsUseIs(0,1).get(0);
                }else{
                    currentGroup=this.groupService.queryGroupByIdAndIsUseIs(groupId,1);
                }
                model.addAttribute("groups",groups);
                model.addAttribute("currentGroupId",groupId);
                List<User> groupUsers= currentGroup.getUsers();
                List<User> taskUsers=task.getReciver();
                List<User> recivers=new ArrayList<User>();
                for(User u:groupUsers){
                    for(User u2:taskUsers){
                        if(u2.getId().intValue()==u.getId().intValue()){
                            recivers.add(u2);
                        }
                    }
                }
                task.setReciver(recivers);
                //如果任务还没有开始
                if(task.getStatu()==0){
                    form=task.getForm();
                }
                //如果任务已经开始或已经结束
                if(task.getStatu()==2||task.getStatu()==1){
                    if(ownerId==-1){
                        ownerId=task.getReciver().get(0).getId();
                    }
                    form=this.myFormService.queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(ownerId,taskId,1,1);
                }
            }
            else if(seeModel==1){
                 currentUser=(User)this.httpSession.getAttribute("userinfo");
                 form=this.myFormService.queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(currentUser.getId(),taskId,1,1);
            }
            int rows_n=form.getRows();
            List<Integer> rows=new ArrayList<Integer>();
            for(int i=0;i<rows_n;i++){
                rows.add(i);
            }
            //如果任务已经开始或结束，需要用用户的单元格填充模板表单单元格
            if(task.getStatu()!=0){
                List<MyFormItem> items=this.myFormService.renderMyForm(form);
                form.setItmes(items);
            }
            model.addAttribute("rows",rows) ;
            model.addAttribute("myForm",form);
            model.addAttribute("ownerId",ownerId);
            model.addAttribute("seeModel",seeModel);
            if(currentUser!=null) {
                model.addAttribute("currentUser", currentUser.getRealname());
                model.addAttribute("currentUserId", currentUser.getId());
            }
            return "pages/taskmanage/seeTaskDialog";
    }
    /**
     * @Description 任务统计信息
     * @Author wujian
     * @Date 9:51 2022/5/30
     * @Params [taskId, model]
     * @Return
     **/
    @GetMapping(path="/taskStatistics")
    public String taskStatistics(@RequestParam(defaultValue = "all") String searchName,int taskId,@RequestParam(defaultValue = "-1") int groupId,@RequestParam(defaultValue = "1") int page,Model model){
        List<Group> groups=this.groupService.queryGroups(1);
        model.addAttribute("groups",groups);
        model.addAttribute("taskId",taskId);
        model.addAttribute("searchName",searchName);
        if(groupId==-1){
            groupId=this.groupService.queryByTypeIsAndIsUseIs(0,1).get(0).getId();
        }
        model.addAttribute("groupId",groupId);
        model.addAttribute("tasks",this.myTaskService.taskStatistics(taskId,groupId,page,searchName));
        return "pages/taskmanage/taskStatistics";
    }
    /**
     * @Description 查看指定任务指定用户的完整复核记录
     * @Author wujian
     * @Date 15:29 2022/6/10
     * @Params [itemId]
     * @Return
     **/
    @GetMapping(path="/showFhRecord")
    @ResponseBody
    public TaskPoint showFhRecord(int itemId){
            return this.myTaskService.showFhRecord(itemId);
    }
    /**
     * @Description 检查指定用户组中，是否有组内成员参与了指定的任务
     * @Author wujian
     * @Date 18:10 2022/6/10
     * @Params [groupId, taskId]
     * @Return
     **/
    @GetMapping(path="/checkGroupMatchTask")
    @ResponseBody
    public int checkGroupMatchTask(int groupId,int taskId){
        MyTask task=this.myTaskService.queryTaskById(taskId);
        List<User> recivers=task.getReciver();
        Group currentGroup=this.groupService.queryGroupByIdAndIsUseIs(groupId,1);
        for(User u:recivers){
            List<Group> ugs= u.getGroups();
            for(Group g:ugs){
                if(g.getId().intValue()==currentGroup.getId().intValue()){
                    return HttpResponseRebackCode.Ok;
                }
            }
        }
        return HttpResponseRebackCode.Fail;
    }
    /**
     * @Description 清除任务数据
     * @Author wujian
     * @Date 20:53 2022/6/11
     * @Params [taskId]
     * @Return
     **/
    @PostMapping(path="/clearTaskData")
    @ResponseBody
    public int clearTaskData(int taskId){
            return this.myTaskService.clearTaskData(taskId);
    }
}
