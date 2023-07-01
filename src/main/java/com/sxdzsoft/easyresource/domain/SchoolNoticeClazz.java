package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;

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
@Table(name="t_schoolNoticeClazz_db")
@Data
public class SchoolNoticeClazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID

    @Column
    private Integer noticeId;

    @Column
    private Integer clazzId;
}
