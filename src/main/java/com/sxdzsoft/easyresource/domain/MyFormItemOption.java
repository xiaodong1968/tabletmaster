package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName MyFormItemOption
 * @Description 表单明细-选项明细（针对复选框，单选框，下拉列表）
 * @Author wujian
 * @Date 2022/5/24 17:02
 * @Version 1.0
 **/
@Entity
@Table(name="t_myformitemoption_db")
@Data
public class MyFormItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;//
    @ManyToOne
    @JoinColumn(name="item_id",referencedColumnName = "id")
    private MyFormItem item;
    @Column(nullable = false)
    private int isUse;
    @Override
    public String toString(){
        return this.name;
    }
}
