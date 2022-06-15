package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Description 角色管理数据接口
 * @Author wujian
 * @Date 2022/5/10 15:04
 * @Version 1.0
 **/
public interface RoleMapper extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    /**
     * @Description 根据ID获取正常使用的加色，isUse=1
     * @Author wujian
     * @Date 13:55 2022/5/13
     * @Params [id, isUse]
     * @Return
     **/
    public Role queryRoleByIdIsAndIsUseIs(int id,int isUse);
    /**
     * @Description 根据ID获取未删除的加色，isUse！=0
     * @Author wujian
     * @Date 10:54 2022/6/15
     * @Params [id, isUse]
     * @Return
     **/
    public Role queryRoleByIdIsAndIsUseNot(int id,int isUse);
    /**
     * @Description 根据角色名称查找正常使用的角色
     * @Author wujian
     * @Date 14:22 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public Role queryRoleByNameIsAndIsUseIs(String name,int isUse);
    /**
     * @Description 根据名称统计未被删除的角色
     * @Author wujian
     * @Date 17:05 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public long countByNameIsAndIsUseIsNot(String name,int isUse);
    /**
     * @Description 查询未被删除的角色
     * @Author wujian
     * @Date 17:09 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public Role queryByNameIsAndIsUseIsNot(String name,int isUse);
    /**
     * @Description 获取正常使用的角色(不允许读取超级管理员)
     * @Author wujian
     * @Date 17:10 2022/5/16
     * @Params [isUse]
     * @Return
     **/
    public List<Role> queryByIsUseIsAndIdIsNot(int isUse,int roleId);
}
