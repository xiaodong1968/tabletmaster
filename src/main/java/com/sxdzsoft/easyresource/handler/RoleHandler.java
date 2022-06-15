package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.AuService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.service.RoleService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleHandler
 * @Description 角色管理处理器
 * @Author wujian
 * @Date 2022/5/10 15:01
 * @Version 1.0
 **/
@Controller
public class RoleHandler {
    private static final Logger log = LoggerFactory.getLogger("operationLog");
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private AuService auService;
    /**
     * @Description 角色管理跳转
     * @Author wujian
     * @Date 15:02 2022/5/10
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/roleManage")
    @MenuButton
    public String roleManage(Integer menuId, Model model){
        model.addAttribute("menuId", menuId);
        return "pages/rolemanage/rolemanage";
    }
    /**
     * @Description 查询角色的分页表格
     * @Author wujian
     * @Date 15:19 2022/5/10
     * @Params [role, menuId, table]
     * @Return
     **/
    @GetMapping(path = "/queryRolesForTable")
    @ResponseBody
    public DataTableModel<Role> queryRolesForTable(Role role, @RequestParam Integer menuId, DataTableModel<Role> table) {
        DataTableModel<Role> result=this.roleService.queryUserRoleForTable(role, table);
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }
    /**
     * @Description 新增角色
     * @Author wujian
     * @Date 13:38 2022/5/13
     * @Params []
     * @Return
     **/
    @GetMapping(path = "/addRoleDialog")
    public String addRoleDialog() {
        return "pages/rolemanage/addRoleDialog";
    }
    /**
     * @Description 根据权限父节点获取权限子节点（JSTREE）
     * @Author wujian
     * @Date 13:56 2022/5/13
     * @Params [parentId, roleId]
     * @Return
     **/
    @GetMapping(path = "/queryJsTreeByParentId")
    @ResponseBody
    public List<JsTreeModel> queryJsTreeByParentId(@RequestParam(value="parentId",defaultValue = "0")String parentId, @RequestParam(value="roleId",defaultValue = "-1")Integer roleId){
        Integer pid=0;
        if(!parentId.equals("#")) {//如果是根节点，JsTree会发送#
            pid=Integer.valueOf(parentId);
        }
        List<RoleAuthority> permissions=this.auService.queryAuthorityByParentIdIsAndIsUseIs(pid, 1);//根据权限父节点Id查询所有子权限节点
        List<RoleAuthority> authors=new ArrayList<RoleAuthority>();
        if(roleId!=-1) {
            Role role=this.roleService.queryRoleByIdIsAndIsUseIsNot(roleId,0);
            authors=role.getAuthorities();
        }
        List<JsTreeModel> result=new ArrayList<JsTreeModel>();
        for(RoleAuthority p:permissions) {
            JsTreeModel m=new JsTreeModel();
            m.setId(String.valueOf(p.getId()));
            m.setText(p.getRemark());
            JsTreeModelState state=new JsTreeModelState();
            state.setOpend(false);
            if(authors.size()>0) {
                if(authors.contains(p)) {
                    state.setSelected(true);
                }
                else {
                    state.setSelected(false);
                }
            }else {
                state.setSelected(false);
            }
            m.setState(state);
            if(p.getHasChild()==0) {
                m.setChildren(false);
                m.setIcon("fa fa-file");
            }
            if(p.getHasChild()==1) {
                m.setChildren(true);
                m.setIcon("fa fa-folder");
            }
            result.add(m);
        }
        return result;
    }
    /**
     * @Description 新增角色
     * @Author wujian
     * @Date 14:12 2022/5/13
     * @Params [role, authors, session]
     * @Return
     **/
    @PostMapping(path = "/addRole")
    @ResponseBody
    public int saveRole(Role role, String[] authorites, HttpSession session) {
        long r=this.roleService.countByNameIsAndIsUseIsNot(role.getName(), 0);
        if(r>0)
            return HttpResponseRebackCode.SameName;
        User user=(User)session.getAttribute("userinfo");
        log.info(user.getUsername()+"新增了角色："+role.getName());
        return this.roleService.saveRole(role, authorites);
    }
    /**
     * @Description 编辑角色
     * @Author wujian
     * @Date 14:41 2022/5/13
     * @Params [roleId, model]
     * @Return
     **/
    @GetMapping(path = "/editRoleDialog")
    public String editRoleDialog(@RequestParam Integer roleId,Model model) {
        if(roleId==1) {
            return "noAu";
        }
        Role role=this.roleService.queryRoleByIdIsAndIsUseIsNot(roleId,0);
        model.addAttribute("role", role);
        return "pages/rolemanage/editRoleDialog";
    }
    /**
     * @Description 编辑角色提交
     * @Author wujian
     * @Date 14:44 2022/5/13
     * @Params [role, addAu, delAu, session]
     * @Return
     **/
    @PostMapping(path = "/editRole")
    @ResponseBody
    public int updateRole(Role role,String[] addAu,String[] delAu,HttpSession session) {
        if(role.getId()==1) {
            User user=(User)session.getAttribute("userinfo");
            log.error(user.getUsername()+"试图修改超级管理员信息！");
            return HttpResponseRebackCode.InValidate;
        }
        Role r=this.roleService.queryRoleByNameIsAndIsUseIsNot(role.getName(), 0);
        if(r!=null&&r.getId().intValue()!=role.getId().intValue()) {
            return HttpResponseRebackCode.SameName;
        }
        if(this.roleService.updateRole(role, addAu, delAu)!=null) {
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"编辑了角色："+role.getId());
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
     /**
      * @Description 变更指定角色状态
      * @Author wujian
      * @Date 14:53 2022/5/13
      * @Params [role, session]
      * @Return
      **/
    @PostMapping(path = "/changeRole")
    @ResponseBody
    public int  changeRolestate(Role role,HttpSession session){
        if(role.getId()==1) {
            User user=(User)session.getAttribute("userinfo");
            log.error(user.getUsername()+"试图修改超级管理员信息！");
            return HttpResponseRebackCode.InValidate;
        }
        if(this.roleService.changeRoleStatue(role)!=null) {
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"改变了角色："+role.getId()+"状态");
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
    /**
     *
     * Author:WuJian
     * Description:删除指定角色
     * @param role
     * @return
     * String
     * UserHandler.java
     * LastModify:2020年2月2日
     */
    @PostMapping(path = "/delRole")
    @ResponseBody
    public int  delRole(Role role,HttpSession session){
        if(role.getId()==1) {
            User user=(User)session.getAttribute("userinfo");
            log.error(user.getUsername()+"试图删除超级管理员！");
            return HttpResponseRebackCode.InValidate;
        }
        if(this.roleService.changeRoleStatue(role)!=null) {
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"删除了角色："+role.getId());
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}
