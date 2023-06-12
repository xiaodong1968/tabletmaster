package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.DutyRoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:25
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: DutyRosterMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface DutyRosterMapper extends JpaRepository<DutyRoster,Integer>, JpaSpecificationExecutor<DutyRoster> {
}
