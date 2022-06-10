package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyForm
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/24 13:09
 * @Version 1.0
 **/
@Entity
@Table(name="t_myform_db")
@Data
@Proxy(lazy = false)
public class MyForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//主键ID
    @Column(nullable = false)
    private String name;//表单名称
    @ManyToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    @JsonIgnore
    private User owner;//表单创建者|拥有者
    @Column(nullable = false)
    private int isUse;//删除标记位
    @ManyToOne
    @JoinColumn(name="dir_id",referencedColumnName = "id")
    @JsonIgnore
    private MyDir storeDir;//存储目录
    @OneToMany(mappedBy = "myForm")
    @JsonIgnore
    private List<MyFormItem> itmes=new ArrayList<MyFormItem>();//表单明细
    @Column(nullable = false)
    private int type;//表单类型 0模板表单 1用户表单
    @Column(nullable = false)
    private int rows;//行数
    @Column(nullable = false)
    private int cols;//列数
    @ManyToOne
    @JoinColumn(name="task_id",referencedColumnName = "id")
    @JsonIgnore
    private MyTask myTask;//归属任务
    @Override
    public String toString(){
        return this.name;
    }
}
