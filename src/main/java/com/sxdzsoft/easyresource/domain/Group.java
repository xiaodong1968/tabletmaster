package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Group
 * @Description 群组模型
 * @Author wujian
 * @Date 2022/5/13 15:22
 * @Version 1.0
 **/
@Entity
@Table(name="t_group_db")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//群组ID
    @Column(unique = true,nullable = false)
    private String name;//群组名称
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "t_admin_group", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"))
    private List<User> admins=new ArrayList<User>();//群组管理员
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "t_users_group", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users=new ArrayList<User>();//群组成员（不包含管理员）
    @Column
    private String info;//群组描述信息
    @ManyToOne
    @JoinColumn(name="creater_id",referencedColumnName ="id")
    private User creater;//群组创建者
    @Column
    private int total;//群组成员总数
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date createTime;//群组创建时间
    @Column
    private int isUse;//删除标志位
    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private List<MyDir> dirs=new ArrayList<MyDir>();//群组中共享的目录
    @Transient
    private boolean checked;//选中
}
