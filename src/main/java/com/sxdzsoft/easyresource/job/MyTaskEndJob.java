package com.sxdzsoft.easyresource.job;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.MyDirMapper;
import com.sxdzsoft.easyresource.mapper.MyFormItemMapper;
import com.sxdzsoft.easyresource.mapper.MyFormMapper;
import com.sxdzsoft.easyresource.mapper.MyTaskMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName MyTaskJob
 * @Description 我的任务结束
 * @Author wujian
 * @Date 2022/5/30 13:35
 * @Version 1.0
 **/
public class MyTaskEndJob implements Job {
    @Autowired
    private MyTaskMapper myTaskMapper;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int taskId=(int) jobExecutionContext.getJobDetail().getJobDataMap().get("taskId");
        MyTask task = this.myTaskMapper.getById(taskId);
        task.setStatu(1);//修改任务状态为已完成
        this.myTaskMapper.save(task);

    }
}
