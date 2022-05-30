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
 * @Description 我的任务开始
 * @Author wujian
 * @Date 2022/5/30 13:35
 * @Version 1.0
 **/
public class MyTaskStartJob implements Job {
    @Autowired
    private MyTaskMapper myTaskMapper;
    @Autowired
    private MyDirMapper myDirMapper;
    @Autowired
    private MyFormMapper myFormMapper;
    @Autowired
    private MyFormItemMapper myFormItemMapper;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int taskId=(int) jobExecutionContext.getJobDetail().getJobDataMap().get("taskId");
        MyTask task = this.myTaskMapper.getById(taskId);
        task.setStatu(2);//修改任务状态未进行中
        this.myTaskMapper.save(task);
        List<User> recivers = task.getReciver();//获取任务接收者
        MyForm formTemplate = task.getForm();
        List<MyFormItem> items = this.myFormItemMapper.queryByMyFormIdIsAndIsUseIs(formTemplate.getId(),1);
        for (User u : recivers) {
            MyDir rootDir=this.myDirMapper.queryByOwnerIdIsAndIsUseIsAndRootDirIs(u.getId(),1,true);
            MyForm newOne = new MyForm();
            newOne.setIsUse(1);
            newOne.setCols(formTemplate.getCols());
            newOne.setMyTask(task);
            newOne.setOwner(u);
            newOne.setType(1);
            newOne.setName(UUID.randomUUID().toString());
            newOne.setRows(formTemplate.getRows());
            List<MyFormItem> temp = new ArrayList<MyFormItem>();
            MyDir myDir = new MyDir();
            myDir.setType(0);//设置目录为个人目录
            myDir.setChild_file_total(0);
            myDir.setChild_total(0);
            myDir.setName(task.getName());
            myDir.setOwner(u);
            myDir.setChild_total(myDir.getChild_total()+1);
            myDir.setParentId(rootDir.getId());
            myDir.setShareToGroup(false);
            myDir.setRootDir(false);
            myDir.setSize(0);
            myDir.setIsUse(1);
            myDir = this.myDirMapper.save(myDir);
            newOne.setStoreDir(myDir);
            this.myFormMapper.save(newOne);
            for (MyFormItem item : items) {
                MyFormItem it = new MyFormItem();
                it.setName(item.getName());
                it.setType(item.getType());
                it.setCol(item.getCol());
                it.setDirName(item.getName());
                it.setIsUse(1);
                it.setMergeLength(item.getMergeLength());
                it.setMergeModel(item.getMergeModel());
                it.setMount_limit(item.getMount_limit());
                it.setMyForm(newOne);
                // it.setOptions(item.getOptions());
                it.setRow(item.getRow());
                it.setSize_limit(item.getSize_limit());
                it.setStatu(0);
                it.setType_limit(item.getType_limit());
                it.setMyForm(newOne);
                if(it.getType()==1){
                    MyDir myDir2 = new MyDir();
                    myDir2.setType(0);//设置目录为个人目录
                    myDir2.setChild_file_total(0);
                    myDir2.setChild_total(0);
                    myDir2.setName(item.getDirName());
                    myDir2.setOwner(u);
                    myDir2.setParentId(myDir.getId());
                    myDir2.setShareToGroup(false);
                    myDir2.setRootDir(false);
                    myDir2.setSize(0);
                    myDir2.setIsUse(1);
                    myDir.setChild_total(myDir.getChild_total()+1);
                    myDir2 = this.myDirMapper.save(myDir2);
                    it.setDirName(myDir2.getName());
                    it.setStoreDir(myDir2);
                }
                    this.myFormItemMapper.save(it);
            }

        }

    }
}
