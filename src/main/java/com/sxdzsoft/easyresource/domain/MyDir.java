package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyDir
 * @Description 文件目录数据模型
 * @Author wujian
 * @Date 2022/5/19 9:57
 * @Version 1.0
 **/
@Entity
@Table(name="t_mydir_db")
@Data
@Proxy(lazy = false)
public class MyDir implements BaseFile{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键ID
    @Column(nullable = false)
    private String name;//目录名称
    @Column(nullable = false)
    private int type;//目录类型 0个人目录 1群组目录
    @Column(nullable = false)
    private int parentId;//父目录ID
    @Column
    private int child_total;//子目录个数
    @Column
    private int child_file_total;//包含文件个数
    @Column(nullable = false)
    private int isUse;//删除标志位
    @Column
    private long size;//目录大小
    @Column(nullable = false)
    private boolean rootDir;//是否为顶级目录
    @ManyToOne
    @JoinColumn(name="owner_id")
    @JsonIgnore
    private User owner;//目录拥有者（如果type=0）
    @ManyToOne
    @JoinColumn(name="gowner_id")
    @JsonIgnore
    private Group gowner;//归属群组（如果type=1）
    @Column
    private boolean  shareToGroup;//是否共享到群组
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "t_dir_groups", joinColumns = @JoinColumn(name = "dir_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> groups=new ArrayList<Group>();//分享到的群组
    @Override
    public String toString(){
        return this.name;
    }
}
