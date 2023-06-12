package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CoursePresentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 13:56
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: CourseMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface CoursePresentationMapper extends JpaRepository<CoursePresentation,Integer>, JpaSpecificationExecutor<CoursePresentation> {

    /**
     * @Description: 查询班级对应课程表
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CoursePresentation>
     * @Author: YangXiaoDong
     * @Date: 2023/6/1 8:36
     */
    public List<CoursePresentation> queryByClazzId(Integer clazzId);
}
