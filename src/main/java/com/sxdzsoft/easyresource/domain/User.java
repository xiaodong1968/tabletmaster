package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name="t_user_db")
@Data
public class User implements UserDetails, Serializable, Comparable<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID
    @Column
    private String realname;//真实姓名
    @Column(unique = true,nullable = false)
    private String username;//用户名
    @Column(nullable = false)
    private String password;//用户密码
    @Column
    private String pinyinname;// 拼音名称
    @Column
    private String first;// 拼音首字母
    @Column
    private String tel;// 用户联系方式
    @Column
    private String qq;// qq
    @Column
    private String wechat;// 微信
    @Column
    private String headImg;// 头像
    @Column
    private String email;//用户邮箱
    @Column
    private String sex;//用户性别
    @Column(nullable = false)
    private int isUse;//删除标志位 0、删除 1、启用  2、禁用
    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;//用户角色
    @Transient
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Transient
    private boolean isAdmin;//标记成员是否为群组管理员，不做持久化处理
    @Transient
    private boolean isMember;//表示是否为群组成员，不做持久化处理
    @Column
    private boolean isDiskInit;//个人空间是否被初始化？
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

    @Override
    public int compareTo(User o) {
        return this.getUsername().compareTo(o.getUsername());
    }
}
