package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.ClazzTeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/25 15:00
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: ClazzTeacherHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class ClazzTeacherHandler {

    @Autowired
    private ClazzTeacherService clazzTeacherService;

    @Autowired
    private ClazzService clazzService;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    /**
     * @Description: 新增班级对应课程教师
     * @data:[clazzroomCourseTeachers]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/26 8:32
     */
    @PostMapping("/addClazzTeacher")
    @ResponseBody
    public int addClazzTeacher(@RequestBody List<ClazzroomCourseTeacher> clazzroomCourseTeachers, HttpSession session) {
        int res = clazzTeacherService.addClazzTeacher(clazzroomCourseTeachers);
        if (res == 1) {
            ClazzroomCourseTeacher clazzroomCourseTeacher = clazzroomCourseTeachers.get(1);
            if (clazzroomCourseTeacher!=null){
                User user = (User) session.getAttribute("userinfo");
                Clazz clazz = clazzService.queryClazzById(clazzroomCourseTeacher.getClassId());
                log.info(user.getUsername() + "为：" + clazz.getClazzName()+"更新了任课教师");
            }

        }
        return res;

    }
}
