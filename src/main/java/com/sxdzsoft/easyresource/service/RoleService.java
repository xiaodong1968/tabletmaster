package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description 角色服务类
 * @Author wujian
 * @Date 2022/5/10 15:13
 * @Version 1.0
 **/
public interface RoleService {
    /**
     * @Description 查询角色分页表格
     * @Author wujian
     * @Date 15:14 2022/5/10
     * @Params [name, isUse, table]
     * @Return
     **/
    public DataTableModel<Role> queryUserRoleForTable(Role role, DataTableModel<Role> table);
    /**
     * @Description 根据角色ID获取正常使用的角色
     * @Author wujian
     * @Date 13:54 2022/5/13
     * @Params [roleId, isUse]
     * @Return
     **/
    public Role queryRoleByIdIsAndIsUseIs(int roleId,int isUse);
    /**
     * @Description 根据角色ID获取未删除的角色
     * @Author wujian
     * @Date 10:56 2022/6/15
     * @Params [roleId, isUse]
     * @Return
     **/
    public Role queryRoleByIdIsAndIsUseIsNot(int roleId, int isUse);
    /**
     * @Description 根据角色名称查询未被删除的角色
     * @Author wujian
     * @Date 17:06 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public long countByNameIsAndIsUseIsNot(String name,int isUse);
    /**
     * @Description 根据角色的名称获取正常使用的角色
     * @Author wujian
     * @Date 14:23 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public Role queryRoleByNameIsAndIsUseIs(String name,int isUse);
    /**
     * @Description 根据名称查询未被删除的角色
     * @Author wujian
     * @Date 17:10 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public Role queryRoleByNameIsAndIsUseIsNot(String name,int isUse);
    /**
     * @Description 新增角色
     * @Author wujian
     * @Date 14:29 2022/5/13
     * @Params [role, authories]
     * @Return
     **/
    public int saveRole(Role role, String[] authories);
    /**
     * @Description 编辑角色
     * @Author wujian
     * @Date 14:46 2022/5/13
     * @Params [role, addAu, delAu]
     * @Return
     **/
    public Role updateRole(Role role,String[] addAu,String[] delAu);
    /**
     * @Description 改变加色使用状态
     * @Author wujian
     * @Date 14:55 2022/5/13
     * @Params [role]
     * @Return
     **/
    public Role changeRoleStatue(Role role);
    /**
     * @Description 获取正常使用的角色
     * @Author wujian
     * @Date 17:11 2022/5/16
     * @Params [isUse]
     * @Return
     **/
    public List<Role> queryByIsUseIs(int isUse);
}
