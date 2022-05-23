package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色模型
 */
@Entity
@Table(name= "t_role_db")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键
    @Column(unique = true,nullable = false)
    private String name;//角色名称
    @Column(nullable = false)
    private String code;//角色代码|别名
    @Column(nullable = false)
    private int isUse;//删除标志位 0、删除 1、启用  2、禁用
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users=new ArrayList<User>();//下属用户
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_authorities", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<RoleAuthority> authorities = new ArrayList<RoleAuthority>();// 下属权限

}
