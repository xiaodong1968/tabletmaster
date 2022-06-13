package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.*;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * @ClassName MyTaskMapper
 * @Description 我的任务数据接口
 * @Author wujian
 * @Date 2022/5/26 10:43
 * @Version 1.0
 **/
public interface MyTaskMapper extends JpaRepository<MyTask, Integer>, JpaSpecificationExecutor<MyTask> {
    /**
     * @Description 根据名称统计任务
     * @Author wujian
     * @Date 16:30 2022/5/26
     * @Params [name, isUse]
     * @Return
     **/
    public long countByNameIsAndIsUseIs(String name,int isUse);
    /**
     * @Description 查询指定用户指定状态的任务（前五）
     * @Author wujian
     * @Date 11:22 2022/5/27
     * @Params [u, isUse, statu]
     * @Return
     **/
    public List<MyTask> queryTop5ByReciverContainsAndIsUseIsAndStatuIs(User u, int isUse, int statu, Sort sort);
    /**
     * @Description 查询指定用户非指定状态的任务（前五）
     * @Author wujian
     * @Date 11:22 2022/5/27
     * @Params [u, isUse, statu]
     * @Return
     **/
    public List<MyTask> queryTop5ByReciverContainsAndIsUseIsAndStatuNot(User u, int isUse, int statu, Sort sort);
    /**
     * @Description 统计指定名称指定ID的任务
     * @Author wujian
     * @Date 11:23 2022/5/27
     * @Params [name, isUse, id]
     * @Return
     **/
    public long countByNameIsAndIsUseIsAndIdIsNot(String name,int isUse,int id);
    /**
     * @Description 查询指定状态的任务
     * @Author wujian
     * @Date 10:14 2022/5/31
     * @Params [isUse, statu]
     * @Return
     **/
    public List<MyTask> queryByIsUseIsAndStatuNot(int isUse,int statu);
}
