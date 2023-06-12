package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.CoursePresentationService;
import com.sxdzsoft.easyresource.service.CourseService;
import com.sxdzsoft.easyresource.service.MenuService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 11:24
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: CourseHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class CourseHandler {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CoursePresentationService coursePresentationService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private WebSocket webSocket;

    /**
     * @Description: 课程表页面跳转
     * @data:[menuId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/22 11:25
     */
    @GetMapping(path = "clazzSchedule")
    @MenuButton
    public String jumpClazzManage(Integer clazzId, Model model) {
        Clazz clazz = clazzService.queryClazzById(clazzId);
        List<CoursePresentation> alls = clazz.getCoursePresentations();
        List<Course> courseAll = courseService.getCourseAll();
        model.addAttribute("clazzId", clazzId);
        model.addAttribute("clazz", clazz);
        model.addAttribute("alls", alls);
        model.addAttribute("courseAll", courseAll);
        return "pages/course/courseManage";
    }


    /**
     * @Description: 获取课程展示表信息
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CoursePresentation>
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 9:36
     */
    @GetMapping("/getCoursePresentations")
    @ResponseBody
    public List<CoursePresentation> getCoursePresentations(Integer clazzId) {
        List<CoursePresentation> alls = coursePresentationService.getByClazzId(clazzId);
        return alls;
    }

    /**
     * @Description: 课程展示表更新
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/22 16:20
     */
    @PostMapping("/coursePresentationUpadte")
    @ResponseBody
    public int coursePresentationUpadte(@RequestParam(value = "rowData[]") List<String> coursePresentationIds,
                                        @RequestParam(value = "clazzId") Integer clazzId) {
        int i = coursePresentationService.CoursePresentationUpdate(coursePresentationIds, clazzId);
        if (i == 1) {
            Clazz clazz = clazzService.queryClazzById(clazzId);
            webSocket.sendMessage("course", clazz.getClazzName());
        }
        return i;
    }


    /**
     * @Description: 课程表管理页面
     * @data:[model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 13:46
     */
    @GetMapping(path = "courseRegulate")
    @MenuButton
    public String courseRegulate(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/course/courseRegulate";
    }


    /**
     * @Description: 获取课程表展示
     * @data:[course, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.Course>
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 13:58
     */
    @GetMapping("/queryCourseAll")
    @ResponseBody
    public DataTableModel<Course> queryCourseAll(Course course, @RequestParam Integer menuId, DataTableModel<Course> table) {
        DataTableModel<Course> result = this.courseService.queryCourseAll(course, table);
        List<Menu> menus = this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }



    /**
     * @Description: 跳转新增课程页面
     * @data:[model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 14:07
     */
    @GetMapping(path = "/addCourseDialog")
    public String addCourseDialog(Model model) {
        return "pages/course/addCourseDialog";
    }


    /**
     * @Description: 新增课程
     * @data:[course]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/2 14:17
     */
    @PostMapping("/addCourse")
    @ResponseBody
    public int addCourse(Course course) {
        int res = courseService.addCourse(course);
        return res;
    }

    /**
     * @Description: 跳转修改课程页面
     * @data:[courseId]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 15:52
     */
    @GetMapping("/editCouesrDialog")
    public String editCourse(Integer courseId, Model model) {
        Course course = courseService.queryCourseById(courseId);
        model.addAttribute("course", course);
        return "pages/course/editCouesrDialog";
    }


    /**
     * @Description: 修改课程
     * @data:[course]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 16:37
     */
    @PostMapping("/editCourse")
    @ResponseBody
    public int editCourse(Course course) {
        int res = courseService.editCourse(course);
        return res;
    }



    /**
     * @Description: 删除课程
     * @data:[courseId, isUse]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/6 16:57
     */
    @PostMapping("/delCourse")
    @ResponseBody
    public int delCourse(Integer courseId, Integer isUse) {
        int res = courseService.changeCourse(courseId, isUse);
        return res;
    }


    /**
     * @Description: 判断当前课程是否在课程表中展示
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/7 9:04
     */
    @GetMapping("/whereCourseShow")
    @ResponseBody
    public int whereCourseShow(int courseId){
        int res = courseService.whereCourseShow(courseId);
        return res;
    }
}
