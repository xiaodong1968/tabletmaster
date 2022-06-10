package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.GroupService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.MyTaskService;
import com.sxdzsoft.easyresource.service.UserService;
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
 * @ClassName GroupHandler
 * @Description 群组处理器
 * @Author wujian
 * @Date 2022/5/13 15:50
 * @Version 1.0
 **/
@Controller
public class GroupHandler {
    @Autowired
    private GroupService groupService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private MyTaskService myTaskService;
    /**
     * @Description 群组管理跳转
     * @Author wujian
     * @Date 15:52 2022/5/13
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/groupManage")
    @MenuButton
    public String groupManage(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/groupmanage/groupmanage";
    }
    /**
     * @Description 查询群组的分页表格
     * @Author wujian
     * @Date 15:53 2022/5/13
     * @Params [role, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryGroupsForTable")
    @ResponseBody
    public DataTableModel<Group> queryGroupsForTable(Group group, @RequestParam Integer menuId, DataTableModel<Group> table) {
        DataTableModel<Group> result=this.groupService.queryGroupForTable(group,table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 新增群组
     * @Author wujian
     * @Date 16:21 2022/5/13
     * @Params []
     * @Return
     **/
    @GetMapping(path = "/addGroupDialog")
    public String addGroupDialog(Model model) {
        model.addAttribute("groups",this.groupService.queryByTypeIsAndIsUseIs(0,1));
        return "pages/groupmanage/addGroupDialog";
    }
    /**
     * @Description 新增群组提交
     * @Author wujian
     * @Date 17:01 2022/5/13
     * @Params [group, admins, users]
     * @Return
     **/
    @PostMapping(path="/addGroup")
    @ResponseBody
    public int addGroup(Group group, int[] admins, int[] users, HttpSession session){
        User currentUser=(User)session.getAttribute("userinfo");
        return this.groupService.addGroup(group,admins,users,currentUser);
    }
    /**
     * @Description 查看群组成员
     * @Author wujian
     * @Date 14:42 2022/5/16
     * @Params [groupId]
     * @Return
     **/
    @GetMapping(path="/groupContacts")
    public String queryGroupUsers(int groupId,String groupName,Model model){
        List<User> members=null;
        if(groupId!=-1){
           members=this.groupService.queryGroupUsers(groupId);
        }
        List<User> users=this.userService.queryUsersByIsUse(1);
        if(groupId!=-1){
            for(User u:users){
                if(members.contains(u)){
                    u.setMember(true);
                }else{
                    u.setMember(false);
                }
            }
        }
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName","groupName");
        model.addAttribute("groupUsers",members);
        model.addAttribute("users",users);
        return "pages/groupmanage/groupContacts";
    }
    /**
     * @Description 编辑群组
     * @Author wujian
     * @Date 15:19 2022/5/16
     * @Params [groupId, model]
     * @Return
     **/
    @GetMapping(path="/editGroupDialog")
    public String editGroupDialog(int groupId,Model model){
       model.addAttribute("group",this.groupService.queryGroupByIdAndIsUseIs(groupId,1));
       return "pages/groupmanage/editGroupDialog";
    }
    /**
     * @Description 修改群组
     * @Author wujian
     * @Date 17:19 2022/5/18
     * @Params [group, admins, users, session]
     * @Return
     **/
    @PostMapping(path="/editGroup")
    @ResponseBody
    public int editGroup(Group group, int[] admins, int[] users, HttpSession session){
        User currentUser=(User)session.getAttribute("userinfo");
        return this.groupService.editGroup(group,admins,users,currentUser);
    }
    /**
     * @Description 启用/停用群组
     * @Author wujian
     * @Date 17:38 2022/5/18
     * @Params [groupId, isUse]
     * @Return
     **/
    @PostMapping(path="/changeGroup")
    @ResponseBody
    public int changeGroup(int groupId,int isUse){
        if(this.groupService.changeGroup(groupId,isUse)==1){
            return HttpResponseRebackCode.Ok;
        }
        else {
            return HttpResponseRebackCode.Fail;
        }
    }
    /**
     * @Description 删除群组
     * @Author wujian
     * @Date 17:39 2022/5/18
     * @Params [groupId, isUse]
     * @Return
     **/
    @PostMapping(path="/delGroup")
    @ResponseBody
    public int delGroup(int groupId,int isUse){
        if(this.groupService.changeGroup(groupId,isUse)==1){
            return HttpResponseRebackCode.Ok;
        }
        else {
            return HttpResponseRebackCode.Fail;
        }
    }
    /**
     * @Description 用于指定群组成员的选择（任务发布）
     * @Author wujian
     * @Date 14:58 2022/5/26
     * @Params [groupId, model]
     * @Return
     **/
    @GetMapping(path="/groupMemberSelect")
    public String groupMemberSelect(int groupId,int allMember,@RequestParam(defaultValue = "-1") int isEditFirst,@RequestParam(defaultValue = "-1")int taskId,Model model){
        List<User> members=null;
        List<User> members2=new ArrayList<User>();
        if(groupId!=-1){
            members=this.groupService.queryGroupUsers(groupId);
        }
        //编辑发布时，第一次打开选择组内成员页面
        if(allMember==0&&isEditFirst==1){
            List<User> recivers= this.myTaskService.queryTaskById(taskId).getReciver();
            for(User u:members){
                if(recivers.contains(u)){
                    u.setMember(true);
                    members2.add(u);
                }

            }
        }
        else{
            for(User u:members){
                u.setMember(true);
                members2.add(u);
            }
        }
        model.addAttribute("groupId",groupId);
        model.addAttribute("groupName","groupName");
        model.addAttribute("groupUsers",members);
        model.addAttribute("isEditFirst",isEditFirst);
        model.addAttribute("memberUsers",members2);
        return "pages/groupmanage/groupMemberSelect";
    }
}
