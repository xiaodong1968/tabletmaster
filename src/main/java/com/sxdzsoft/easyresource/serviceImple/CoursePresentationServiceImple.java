package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.Course;
import com.sxdzsoft.easyresource.domain.CoursePresentation;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.ClazzMapper;
import com.sxdzsoft.easyresource.mapper.CourseMapper;
import com.sxdzsoft.easyresource.mapper.CoursePresentationMapper;
import com.sxdzsoft.easyresource.service.CoursePresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 13:59
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: CoursePresentationServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CoursePresentationServiceImple implements CoursePresentationService {

    @Autowired
    private CoursePresentationMapper coursePresentationMapper;

    @Autowired
    private CourseMapper courseMapper;


    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<CoursePresentation> getByClazzId(Integer clazzId) {
        List<CoursePresentation> all = coursePresentationMapper.queryByClazzId(clazzId);
        return all;
    }

    @Override
    public int CoursePresentationUpdate(List<String> coursePresentationIds, Integer clazzId) {
        Clazz clazz = clazzMapper.getById(clazzId);
        List<CoursePresentation> coursePresentations = clazz.getCoursePresentations();
        if (coursePresentations.isEmpty()) {
            String[] daysOfWeek = { "周一", "周二", "周三", "周四", "周五" };
            for (int i = 0; i < coursePresentationIds.size(); i++) {
                CoursePresentation coursePresentation = new CoursePresentation();
                if (i < daysOfWeek.length) {
                    coursePresentation.setDay(daysOfWeek[i]);
                }
                String[] split = coursePresentationIds.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    Integer s = Integer.valueOf(split[j]);
                    Course course = courseMapper.getById(s);
                    setCoursePresentationClass(coursePresentation, course, j);
                }
                coursePresentation.setClazz(clazz);
                coursePresentationMapper.save(coursePresentation);
            }
        } else {
            for (int i = 0; i < coursePresentationIds.size(); i++) {
                String[] split = coursePresentationIds.get(i).split(",");
                CoursePresentation coursePresentation = coursePresentationMapper.getById(Integer.valueOf(split[0]));
                for (int j = 1; j < split.length; j++) {
                    Integer s = Integer.valueOf(split[j]);
                    Course course = courseMapper.getById(s);
                    setCoursePresentationClass(coursePresentation, course, j - 1);
                }
                coursePresentationMapper.save(coursePresentation);
            }
        }
        return HttpResponseRebackCode.Ok;
    }

    private void setCoursePresentationClass(CoursePresentation coursePresentation, Course course, int index) {
        switch (index) {
            case 0:
                coursePresentation.setFirstClazz(course);
                break;
            case 1:
                coursePresentation.setSecondClazz(course);
                break;
            case 2:
                coursePresentation.setThirdClazz(course);
                break;
            case 3:
                coursePresentation.setFoutrhClazz(course);
                break;
            case 4:
                coursePresentation.setFifthClazz(course);
                break;
            case 5:
                coursePresentation.setSixthClazz(course);
                break;
            case 6:
                coursePresentation.setLessonClazz(course);
                break;
            default:
                break;
        }
    }


}
