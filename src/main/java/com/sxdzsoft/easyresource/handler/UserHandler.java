package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.RoleService;
import com.sxdzsoft.easyresource.service.UserService;
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
import java.util.List;

/**
 * @ClassName UserHandler
 * @Description 用户
 * @Author wujian
 * @Date 2022/5/16 16:38
 * @Version 1.0
 **/
@Controller
public class UserHandler {
    private static final Logger log = LoggerFactory.getLogger("operationLog");
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    /**
     * @Description 用户管理跳转
     * @Author wujian
     * @Date 16:42 2022/5/16
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/userManage")
    @MenuButton
    public String userManage(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/usermanage/usermanage";
    }
    /**
     * @Description 查询用户的分页表格
     * @Author wujian
     * @Date 16:43 2022/5/16
     * @Params [role, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryUsersForTable")
    @ResponseBody
    public DataTableModel<User> queryUsersForTable(User user, @RequestParam Integer menuId, DataTableModel<User> table) {
        DataTableModel<User> result=this.userService.queryUserForTable(user, table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 新增用户
     * @Author wujian
     * @Date 16:56 2022/5/16
     * @Params []
     * @Return
     **/
    @GetMapping(path = "/addUserDialog")
    public String addUserDialog(Model model) {
        model.addAttribute("roles",this.roleService.queryByIsUseIs(1));
        return "pages/usermanage/addUserDialog";
    }
    /**
     * @Description 新增用户
     * @Author wujian
     * @Date 17:13 2022/5/16
     * @Params [user]
     * @Return
     **/
    @PostMapping(path="/addUser")
    @ResponseBody
    public int addUser(User user, HttpSession session){
        User currentUser=(User)session.getAttribute("userinfo");
        int result=this.userService.addUser(user);
        if(result==1){
            log.info(currentUser.getUsername()+"新增了用户："+user.getUsername());
        }
        return result;
    }
    /**
     * @Description 编辑用户
     * @Author wujian
     * @Date 10:48 2022/5/17
     * @Params [userId, model]
     * @Return
     **/
    @GetMapping(path="/editUserDialog")
    public String editUserDialog(int userId,Model model){
        model.addAttribute("roles",this.roleService.queryByIsUseIs(1));
        User user=this.userService.queryUserById(userId);
        model.addAttribute("user",user);
        return "pages/usermanage/editUserDialog";
    }
    /**
     * @Description 编辑用户
     * @Author wujian
     * @Date 10:58 2022/5/17
     * @Params [user, session]
     * @Return
     **/
    @PostMapping(path="/editUser")
    @ResponseBody
    public int editUser(User user,HttpSession session){
        User u=(User)session.getAttribute("userinfo");
        if(user.getId().intValue()==1){
            log.info(u.getUsername()+"试图修改超级管理员信息");
            return HttpResponseRebackCode.InValidate;
        }
        int result=this.userService.editUser(user);
        if(result==HttpResponseRebackCode.Ok){
            log.info(u.getUsername()+"修改用户："+user.getId());
        }
        return result;
    }
    /**
     * @Description 改变用户状态
     * @Author wujian
     * @Date 10:59 2022/5/17
     * @Params [userId, isUse, session]
     * @Return
     **/
    @PostMapping(path="/changeUser")
    @ResponseBody
    public int changeUser(int userId,int isUse,HttpSession session){
            User u=(User)session.getAttribute("userinfo");
            if(userId==1){
                log.info(u.getUsername()+"试图改变超级管理员状态");
                return HttpResponseRebackCode.InValidate;
            }
           int result= this.userService.changeUser(userId,isUse);
            if(result==HttpResponseRebackCode.Ok){
                log.info(u.getUsername()+"试图改变了"+userId+"状态");
            }
            return result;
    }

    /**
     * @Description: 改变用户心理辅导员状态
     * @data:[userId, isCare, session]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/3/14 9:34
     */
    @PostMapping("/changeCareUser")
    @ResponseBody
    public int changeCareUser(int userId,int isCare,HttpSession session){
        User u=(User)session.getAttribute("userinfo");
        if(userId==1){
            log.info(u.getUsername()+"试图改变超级管理员状态");
            return HttpResponseRebackCode.InValidate;
        }
        int result= this.userService.changeCareUser(userId,isCare);
        if(result==HttpResponseRebackCode.Ok){
            log.info(u.getUsername()+"试图改变了"+userId+"心理辅导员状态");
        }
        return result;
    }

    /**
     * @Description 删除用户
     * @Author wujian
     * @Date 10:59 2022/5/17
     * @Params [userId, isUse, session]
     * @Return
     **/
    @PostMapping(path="/delUser")
    @ResponseBody
    public int delUser(int userId,int isUse,HttpSession session){
        User u=(User)session.getAttribute("userinfo");
        if(userId==1){
            log.info(u.getUsername()+"试图删除超级管理员");
            return HttpResponseRebackCode.InValidate;
        }
        int result= this.userService.changeUser(userId,isUse);
        if(result==HttpResponseRebackCode.Ok){
            log.info(u.getUsername()+"删除了"+userId);
        }
        return result;
    }
    /**
     * @Description 密码重置
     * @Author wujian
     * @Date 16:27 2022/5/17
     * @Params [userId]
     * @Return
     **/
    @PostMapping(path="/resetPassword")
    @ResponseBody
    public int resetPassword(int userId,HttpSession session){
        User u=(User)session.getAttribute("userinfo");
        if(userId==1){
            log.info(u.getUsername()+"试图重置超级管理员密码");
            return HttpResponseRebackCode.InValidate;
        }
        int result=this.userService.resetPassword(userId);
        if(result==HttpResponseRebackCode.Ok){
            log.info(u.getUsername()+"重置了"+userId+"的密码");
        }
        return result;
    }


    /**
     * @Description: 根据用户名称进行模糊查询(只查询是心理辅导老师的)
     * @data:[userName]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/3/1 15:06
     */
    @GetMapping("/queryUserBylikeName")
    @ResponseBody
    public List<User> queryUserBylikeName(String userName){
        return userService.queryNameLike(userName);
    }

    /**
     * @Description: 根据用户名称进行模糊查询(只查询是心理辅导老师的)
     * @data:[userName]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/3/1 15:06
     */
    @GetMapping("/queryAllUserBylikeName")
    @ResponseBody
    public List<User> queryAllUserBylikeName(String userName){
        return userService.queryAllNameLike(userName);
    }
}
