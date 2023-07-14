package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 9:23
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: DutyRoster
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name = "t_dutyroster_db")
@Data
public class DutyRoster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    //主键ID
    private Integer id;

    @Column
    private String clazzName;

    //代表今日值日组别
    @Column
    private Integer groupId;

    @OneToMany(mappedBy = "dutyRoster", fetch = FetchType.EAGER)
    private List<DutyRosterOption> dutyRosterOptions;
}
