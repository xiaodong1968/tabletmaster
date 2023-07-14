package com.sxdzsoft.easyresource.log;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName OperationLog
 * @Description 登录日志实体类
 * @Author wujian
 * @Date 2023/2/21 14:28
 * @Version 1.0
 **/
@Entity
@Data
@Table(name="t_loginlog_db")
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //日志记录时间
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MyDateFormat.class)
    private Date time;
    private String message;//日志内容
    private String level_string;//日志级别
    private String thread_name;//触发线程
    private String caller_filename;//触发类
    private String caller_class;//触发类
    private String caller_method;//触发方法
    private String caller_line;//触发代码行

}
