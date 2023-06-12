package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.SchoolNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:00
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: SchoolNoticeMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface SchoolNoticeMapper extends JpaRepository<SchoolNotice, Integer> {

    /**
     * @Description: 获取最新的校园通知新闻
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNotice
     * @Author: YangXiaoDong
     * @Date: 2023/5/25 8:42
     */
    @Query(value = "SELECT * FROM t_school_notice_db ORDER BY id DESC LIMIT 0,1", nativeQuery = true)
    public SchoolNotice findFirst();

}