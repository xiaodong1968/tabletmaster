package com.sxdzsoft.easyresource.mapper;


import com.sxdzsoft.easyresource.echarts.EchartsPieBorderRadiusInterface;
import com.sxdzsoft.easyresource.log.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @ClassName LoginLogMapper
 * @Description 登录日志数据接口
 * @Author wujian
 * @Date 2023/5/31 19:56
 * @Version 1.0
 **/
public interface LoginLogMapper extends JpaRepository<LoginLog, Integer>, JpaSpecificationExecutor<LoginLog> {
    /**
     * 统计日志数量
     * @return
     */
    public long countBy();

    /**
     * 统计指定查询范围内的日志总量
     * @param startTime
     * @param endTime
     * @return
     */
    public long countByTimeBetween(Date startTime, Date endTime);

    /**
     * 统计不同日志级别的日志数量
     * @return
     */
    @Query(value="select count(*) as value,level_string as name from t_loginLog_db group by level_string",nativeQuery=true)
    public List<EchartsPieBorderRadiusInterface> countAmountByLogLevel();
}
