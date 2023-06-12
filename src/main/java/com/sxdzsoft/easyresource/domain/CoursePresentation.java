package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 11:11
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: CoursePresentation
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_coursePresentation_db")
@Data
public class CoursePresentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    //主键ID
    private Integer id;


    @Column(nullable = false)
    private String day;


    @OneToOne
    private Course firstClazz;

    @OneToOne
    private Course secondClazz;

    @OneToOne
    private Course thirdClazz;

    @OneToOne
    private Course foutrhClazz;

    @OneToOne
    private Course fifthClazz;

    @OneToOne
    private Course sixthClazz;

    @OneToOne
    private Course lessonClazz;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "clazz_id", referencedColumnName = "id")
    private Clazz clazz;//当前课程表从属班级

}
