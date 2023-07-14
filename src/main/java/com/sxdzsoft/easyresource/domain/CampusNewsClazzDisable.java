package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 10:54
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: CampusNews
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_campusnewscclazzdisable_db")
@Data
@NoArgsConstructor
public class CampusNewsClazzDisable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID

    @Column
    private Integer campNewsId;

    @Column
    private Integer clazzId;

    @Transient
    private Integer isUse;
}
