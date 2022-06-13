package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.MyForm;
import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.domain.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description 表单数据服务接口
 * @Author wujian
 * @Date 17:06 2022/5/24
 * @Params
 * @Return
 **/
public interface MyFormMapper extends JpaRepository<MyForm,Integer>,JpaSpecificationExecutor<MyForm> {
    /**
     * @Description 根据表单ID查询表单
     * @Author wujian
     * @Date 14:56 2022/5/25
     * @Params [id, isUse]
     * @Return
     **/
    public MyForm queryByIdIsAndIsUseIs(int id,int isUse);
    /**
     * @Description 获取指定用户指定类型的表单
     * @Author wujian
     * @Date 13:48 2022/5/26
     * @Params [ownerId, isUse, formType]
     * @Return
     **/
    public List<MyForm> queryByOwnerIdIsAndIsUseIsAndTypeIs(int ownerId,int isUse,int formType);
    /**
     * @Description 获取指定类型的表单
     * @Author wujian
     * @Date 9:17 2022/5/27
     * @Params [isUse, formType]
     * @Return
     **/
    public List<MyForm> queryByIsUseIsAndTypeIs(int isUse,int formType);
    /**
     * @Description 查找指定任务的指定用户的 用户表单
     * @Author wujian
     * @Date 15:54 2022/5/30
     * @Params [ownerId, taskId, isUse, type]
     * @Return
     **/
    public MyForm queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(int ownerId,int taskId,int isUse,int type);
    /**
     * @Description 获取指定任务指定类型的表单
     * @Author wujian
     * @Date 21:04 2022/6/11
     * @Params [taskId, type]
     * @Return
     **/
    public List<MyForm> queryByMyTaskIdIsAndTypeIs(int taskId,int type);
}
