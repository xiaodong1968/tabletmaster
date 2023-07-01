package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Course;
import com.sxdzsoft.easyresource.domain.CoursePresentation;
import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.CourseMapper;
import com.sxdzsoft.easyresource.mapper.CoursePresentationMapper;
import com.sxdzsoft.easyresource.mapper.CourseSpecification;
import com.sxdzsoft.easyresource.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 14:23
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: CourseServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CourseServiceImple implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CoursePresentationMapper coursePresentationMapper;

    @Override
    public List<Course> getCourseAll() {
        List<Course> all = courseMapper.findAll();
        List<Course> res = new ArrayList<>();
        for (Course course : all) {
            if (course.getIsUse().equals(1)) {
                res.add(course);
            }
        }
        return res;
    }

    @Override
    public DataTableModel<Course> queryCourseAll(Course course, DataTableModel<Course> table) {
        Page<Course> all = this.courseMapper.findAll(new CourseSpecification(course), PageRequest.of(table.getStart() / table.getLength(), table.getLength(), JpaSort.by("id").descending()));
        DataTableModel<Course> result = new DataTableModel();
        result.setData(all.getContent());
        result.setRecordsFiltered(Long.valueOf(all.getTotalElements()).intValue());
        result.setRecordsTotal(all.getNumberOfElements());
        return result;
    }


    @Override
    public int addCourse(Course course) {
        Course singCourse = courseMapper.queryBySubjectAndIsUse(course.getSubject(), 1);
        //判断当前课程是否已经存在
        if (singCourse != null) {
            return HttpResponseRebackCode.SameName;
        }
        Course save = courseMapper.save(course);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public Course queryCourseById(Integer courseId) {
        Course course = courseMapper.getById(courseId);
        return course;
    }

    @Override
    public int editCourse(Course course) {
        Course singCourse = courseMapper.queryBySubjectAndIsUse(course.getSubject(), 1);
        //判断当前课程是否存在
        if (singCourse != null && course.getId().intValue() != singCourse.getId().intValue()) {
            return HttpResponseRebackCode.SameName;
        }
        Course resCourse = courseMapper.getById(course.getId());
        resCourse.setSubject(course.getSubject());
        Course save = courseMapper.save(resCourse);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public Course changeCourse(int CourseId, int isUse) {
        Course course = courseMapper.getById(CourseId);
        course.setIsUse(isUse);
        Course save = courseMapper.save(course);
        return save;
    }

    @Override
    public int whereCourseShow(int courseId) {
        List<CoursePresentation> all = coursePresentationMapper.findAll();
        Set<Integer> courseIds = new HashSet<>();

        for (CoursePresentation coursePresentation : all) {
            List<Course> courseList = Arrays.asList(
                    coursePresentation.getFirstClazz(),
                    coursePresentation.getSecondClazz(),
                    coursePresentation.getThirdClazz(),
                    coursePresentation.getFoutrhClazz(),
                    coursePresentation.getFifthClazz(),
                    coursePresentation.getSixthClazz(),
                    coursePresentation.getLessonClazz()
            );

            for (Course course : courseList) {
                courseIds.add(course.getId());
            }
        }

        if (courseIds.contains(courseId)) {
            return HttpResponseRebackCode.Fail;
        }

        return HttpResponseRebackCode.Ok;
    }

}
