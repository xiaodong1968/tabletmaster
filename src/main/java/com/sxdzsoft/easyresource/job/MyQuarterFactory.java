package com.sxdzsoft.easyresource.job;

import com.sxdzsoft.easyresource.domain.MyTask;
import com.sxdzsoft.easyresource.mapper.MyTaskMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName MyQuarterFactory
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/30 11:44
 * @Version 1.0
 **/
@Service
public class MyQuarterFactory {
    @Autowired
    private SchedulerFactoryBean factory;
    @Autowired
    private MyTaskMapper myTaskMapper;
    /**
     * @Description 创建表单任务
     * @Author wujian
     * @Date 13:32 2022/5/30
     * @Params []
     * @Return
     **/
    public void createTaskJob(int taskId) throws SchedulerException {
        MyTask myTask=this.myTaskMapper.getById(taskId);
        JobDetail jobDetailStart=JobBuilder.newJob(MyTaskStartJob.class).withIdentity("MYTASKSTART"+taskId).build();
        JobDetail jobDetailEnd=JobBuilder.newJob(MyTaskEndJob.class).withIdentity("MYTASKEND"+taskId).build();
        jobDetailStart.getJobDataMap().put("taskId",taskId);
        jobDetailEnd.getJobDataMap().put("taskId",taskId);
        SimpleScheduleBuilder budiler=SimpleScheduleBuilder.simpleSchedule();
        Date startTime=myTask.getStartTime();
        Date endTime=myTask.getEndTime();
        Trigger triggerStart=TriggerBuilder.newTrigger().withIdentity("MYTASKSTART"+taskId).withSchedule(budiler).startAt(startTime).build();
        Trigger triggerEnd=TriggerBuilder.newTrigger().withIdentity("MYTASKEND"+taskId).withSchedule(budiler).startAt(endTime).build();
        Scheduler scheduler=this.factory.getScheduler();
        scheduler.scheduleJob(jobDetailStart,triggerStart);
        scheduler.scheduleJob(jobDetailEnd,triggerEnd);
    }
    /**
     * @Description 删除表单任务
     * @Author wujian
     * @Date 17:38 2022/5/30
     * @Params [taskId]
     * @Return
     **/
    public void deleteTask(int taskId) throws SchedulerException {
            Scheduler scheduler=this.factory.getScheduler();
            scheduler.deleteJob(new JobKey("MYTASKSTART"+taskId));
            scheduler.deleteJob(new JobKey("MYTASKEND"+taskId));
            scheduler.getJobDetail(new JobKey("MYTASKSTART"+taskId));
            scheduler.getJobDetail(new JobKey("MYTASKEND"+taskId));
    }
    /**
     * @Description 修改任务终止时间
     * @Author wujian
     * @Date 17:47 2022/5/30
     * @Params [taskId]
     * @Return
     **/
    public void modifyTaskEndTime(int taskId,Date newEndTime) throws SchedulerException {
            deleteTask(taskId);
            Scheduler scheduler=this.factory.getScheduler();
            JobDetail jobDetailEnd=JobBuilder.newJob(MyTaskEndJob.class).withIdentity("MYTASKEND"+taskId).build();
            jobDetailEnd.getJobDataMap().put("taskId",taskId);
            SimpleScheduleBuilder budiler=SimpleScheduleBuilder.simpleSchedule();
            Trigger triggerEnd=TriggerBuilder.newTrigger().withIdentity("MYTASKEND"+taskId).withSchedule(budiler).startAt(newEndTime).build();
            scheduler.scheduleJob(jobDetailEnd,triggerEnd);
    }
}
