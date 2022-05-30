package com.sxdzsoft.easyresource.domain;
import lombok.Data;
import javax.persistence.*;



/**
 * @ClassName MyFile
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/23 17:29
 * @Version 1.0
 **/
@Entity
@Table(name="t_myfile_db")
@Data
public class MyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;//文件名称
    @ManyToOne
    @JoinColumn(name="dir_id",referencedColumnName = "id")
    private MyDir myDir;//归属目录
    @Column(nullable = false)
    private int isUse;//删除标记位置
    @Column
    private long size;//文件大小
    @Column(nullable = false)
    private int type;//文件类型
    @Column(nullable = false)
    private String store;//存储路径
    @ManyToOne
    @JoinColumn(name="owner_id",referencedColumnName = "id")
    private User owner;//创建用户
    @ManyToOne
    @JoinColumn(name="item_id",referencedColumnName = "id")
    private MyFormItem myFormItem;
    @Override
    public String toString(){
        return this.name;
    }
}
