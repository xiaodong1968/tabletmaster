package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseStatistics
 * @Description 任务执行情况统计
 * @Author wujian
 * @Date 2022/5/30 10:24
 * @Version 1.0
 **/
@Data
public class BaseStatistics {
    private List<String> names=new ArrayList<String>();
    private List<TaskStatistics> its=new ArrayList<TaskStatistics>();
}
