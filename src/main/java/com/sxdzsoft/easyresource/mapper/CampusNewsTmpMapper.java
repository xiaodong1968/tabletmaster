package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CampusNewsTmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/15 10:04
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: Myform2Mapper
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewsTmpMapper extends JpaRepository<CampusNewsTmp, Integer>, JpaSpecificationExecutor<CampusNewsTmp> {

}
