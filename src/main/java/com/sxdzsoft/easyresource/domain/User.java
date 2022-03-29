package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 用户模型
 */
@Entity
@Table(name="t_user_db")
@Data
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID
    @Column(unique = true)
    private String realname;//真实姓名
    @Column(unique = true,nullable = false)
    private String username;//用户名
    @Column(unique = true,nullable = false)
    private String password;//用户密码
    @Column(unique = true)
    private String pinyinname;// 拼音名称
    @Column(unique = true)
    private String first;// 拼音首字母
    @Column(unique = true)
    private String tel;// 用户联系方式
    @Column(unique = true)
    private String qq;// qq
    @Column(unique = true)
    private String wechat;// 微信
    @Column(unique = true)
    private String headImg;// 头像
    @Column(unique = true)
    private String email;//用户邮箱
    @Column(unique = true)
    private String sex;//用户性别
    @Column(unique = true,nullable = false)
    private int isUse;//删除标志位 0、删除 1、启用  2、禁用
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;//用户角色
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
