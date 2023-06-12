package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/17 11:23
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: Clazz
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_clazz_db")
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    //主键ID
    private Integer id;

    //班级名称
    @Column(nullable = false)
    private String clazzName;

    //口号
    @Column(nullable = false)
    private String slogan;

    //班主任
    @Column(nullable = false)
    private String clazzTeacher;

    //任课学科
    @Column(nullable = false)
    private String subject;

    //职称
    @Column(nullable = false)
    private String jobTitle;

    //联系方式
    @Column(nullable = false)
    private String tel;

    //班级荣誉
    @OneToMany(mappedBy = "clazz")
    private List<MyFile> honor;

    //班级风采
    @OneToMany(mappedBy = "clazzMien")
    private List<MyFile> mien;

    //语文
    @Column
    private String chinese;
    //数学
    @Column
    private String mathematics;
    //英语
    @Column
    private String english;
    //思想品德
    @Column
    private String moral;
    //美术
    @Column
    private String fineArts;
    //体育
    @Column
    private String sports;
    //科学
    @Column
    private String science;
    //信息技术
    @Column
    private String it;
    //音乐
    @Column
    private String music;

    @Column
    private Integer sortNum;

    @Column
    private Integer image1;
    @Column
    private Integer image2;
    @Column
    private Integer image3;
    @Column
    private Integer image4;
    @Column
    private Integer image5;
    @Column
    private Integer image6;


    @Column
    private Integer mien1;
    @Column
    private Integer mien2;
    @Column
    private Integer mien3;
    @Column
    private Integer mien4;


    @Column
    private int isUse;//删除标志位 0、删除 1、启用  2、禁用


    @OneToOne
    private DutyRoster dutyRoster;

    @OneToMany(mappedBy = "clazz")
    private List<CoursePresentation> coursePresentations;

    @Override
    public String toString() {
        return "Clazz{" +
                "clazzName='" + clazzName + '\'' +
                '}';
    }
}
