package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户加色模型
 */
@Entity
@Table(name="t_roleauthority_db")
@Data
public class RoleAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String code;//权限代码
    @Column(nullable = false)
    private int parentId;//父权限ID
    @Column(nullable = false)
    private int hasChild;//是否拥有子权限
    @Column
    private String remark;//说明
    @Column(nullable = false)
    private int isUse;//删除标志位 0、删除 1、启用  2、禁用
    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private List<Role> roles = new ArrayList<Role>();// 归属角色
}
