package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName GroupMapper
 * @Description 群组数据模型
 * @Author wujian
 * @Date 2022/5/10 15:04
 * @Version 1.0
 **/
public interface GroupMapper extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {
    /**
     * @Description 根据指定名称统计未删除的群组
     * @Author wujian
     * @Date 17:03 2022/5/13
     * @Params [name, isUse]
     * @Return
     **/
    public long countByNameIsAndIsUseNot(String name,int isUse);
    /**
     * @Description 根据启用状态和ID查询群组
     * @Author wujian
     * @Date 15:21 2022/5/16
     * @Params [groupId, isUse]
     * @Return
     **/
    public Group queryByIdIsAndIsUseIs(int groupId,int isUse);
    /**
     * @Description 根据指定名称查询未被删除的群组
     * @Author wujian
     * @Date 17:21 2022/5/18
     * @Params [name, isUse]
     * @Return
     **/
    public Group queryByNameIsAndIsUseIsNot(String name,int isUse);
    /**
     * @Description 查询正在使用的群组
     * @Author wujian
     * @Date 14:49 2022/5/23
     * @Params [isUse]
     * @Return
     **/
    public List<Group> queryByIsUseIs(int isUse);
    /**
     * @Description 查询指定类型的群组
     * @Author wujian
     * @Date 9:04 2022/6/9
     * @Params [type, isUse]
     * @Return
     **/
    public List<Group> queryByTypeIsAndIsUseIs(int type,int isUse);
    /**
     * @Description 查询指定父群组的所有子群组
     * @Author wujian
     * @Date 9:04 2022/6/9
     * @Params [parentGroupId, isUse]
     * @Return
     **/
    public List<Group> queryByParentGroupIdIsAndIsUseIs(int parentGroupId,int isUse);
}
