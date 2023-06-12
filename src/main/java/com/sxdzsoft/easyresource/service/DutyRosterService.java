package com.sxdzsoft.easyresource.service;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:27
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: DutyRosterService
 * @Description: TODO
 * @Version 1.0
 */
public interface DutyRosterService {

    /**
     * @Description: 自动更新班级值日表
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 11:13
     */
    public int updateGruop();


    /**
     * @Description: 手动更新指定组别值日表
     * @data:[clazzId, groupId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 11:15
     */
    public int manualUpdateGroupByClazzId(Integer clazzId,Integer groupId);

}
