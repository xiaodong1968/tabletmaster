package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.domain.User;

import java.util.List;

/**
 * @ClassName GroupService
 * @Description 群组服务类
 * @Author wujian
 * @Date 2022/5/13 15:48
 * @Version 1.0
 **/
public interface GroupService {
    /**
     * @Description 查询群组的分页表格
     * @Author wujian
     * @Date 15:54 2022/5/13
     * @Params [group, group]
     * @Return
     **/
    public DataTableModel<Group> queryGroupForTable(Group group, DataTableModel<Group> table);
    /**
     * @Description 统计未被删除的群组
     * @Author wujian
     * @Date 17:12 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public long countByNameIsAndIsUseNot(String name,int isUse);
    /**
     * @Description 新增群组
     * @Author wujian
     * @Date 17:13 2022/5/13
     * @Params [group, admins, users]
     * @Return
     **/
    public int addGroup(Group group, int[] admins, int[] users, User currentUser);
    /**
     * @Description 查看群组成员
     * @Author wujian
     * @Date 14:43 2022/5/16
     * @Params [groupId]
     * @Return
     **/
    public List<User> queryGroupUsers(int groupId);
    /**
     * @Description 根据启用状态和ID查询群组
     * @Author wujian
     * @Date 15:20 2022/5/16
     * @Params [groupId, isUse]
     * @Return
     **/
    public Group queryGroupByIdAndIsUseIs(int groupId,int isUse);
    /**
     * @Description 编辑群组提交
     * @Author wujian
     * @Date 17:25 2022/5/18
     * @Params [group, admins, users, currentUser]
     * @Return
     **/
    public int editGroup(Group group, int[] admins, int[] users, User currentUser);
    /**
     * @Description 改变群组状态
     * @Author wujian
     * @Date 17:35 2022/5/18
     * @Params [groupId, isUse]
     * @Return
     **/
    public int changeGroup(int groupId,int isUse);
}
