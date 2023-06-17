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
@Table(name="t_campusnews_db")
@Data
@NoArgsConstructor
public class CampusNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String title;//标题名称

    @Column(nullable = false)
    private String details;//新闻详情

    //时间
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date time;

    @Column
    private int isUse;//状态

    //图片
    @OneToOne
    private MyFile imageAddress;

    @Transient
    private String startTimeStr;//用于接收前台任务开始时间不做存储

    public CampusNews(Integer id, String title, String details, int isUse) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.isUse = isUse;
    }
}
