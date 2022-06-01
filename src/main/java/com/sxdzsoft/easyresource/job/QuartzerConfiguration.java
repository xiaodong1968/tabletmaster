package com.sxdzsoft.easyresource.job;

import com.sxdzsoft.easyresource.domain.MyTask;
import com.sxdzsoft.easyresource.service.MyTaskService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;
import java.util.List;

/**
 * @ClassName QuartzerConfiguration
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/31 10:09
 * @Version 1.0
 **/
@Configuration
public class QuartzerConfiguration implements SmartLifecycle {
    @Autowired
    private SchedulerFactoryBean factoryBean;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private MyQuarterFactory factory;
    @Override
    public void start() {
       List<MyTask> tasks= this.myTaskService.queryByIsUseIsAndStatuNot(1,1);
        Date now=new Date();
       for(MyTask task:tasks){
          try{
               //如果任务尚未开始
               if(task.getStatu()==0){
                   //如果任务尚未到开始时间
                   if(task.getStartTime().after(now)){
                           this.factory.createTaskJob(task.getId());
                           continue;
                   }
                   //如果此时任务已经开始并且任务应该已经结束
                   if(task.getStartTime().before(now)&&task.getEndTime().before(now)){
                        this.myTaskService.changeTaskStatu(task.getId(),1,3);
                        continue;
                   }
                   //如果此时任务已经开始并且任务应该没有结束
                   if(task.getStartTime().before(now)&&task.getEndTime().after(now)){
                       this.factory.createTaskJob(task.getId());
                       continue;
                   }
               }
               //如果任务已经开始
               if(task.getStatu()==2){
                   //如果任务已经结束
                   if(task.getEndTime().before(now)){
                       this.myTaskService.changeTaskStatu(task.getId(),1,1);
                       continue;
                   }
                   //如果任务尚未结束
                   if(task.getEndTime().after(now)){
                       this.factory.modifyTaskEndTime(task.getId(),task.getEndTime());
                       continue;
                   }
               }
         } catch (SchedulerException e) {
            e.printStackTrace();
         }
       }
    }
    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }


}
