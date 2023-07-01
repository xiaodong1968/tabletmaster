package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 14:57
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: CampusNewsTmp
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name = "t_campusnewstmp_db")
@Data
public class CampusNewsTmp {

    @Id
    @Column(unique = true, nullable = false)
    private Integer id;//主键ID

    @Column(nullable = false)
    private String title;//标题名称

    @Column(nullable = false)
    private String details;//新闻详情

    //时间
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date time;

    //图片
    @OneToOne
    private MyFile imageAddress;

    @Override
    public String toString() {
        return "CampusNewsTmp{" +
                "title='" + title + '\'' +
                '}';
    }
}
