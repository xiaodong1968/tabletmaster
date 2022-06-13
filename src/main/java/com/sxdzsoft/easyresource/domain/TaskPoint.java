package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TaskPoint
 * @Description 任务评分
 * @Author wujian
 * @Date 2022/6/10 16:19
 * @Version 1.0
 **/
@Data
public class TaskPoint {
    private String owner;//评分对象
    private int length;//评分项数量
    private List<TaskPointRecord> records=new ArrayList<TaskPointRecord>();
}
