package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.Course;
import com.sxdzsoft.easyresource.domain.DataTableModel;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 14:23
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: CourseService
 * @Description: TODO
 * @Version 1.0
 */
public interface CourseService {

    /**
     * @Description: 获取所有的课程信息
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Course>
     * @Author: YangXiaoDong
     * @Date: 2023/5/22 14:25
     */
    public List<Course> getCourseAll();

    /**
     * @Description: 获取课程表展示
     * @data:[course, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.Course>
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 13:58
     */
    public DataTableModel<Course> queryCourseAll(Course course, DataTableModel<Course> table);

    /**
     * @Description: 新增课程
     * @data:[course]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 14:19
     */
    public int addCourse(Course course);


    /**
     * @Description: 根据id查询课程信息
     * @data:[courseId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 15:55
     */
    public Course queryCourseById(Integer courseId);


    /**
     * @Description: 修改课程
     * @data:[course]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 16:37
     */
    public int editCourse(Course course);


    /**
     * @Description: 更改课程状态(包括删除)
     * @data:[CourseId, isUse]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 17:00
     */
    public int changeCourse(int CourseId, int isUse);

    /**
     * @Description: 判断当前课程是否在课程表中展示
     * @data:[courseId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/7 9:05
     */
    public int whereCourseShow(int courseId);
}
