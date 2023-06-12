package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 10:54
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: CampusNews
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_campusnewscclazz_db")
@Data
@NoArgsConstructor
public class CampusNewsClazz {
    @Id
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID

    @Column(nullable = false)
    private String title;//标题名称

    @Column
    private String details;//新闻详情

    //时间
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date time;

    //图片
    @OneToOne
    private MyFile imageAddress;

    @Column
    private Integer clazzId;//新闻详情
}
