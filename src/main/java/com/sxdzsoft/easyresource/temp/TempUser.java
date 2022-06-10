package com.sxdzsoft.easyresource.temp;

import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import javax.persistence.*;

/**
 * @ClassName TempUser
 * @Description 临时用户，用于数据导入
 * @Author wujian
 * @Date 2022/6/9 13:30
 * @Version 1.0
 **/
@Data
@Entity
@Table(name="t_temp_user")
public class TempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String group;
}
