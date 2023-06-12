package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DutyRosterOption;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:27
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: DutyRosterOptionService
 * @Description: TODO
 * @Version 1.0
 */
public interface DutyRosterOptionService {

    /**
     * @Description: 创建值日表
     * @data:[data, clazzId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:11
     */
    public int creatDutyRoster(List<String> data, Integer clazzId);


    /**
     * @Description: 获取今日值日表
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.DutyRosterOption>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:11
     */
    public List<DutyRosterOption> getDutyRosterOption(Integer clazzId);


    /**
     * @Description: 查询班级对应值日组
     * @data:[dutyRosterId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.DutyRosterOption>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:42
     */
    public List<DutyRosterOption> queryByDutyRosterIdAndGruopBy(Integer clazzId);
}
