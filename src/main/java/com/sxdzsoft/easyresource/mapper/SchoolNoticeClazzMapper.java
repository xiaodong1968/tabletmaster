package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.SchoolNoticeClazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.nio.channels.Pipe;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:00
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: SchoolNoticeMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface SchoolNoticeClazzMapper extends JpaRepository<SchoolNoticeClazz, Integer>, JpaSpecificationExecutor<SchoolNoticeClazz> {

    /**
     * @Description: 根据班级id和通知id查找
     * @data:[clazzId, noticeId]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNoticeClazz
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 22:14
     */
    public SchoolNoticeClazz queryByClazzId(Integer clazzId);
}
