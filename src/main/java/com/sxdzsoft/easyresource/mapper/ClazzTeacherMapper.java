package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/25 11:05
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: ClazzTeacherMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface ClazzTeacherMapper extends JpaRepository<ClazzroomCourseTeacher, Integer>, JpaSpecificationExecutor<ClazzroomCourseTeacher> {


    /**
     * @Description: 根据班级id查找
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher>
     * @Author: YangXiaoDong
     * @Date: 2023/6/25 13:34
     */
    public List<ClazzroomCourseTeacher> getByClazzId(Integer clazzId);

}
