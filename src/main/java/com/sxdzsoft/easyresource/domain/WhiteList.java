package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 15:47
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: WhiteList
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_whitelist_db")
@Data
@Proxy(lazy = false)
public class WhiteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID


    @Column
    private String ipRanges;

}
