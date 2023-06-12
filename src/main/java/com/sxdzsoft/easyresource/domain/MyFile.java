package com.sxdzsoft.easyresource.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private int isUse;//删除标记位置
    @Column
    private long size;//文件大小

    @Column(nullable = false)
    private String store;//存储路径


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "clazz_id", referencedColumnName = "id")
    private Clazz clazz;//当前照片文件从属表单


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "clazzMien_id", referencedColumnName = "id")
    private Clazz clazzMien;//当前照片文件从属表单


    @Override
    public String toString(){
        return this.name;
    }
}
