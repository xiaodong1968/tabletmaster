package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.DutyRosterOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:25
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: DutyRosterOption
 * @Description: TODO
 * @Version 1.0
 */
public interface DutyRosterOptionMapper extends JpaRepository<DutyRosterOption,Integer>, JpaSpecificationExecutor<DutyRosterOption> {

    /**
     * @Description: 获取班级当日值日表
     * @data:[dutyRosterId, gruopId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.DutyRosterOption>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:14
     */
    public List<DutyRosterOption> queryByDutyRosterIdAndGroupId(Integer dutyRosterId,Integer gruopId);


    /**
     * @Description: 查询班级对应值日组
     * @data:[dutyRosterId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.DutyRosterOption>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 15:42
     */
    @Query(value = "SELECT * FROM t_dutyrosteroption_db WHERE dutyroster_id = :dutyRosterId GROUP BY group_id",nativeQuery = true)
    public List<DutyRosterOption> queryByDutyRosterIdAndGruopBy(Integer dutyRosterId);
}
