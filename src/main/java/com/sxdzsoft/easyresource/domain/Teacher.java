package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/21 16:46
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: Teacher
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_teacher_db")
@Data
@Proxy(lazy = false)
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //教师姓名
    private String name;
}
