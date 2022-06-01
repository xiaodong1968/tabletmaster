package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.*;

import java.util.List;

/**
 * @ClassName MyTaskService
 * @Description 我的任务服务
 * @Author wujian
 * @Date 2022/5/26 10:51
 * @Version 1.0
 **/
public interface MyTaskService {
    /**
     * @Description 查询我的发布分页表格
     * @Author wujian
     * @Date 10:52 2022/5/26
     * @Params [task, table]
     * @Return
     **/
    public DataTableModel<MyTask> queryMyPublishForTable(MyTask task, DataTableModel<MyTask> table);
    /**
     * @Description 新增任务
     * @Author wujian
     * @Date 16:09 2022/5/26
     * @Params [myTask, users, owner]
     * @Return
     **/
    public int addTask(MyTask myTask, int[] users, User owner);
    /**
     * @Description 查询指定用户正在执行中的前5个任务
     * @Author wujian
     * @Date 17:06 2022/5/26
     * @Params [user]
     * @Return
     **/
    public List<MyTask> queryTop5Task(User user);
    /**
     * @Description 根据ID查询任务
     * @Author wujian
     * @Date 9:36 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    public MyTask queryTaskById(int taskId);
    /**
     * @Description 编辑任务提交
     * @Author wujian
     * @Date 10:47 2022/5/27
     * @Params [myTask, users]
     * @Return
     **/
    public int editTask(MyTask myTask, int[] users);
    /**
     * @Description 删除任务
     * @Author wujian
     * @Date 11:42 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    public int delTask(int taskId);
    /**
     * @Description 终止任务
     * @Author wujian
     * @Date 11:42 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    public int stopTask(int taskId);
    /**
     * @Description 查询我的任务分页表格
     * @Author wujian
     * @Date 10:52 2022/5/26
     * @Params [task, table]
     * @Return
     **/
    public DataTableModel<MyTask> queryMyTaskForTable(MyTask task,User u, DataTableModel<MyTask> table);
    /**
     * @Description 查询群任务分页表格
     * @Author wujian
     * @Date 15:18 2022/5/27
     * @Params [task, u, table]
     * @Return
     **/
    public DataTableModel<MyTask> queryGroupTaskForTable(MyTask task,User u, DataTableModel<MyTask> table);
    /**
     * @Description 根据任务查询任务接收者
     * @Author wujian
     * @Date 16:40 2022/5/27
     * @Params [taskId]
     * @Return
     **/
    public List<User> queryReciverByTaskId(int taskId);
    /**
     * @Description 查询任务统计信息
     * @Author wujian
     * @Date 10:16 2022/5/30
     * @Params [taskId]
     * @Return
     **/
    public BaseStatistics taskStatistics(int taskId);
    /**
     * @Description 查询指定状态的任务
     * @Author wujian
     * @Date 10:14 2022/5/31
     * @Params [isUse, statu]
     * @Return
     **/
    public List<MyTask> queryByIsUseIsAndStatuNot(int isUse,int statu);
    /**
     * @Description 修改指定任务的状态
     * @Author wujian
     * @Date 10:26 2022/5/31
     * @Params [isUse, statu]
     * @Return
     **/
    public int changeTaskStatu(int taskId,int isUse,int statu);
}
