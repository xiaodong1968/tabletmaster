package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TaskStatistics
 * @Description 任务统计模型
 * @Author wujian
 * @Date 2022/5/30 10:07
 * @Version 1.0
 **/
@Data
public class TaskStatistics {
    private int userId;
    private String userName;
    private int taskId;
    private List<MyFormItem> itmes=new ArrayList<MyFormItem>();
    private List<String> itemNames=new  ArrayList<String>();
}
