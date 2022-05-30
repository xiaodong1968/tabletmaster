package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Menu
 * @Description 系统菜单模型
 * @Author wujian
 * @Date 2022/4/20 16:29
 * @Version 1.0
 **/
@Entity
@Table(name="t_menu_db")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Integer id;//主键ID
    @Column(nullable = false)
    private String name;//菜单名称
    @Column(nullable = false)
    private int type;//菜单类型 1导航菜单  2栏目内菜单  3数据栏菜单
    @Column(nullable = false)
    private boolean hasChild;//是否拥有子菜单
    @Column(unique = true,nullable = false)
    private String href;//请求路径
    @Column(unique = true,nullable = false)
    private String auUrl;//访问权限
    @Column(nullable = false)
    private int isUse;//删除标志位
    @Column(nullable = false)
    private String icon;//菜单图标
    private int innerOrder;//组内排序
    private int outterOrder;//组外排序
    @Column(nullable = false)
    private Integer parentId;//父菜单ID
    private String func;//菜单函数
    @Override
    public String toString(){
        return this.name;
    }
}
