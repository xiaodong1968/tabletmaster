package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:16
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: DutyRosterOption
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name = "t_dutyrosteroption_db")
@Data
@NoArgsConstructor
public class DutyRosterOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    //主键ID
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer groupId;

    //归属值日表
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "dutyroster_id", referencedColumnName = "id")
    private DutyRoster dutyRoster;

    public DutyRosterOption(String name, Integer groupId, DutyRoster dutyRoster) {
        this.name = name;
        this.groupId = groupId;
        this.dutyRoster = dutyRoster;
    }
}
