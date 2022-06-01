package com.sxdzsoft.easyresource.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import org.hibernate.annotations.Proxy;

/**
 * @ClassName MyTask
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/26 10:17
 * @Version 1.0
 **/
@Entity
@Table(name="t_mytask_db")
@Data
@Proxy(lazy = false)
public class MyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//
    @Column(nullable = false,unique = true)
    private String name;//任务名称
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date startTime;//任务开始日期
    @Transient
    private String startTimeStr;//用于接收前台任务开始时间不做存储
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date endTime;//任务开始日期
    @Transient
    private String endTimeStr;//用于接收前台任务结束时间不做存储
    @Column(nullable = false)
    private int isUse;//删除标记
    @ManyToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private User owner;//任务创建者
    @Transient
    private String ownerName;
    @Column
    private int groupId;//发布群组
    @Column
    private int allMember;//是否整组接收 0否 1是
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "t_tasks_user", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> reciver=new ArrayList<User>();//任务接收者
    @ManyToOne
    @JoinColumn(name="form_id",referencedColumnName = "id")
    @JsonIgnore
    private MyForm form;//任务表单模板
    @Column(nullable = false)
    private int statu;//任务状态 0未开始 1已结束 2进行中 3意外终止
    @Override
    public String toString(){
        return this.name;
    }

}
