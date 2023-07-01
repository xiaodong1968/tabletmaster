package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher;
import com.sxdzsoft.easyresource.domain.Course;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.ClazzTeacherMapper;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.ClazzTeacherService;
import com.sxdzsoft.easyresource.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/25 11:03
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: ClazzTeacherServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class ClazzTeacherServiceImple implements ClazzTeacherService {

    @Autowired
    private ClazzTeacherMapper clazzTeacherMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClazzService clazzService;


    @Override
    public int addClazzTeacher(List<ClazzroomCourseTeacher> clazzroomCourseTeachers) {

        if (clazzroomCourseTeachers.isEmpty()) {
            return HttpResponseRebackCode.Fail;
        }
        List<ClazzroomCourseTeacher> clazzroomCourseTeachers1 = new ArrayList<>();

        for (ClazzroomCourseTeacher clazzroomCourseTeacher : clazzroomCourseTeachers) {
            List<ClazzroomCourseTeacher> courseTeachers = clazzTeacherMapper.getByClazzId(clazzroomCourseTeacher.getClassId());
            Clazz clazz = clazzService.queryById(clazzroomCourseTeacher.getClassId());
            Course course = courseService.queryCourseById(clazzroomCourseTeacher.getCourseId());
            clazzroomCourseTeacher.setClazz(clazz);
            clazzroomCourseTeacher.setCourse(course);
            clazzroomCourseTeachers1.add(clazzroomCourseTeacher);
            //当前班级暂未分配任课教师
            if (courseTeachers.isEmpty()){
                clazzTeacherMapper.saveAll(clazzroomCourseTeachers1);
            }else {
                clazzTeacherMapper.deleteAll(courseTeachers);
                clazzTeacherMapper.saveAll(clazzroomCourseTeachers1);
            }

        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<ClazzroomCourseTeacher> getByClazzId(Integer clazzId) {
        List<ClazzroomCourseTeacher> courseTeachers = clazzTeacherMapper.getByClazzId(clazzId);
        return courseTeachers;
    }

}
