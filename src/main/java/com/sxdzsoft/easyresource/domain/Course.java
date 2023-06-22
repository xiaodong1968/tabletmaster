package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 11:09
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: Course
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name = "t_course_db")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    //主键ID
    private Integer id;


    @Column(nullable = false)
    private String subject; //学科

    @Column
    private Integer isUse = 1;

    @ManyToMany
    @JoinTable(
            name = "t_course_teacher",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "clazz_id", referencedColumnName = "id")
    private Clazz clazz;
}
