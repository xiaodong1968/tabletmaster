package com.sxdzsoft.easyresource.service;


import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.echarts.EchartsForLineSmooth;
import com.sxdzsoft.easyresource.echarts.EchartsPieBorderRadius;
import com.sxdzsoft.easyresource.log.LoginLog;
import com.sxdzsoft.easyresource.log.OperationLog;

import java.util.List;

/**
 * @ClassName LogService
 * @Description TODO
 * @Author wujian
 * @Date 2023/5/31 19:56
 * @Version 1.0
 **/
public interface LogService {
    /**
     * 统计指定类型日志数量
     * @param logType
     * @return
     */
    public long queryLogAmount(int logType);

    /**
     * 查询年度指定类型日志的逐月变化趋势折线图数据
     * @param logType
     * @return
     */
    public EchartsForLineSmooth queryLogAmountForYear(int logType);

    /**
     * 查询不同级别的日志数量
     * @return
     */
    public List<EchartsPieBorderRadius> queryLogAmountByLogLevel(int logType);
    /**
     * @Description 查询操作日志分页表格
     * @Author wujian
     * @Date 14:39 2023/2/21
     * @Params [operationLog, table]
     * @Return
     **/
    public DataTableModel<OperationLog> queryOperationLogsForTable(OperationLog operationLog, DataTableModel<OperationLog> table);
    /**
     * @Description 查询登录日志分页表格
     * @Author wujian
     * @Date 14:56 2023/2/21
     * @Params [LoginLog, table]
     * @Return
     **/
    public DataTableModel<LoginLog> queryLoginLogsForTable(LoginLog LoginLog, DataTableModel<LoginLog> table);
    /**
     * @Description 查询指定操作日志
     * @Author wujian
     * @Date 15:30 2023/2/21
     * @Params [id]
     * @Return
     **/
    public OperationLog queryOperationLogById(int id);
    /**
     * @Description 查询指定登录日志
     * @Author wujian
     * @Date 15:31 2023/2/21
     * @Params [id]
     * @Return
     **/
    public LoginLog queryLoginLogById(int id);
    /**
     * @Description 清除指定日志
     * @Author wujian
     * @Date 16:01 2023/2/21
     * @Params [type logId] type: 0操作日志 1登录日志
     * @Return
     **/
    public int clearLog(int type,int logId);
    /**
     * @Description 清除所有日志
     * @Author wujian
     * @Date 16:00 2023/2/21
     * @Params [logType] 0操作日志 1登录日志
     * @Return
     **/
    public int clearAllLog(int logType);
}
