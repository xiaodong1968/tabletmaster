package com.sxdzsoft.easyresource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MyFormItem
 * @Description 单元格
 * @Author wujian
 * @Date 2022/5/24 13:12
 * @Version 1.0
 **/
@Entity
@Table(name="t_myformitem_db")
@Data
@Proxy(lazy = false)
public class MyFormItem implements  Comparable<MyFormItem>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;//单元格名称
    @Column
    private int type;//单元格类型 0标题 1上传控件 2接收者输入 3管理员输入 4接收者统计 5管理员输入统计
    @Column
    private String fhMessage;//复核意见 只有type=3时生效
    @Column
    private int itemId;//如果是用户表单中的表单明细，那么该字段记录对应模板表单中，对应表单明细的ID
    @Column
    private float itemValue;//单元格值
    @Column
    private int mount_limit;//数量限制
    @Column
    private int type_limit;//类型限制
    @Column
    private int value_limit;//如果时文本框控件，最大值限制
    @OneToMany(mappedBy = "item")
    private List<MyFormItemOption> options=new ArrayList<MyFormItemOption>();//选项明细（如复选框，单选框，下拉列表）
    @Column
    private long size_limit;//大小限制
    @Column
    private int totalFiles;//如果是上传控件，已上传文件数量
    @Column
    private int isUse;//删除标记位置
    @ManyToOne
    @JoinColumn(name="form_id",referencedColumnName = "id")
    private MyForm myForm;//归属表单
    @ManyToOne
    @JoinColumn(name="dir_id",referencedColumnName = "id")
    private MyDir storeDir;//存储目录
    @Column
    private String dirName;//存储目录名称
    @OneToMany(mappedBy = "myFormItem")
    private List<MyFile> files=new ArrayList<MyFile>();
    @Column
    private int row;//表单中的行号
    @Column
    private int col;//表单中的列号
    @Column
    private int mergeModel;//单元格合并模式 0行合并 1列合并
    @Column
    private int mergeLength;//合并数量
    @Column
    private int statu;//完成状态 0未完成 1已完成 2部分完成
    @ManyToOne
    @JoinColumn(name="lastModify_id")
    @JsonIgnore
    private User lastModify;//单元格值最后修改人
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date lastModifyTime;//最后修改时间
    @Transient
    private String lastModifyUserName;
    @Transient
    private String lastModifyTimeStr;
    @Override
    public String toString(){
        return this.name;
    }
    @Override
    public int compareTo(MyFormItem o) {
        return this.getId().compareTo(o.getId());
    }
}
