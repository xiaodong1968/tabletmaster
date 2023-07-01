package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher;

import java.util.List;
import java.util.Map;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/25 11:00
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: ClazzTeacherService
 * @Description: TODO
 * @Version 1.0
 */
public interface ClazzTeacherService {

    /**
     * @Description: 为班级课程指定教师
     * @data:[clazzId, courseAndTeacher]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/25 11:02
     */
    public int addClazzTeacher(List<ClazzroomCourseTeacher> clazzroomCourseTeachers);


    /**
     * @Description: 查询班级课程对应教师
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher>
     * @Author: YangXiaoDong
     * @Date: 2023/6/25 13:33
     */
    public List<ClazzroomCourseTeacher> getByClazzId(Integer clazzId);
}
