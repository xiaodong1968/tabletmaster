package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 14:22
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: CourseMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface CourseMapper extends JpaRepository<Course,Integer>, JpaSpecificationExecutor<Course> {


    /**
     * @Description: 根据课程名称查询课程信息
     * @data:[subject]
     * @return: com.sxdzsoft.easyresource.domain.Course
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 14:21
     */
    public Course queryBySubjectAndIsUse(String subject,Integer isUse);
}
