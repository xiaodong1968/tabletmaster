package com.sxdzsoft.easyresource.form;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.ClazzroomCourseTeacher;
import com.sxdzsoft.easyresource.domain.Course;
import lombok.Data;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/26 9:34
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: ClazzShowVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class ClazzShowVo {
    private Clazz clazz;
    private List<ClazzroomCourseTeacher> courseTeachers;
}
