package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 15:56
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: SchoolNotice
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_schoolNotice_db")
@Data
public class SchoolNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID

    @Column
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String content;

    @Column
    private Integer isUse=1;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date createDate;


    //接收String类型时间，不做存储
    @Transient
    private String tmpTime;
}
