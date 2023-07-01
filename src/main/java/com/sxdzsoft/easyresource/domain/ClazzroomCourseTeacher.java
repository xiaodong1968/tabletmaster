package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/24 14:31
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: ClazzroomCourseTeacher
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name = "t_clazz_course_teacher")
@Data
public class ClazzroomCourseTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    //教师姓名
    @Column
    private String teacher;


    @Transient
    private int courseId;

    @Transient
    private int classId;
}
