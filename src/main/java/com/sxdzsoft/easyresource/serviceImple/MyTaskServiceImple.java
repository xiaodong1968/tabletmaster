package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.job.MyQuarterFactory;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.MyTaskService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyTaskServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/26 10:51
 * @Version 1.0
 **/
@Service
public class MyTaskServiceImple implements MyTaskService {
    @Autowired
    private MyTaskMapper myTaskMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MyFormMapper myFormMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private MyQuarterFactory myQuarterFactory;
    @Autowired
    private MyFormItemMapper myFormItemMapper;
    @Autowired
    private MyFileMapper myFileMapper;
    @Autowired
    private MyDirMapper myDirMapper;
    @Override
    public DataTableModel<MyTask> queryMyPublishForTable(MyTask task, DataTableModel<MyTask> table) {
        Page<MyTask> tasks=this.myTaskMapper.findAll(new MyPublishSpecification(task), PageRequest.of(table.getStart()/table.getLength(), table.getLength(), JpaSort.by("startTime").descending()));
        DataTableModel<MyTask> result=new DataTableModel<MyTask>();
        result.setData(tasks.getContent());
        result.setRecordsFiltered(Long.valueOf(tasks.getTotalElements()).intValue());
        result.setRecordsTotal(tasks.getNumberOfElements());
        return result;
    }

    @Override
    @Transactional
    public int addTask(MyTask myTask, int[] users, User owner)  {
        if(this.myTaskMapper.countByNameIsAndIsUseIs(myTask.getName(),1)>0){
            return HttpResponseRebackCode.SameName;
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<User> recivers=new ArrayList<User>();
        try {
            Date startTime=format.parse(myTask.getStartTimeStr());
            Date endTime= format.parse(myTask.getEndTimeStr());
            Date now=new Date();
            if(startTime.before(now)){
                return HttpResponseRebackCode.InvalidateDate;
            }
            if(startTime.after(endTime)||startTime.getTime()==endTime.getTime()){
                return HttpResponseRebackCode.InvalidateDate;
            }
            myTask.setStartTime(startTime);
            myTask.setEndTime(endTime);
            myTask.setOwner(owner);
            myTask.setStatu(0);
            if(myTask.getAllMember()==0){
                for(int id:users){
                   User u= this.userMapper.getById(id);
                    recivers.add(u);
                }
                myTask.setReciver(recivers);
            }
            if(myTask.getAllMember()==1){
                List<User> us= this.groupMapper.queryByIdIsAndIsUseIs(myTask.getGroupId(),1).getUsers();
                for(User u:us){
                    recivers.add(u);
                }
                myTask.setReciver(recivers);
            }
            myTask.setForm(this.myFormMapper.getById(myTask.getForm().getId()));
            myTask=this.myTaskMapper.save(myTask);
            try {
                myQuarterFactory.createTaskJob(myTask.getId());
                return HttpResponseRebackCode.Ok;
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return HttpResponseRebackCode.Fail;
    }

    @Override
    public List<MyTask> queryTop5Task(User user) {
        return this.myTaskMapper.queryTop5ByReciverContainsAndIsUseIsAndStatuNot(user,1,0,JpaSort.by("statu","startTime").descending());

    }

    @Override
    public MyTask queryTaskById(int taskId) {
        return this.myTaskMapper.getById(taskId);
    }

    @Override
    @Transactional
    public int editTask(MyTask myTask, int[] users) {
        try {
            MyTask task = this.myTaskMapper.getById(myTask.getId());
            List<User> recivers = new ArrayList<User>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = format.parse(myTask.getStartTimeStr());
            Date endTime = format.parse(myTask.getEndTimeStr());
            //如果任务还没有开始
            if (task.getStatu() == 0) {
                //如果任务名称被修改
                if (!myTask.getName().equals(task.getName())) {
                    if (this.myTaskMapper.countByNameIsAndIsUseIsAndIdIsNot(myTask.getName(), 1, task.getId()) > 0) {
                        return HttpResponseRebackCode.SameName;
                    }
                }
                Date now = new Date();
                if (startTime.before(now)) {
                    return HttpResponseRebackCode.InvalidateDate;
                }
                if (startTime.after(endTime) || startTime.getTime() == endTime.getTime()) {
                    return HttpResponseRebackCode.InvalidateDate;
                }
                task.setStartTime(startTime);
                task.setEndTime(endTime);
                task.setName(myTask.getName());
                MyForm form = this.myFormMapper.getById(myTask.getForm().getId());
                task.setForm(form);
                task.setAllMember(myTask.getAllMember());
                if (myTask.getAllMember() == 0) {
                    for (int id : users) {
                        User u = this.userMapper.getById(id);
                        recivers.add(u);
                    }
                }
                if (myTask.getAllMember() == 1) {
                    List<User> us = this.groupMapper.queryByIdIsAndIsUseIs(myTask.getGroupId(), 1).getUsers();
                    for (User u : us) {
                        recivers.add(u);
                    }
                }
                task.setReciver(recivers);

                try {
                    this.myQuarterFactory.deleteTask(task.getId());
                    this.myQuarterFactory.createTaskJob(task.getId());
                    this.myTaskMapper.save(task);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }

            }
            //如果任务已经结束
            if (myTask.getStatu() == 1) {
                return HttpResponseRebackCode.Ok;
            }
            //如果任务正在进行中
            if (myTask.getStatu() == 2) {
                if (startTime.after(endTime) || startTime.getTime() == endTime.getTime()) {
                    return HttpResponseRebackCode.InvalidateDate;
                }
                task.setEndTime(endTime);
                task.setAllMember(myTask.getAllMember());
                if (myTask.getAllMember() == 0) {
                    for (int id : users) {
                        User u = this.userMapper.getById(id);
                        recivers.add(u);
                    }
                }
                if (myTask.getAllMember() == 1) {
                    List<User> us = this.groupMapper.queryByIdIsAndIsUseIs(myTask.getGroupId(), 1).getUsers();
                    for (User u : us) {
                        recivers.add(u);
                    }
                }
                task.setReciver(recivers);

                try {
                    this.myQuarterFactory.modifyTaskEndTime(task.getId(),task.getEndTime());
                    this.myTaskMapper.save(task);
                    return HttpResponseRebackCode.Ok;
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    @Transactional
    public int delTask(int taskId) {
        MyTask task=this.myTaskMapper.getById(taskId);
        task.setIsUse(0);
        this.myTaskMapper.save(task);
        try {
            this.myQuarterFactory.deleteTask(task.getId());
            return HttpResponseRebackCode.Ok;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    @Transactional
    public int stopTask(int taskId) {
        MyTask task=this.myTaskMapper.getById(taskId);
        task.setStatu(1);
        this.myTaskMapper.save(task);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public DataTableModel<MyTask> queryMyTaskForTable(MyTask task, User u, DataTableModel<MyTask> table) {
        List<User> recivers=new ArrayList();
        recivers.add(u);
        task.setReciver(recivers);
        Page<MyTask> tasks=this.myTaskMapper.findAll(new MyTaskSpecification(task), PageRequest.of(table.getStart()/table.getLength(), table.getLength(), JpaSort.by("startTime").descending()));
        DataTableModel<MyTask> result=new DataTableModel<MyTask>();
        result.setData(tasks.getContent());
        result.setRecordsFiltered(Long.valueOf(tasks.getTotalElements()).intValue());
        result.setRecordsTotal(tasks.getNumberOfElements());
        return result;
    }

    @Override
    public DataTableModel<MyTask> queryGroupTaskForTable(MyTask task, User u, DataTableModel<MyTask> table) {
        User currentUser=this.userMapper.getById(u.getId());
        List<Group> groups=currentUser.getGroups();
        List<Group> adminGroups=new ArrayList<Group>();
        //不开启 只能查看当前用户担任群组的群组任务
//        for(Group g:groups){
//           List<User> admins= g.getAdmins();
//           for(User uu:admins){
//               if(uu.getId().intValue()==u.getId().intValue()){
//                   adminGroups.add(g);
//               }
//           }
//        }
        Page<MyTask> tasks=this.myTaskMapper.findAll(new GroupTaskSpecification(task,adminGroups), PageRequest.of(table.getStart()/table.getLength(), table.getLength(), JpaSort.by("startTime").descending()));
        DataTableModel<MyTask> result=new DataTableModel<MyTask>();
        List<MyTask> tasks1=tasks.getContent();
        for(MyTask ta:tasks1){
            ta.setOwnerName(ta.getOwner().getRealname());
            ta.setOwner(null);
        }
        result.setData(tasks.getContent());
        result.setRecordsFiltered(Long.valueOf(tasks.getTotalElements()).intValue());
        result.setRecordsTotal(tasks.getNumberOfElements());
        return result;
    }

    @Override
    public List<User> queryReciverByTaskId(int taskId) {
        return this.myTaskMapper.getById(taskId).getReciver();
    }

    @Override
    public BaseStatistics taskStatistics(int taskId,int groupId,int page,String searchName) {
        BaseStatistics bs=new BaseStatistics();
        MyTask task=this.myTaskMapper.getById(taskId);
        bs.setTaskName(task.getName());
        List<User> recivers=task.getReciver();
        if(groupId!=-1){
            List<User> temp=new ArrayList<User>();
            for(User u:recivers){
               List<Integer> ids=u.getGroups().stream().map(Group::getId).collect(Collectors.toList());
               if(ids.contains(groupId)){
                   if(!searchName.equals("all")){
                       if(u.getRealname().contains(searchName)){
                           temp.add(u);
                       }
                   }else{
                       temp.add(u);
                   }

               }
            }
            recivers=temp;
        }
        int totalPage=(int)Math.ceil((double) recivers.size()/10);
        int totalMount=recivers.size();
        int end;
        if(page*10>totalMount){
            end=totalMount;
        }else{
            end=page*10;
        }
        bs.setPage(page);
        bs.setTotalPage(totalPage);
        recivers=recivers.subList((page-1)*10,end);
        int statu=task.getStatu();
        List<TaskStatistics> tss=new ArrayList<TaskStatistics>();
        List<String> names=new ArrayList<String>();
        //如果任务尚未开始
        if(statu==0){
            MyForm form=task.getForm();
            List<MyFormItem> items=form.getItmes();
            Collections.sort(items);
            for(User u:recivers){
                TaskStatistics ts=new TaskStatistics();
                if(!names.contains("姓名")){
                    names.add("姓名");
                }
                List<MyFormItem>  its=new ArrayList<MyFormItem>();
                for(MyFormItem item:items){
                    if(item.getType()!=1){
                        continue;
                    }
                    MyFormItem it=new MyFormItem();
                    it.setStatu(item.getStatu());
                    it.setId(item.getId());
                    it.setTotalFiles(item.getTotalFiles());
                    it.setType(item.getType());
                    its.add(it);
                    if(!names.contains(item.getDirName())){
                        names.add(item.getDirName());
                    }
                }
               //添加自评，复核单元格
                MyFormItem mf1= this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(form.getId(),4);
                if(mf1!=null){
                    MyFormItem it=new MyFormItem();
                    it.setStatu(-1);
                    it.setId(mf1.getId());
                    it.setTotalFiles(mf1.getTotalFiles());
                    it.setType(mf1.getType());
                    it.setItemValue(mf1.getItemValue());
                    its.add(it);
                    if(!names.contains("自评")){
                        names.add("自评");
                    }
                }
                MyFormItem mf2= this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(form.getId(),5);
                if(mf2!=null){
                    MyFormItem it2=new MyFormItem();
                    it2.setStatu(-1);
                    it2.setId(mf2.getId());
                    it2.setTotalFiles(mf2.getTotalFiles());
                    it2.setType(mf2.getType());
                    it2.setItemValue(mf2.getItemValue());
                    its.add(it2);
                    if(!names.contains("复核")){
                        names.add("复核");
                    }
                }
                //
                ts.setItmes(its);
                ts.setTaskId(taskId);
                ts.setUserId(u.getId());
                ts.setUserName(u.getRealname());
                ts.setItemNames(names);
                tss.add(ts);
            }
        }
        //如果任务已经开始或结束f
        if(task.getStatu()==1||task.getStatu()==2){
            for(User u:recivers){
                MyForm form=this.myFormMapper.queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(u.getId(),task.getId(),1,1);
                List<MyFormItem> items=form.getItmes();
                Collections.sort(items);
                TaskStatistics ts=new TaskStatistics();
                if(!names.contains("姓名")){
                    names.add("姓名");
                }
                List<MyFormItem>  its=new ArrayList<MyFormItem>();
                for(MyFormItem item:items){
                    if(item.getType()!=1){
                        continue;
                    }
                    MyFormItem it=new MyFormItem();
                    it.setStatu(item.getStatu());
                    it.setId(item.getId());
                    it.setTotalFiles(item.getTotalFiles());
                    it.setType(item.getType());
                    its.add(it);
                    if(!names.contains(item.getDirName())){
                        names.add(item.getDirName());
                    }
                }
                //添加自评，复核单元格
                MyFormItem mf1= this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(form.getId(),4);
                if(mf1!=null){
                    MyFormItem it=new MyFormItem();
                    it.setStatu(-1);
                    it.setId(mf1.getId());
                    it.setTotalFiles(mf1.getTotalFiles());
                    it.setType(mf1.getType());
                    it.setItemValue(mf1.getItemValue());
                    its.add(it);
                    if(!names.contains("自评")){
                        names.add("自评");
                    }
                }
                MyFormItem mf2= this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(form.getId(),5);
                if(mf2!=null){
                    MyFormItem it2=new MyFormItem();
                    it2.setStatu(-1);
                    it2.setId(mf2.getId());
                    it2.setTotalFiles(mf2.getTotalFiles());
                    it2.setType(mf2.getType());
                    it2.setItemValue(mf2.getItemValue());
                    its.add(it2);
                    if(!names.contains("复核")){
                        names.add("复核");
                    }
                }
                //
                ts.setItmes(its);
                ts.setTaskId(taskId);
                ts.setUserId(u.getId());
                ts.setUserName(u.getRealname());
                ts.setItemNames(names);
                tss.add(ts);
            }
        }
        bs.setIts(tss);
        bs.setNames(names);
        return bs;
    }

    @Override
    public List<MyTask> queryByIsUseIsAndStatuNot(int isUse, int statu) {
        return this.myTaskMapper.queryByIsUseIsAndStatuNot(isUse,statu);
    }

    @Override
    public int changeTaskStatu(int taskId,int isUse, int statu) {
        MyTask task=this.myTaskMapper.getById(taskId);
        task.setStatu(statu);
        this.myTaskMapper.save(task);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public TaskPoint showFhRecord(int itemId) {
        TaskPoint tp=new TaskPoint();
        MyFormItem item=this.myFormItemMapper.getById(itemId);
        tp.setOwner(item.getMyForm().getOwner().getRealname());
        List<MyFormItem> items= this.myFormItemMapper.queryByMyFormIdIsAndIsUseIs(item.getMyForm().getId(),1);
        List<TaskPointRecord> results=new ArrayList<TaskPointRecord>();
        int count=0;
        TaskPointRecord record=new TaskPointRecord();
        for(MyFormItem it:items){
            if(it.getType()!=1&&it.getType()!=2&&it.getType()!=3){
                continue;
            }
            if(it.getType()==1){
                 record.setName(it.getDirName());
            }
            if(it.getType()==2){
                record.setZp(String.valueOf(it.getItemValue()));
            }
            if(it.getType()==3){
                record.setFh(String.valueOf(it.getItemValue()));
                User lastModify=it.getLastModify();
                if(lastModify!=null){
                    record.setFhuser(it.getLastModify().getRealname());
                    SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    record.setFhtime(format.format(it.getLastModifyTime()));
                }else{
                    record.setFhuser("-");
                    record.setFhtime("-");
                }

            }
            if(it.getType()==4||it.getType()==5){
                break;
            }
            if(count==2){
                results.add(record);
                record=new TaskPointRecord();
                count=0;
            }else{
                count++;
            }
        }
        tp.setRecords(results);
        tp.setLength(results.size());
        return tp;
    }

    @Override
    @javax.transaction.Transactional
    public int clearTaskData(int taskId) {
        MyTask task=this.myTaskMapper.getById(taskId);
        //如果任务还未开始
        if(task.getStatu()==0){
            this.myTaskMapper.delete(task);
        }
        else{
            try {
                this.myQuarterFactory.deleteTask(taskId);
                List<MyForm> forms=this.myFormMapper.queryByMyTaskIdIsAndTypeIs(taskId,1);
                List<User> recivers=task.getReciver();
                for(MyForm form:forms){
                  List<MyFormItem>  items= this.myFormItemMapper.queryByMyFormIdIsAndIsUseIs(form.getId(),1);
                  for(MyFormItem item:items){
                     List<MyFile> files= this.myFileMapper.queryByMyFormItemIdIsAndIsUseIs(item.getId(),1);
                     for(MyFile file:files){
                         File f=new File("d://upload/"+file.getStore());
                         if(f.exists()){
                             f.delete();
                         }
                     }
                     this.myFileMapper.deleteAllInBatch(files);
                     if(item.getStoreDir()!=null){
                         this.myDirMapper.deleteById(item.getStoreDir().getId());
                     }
                  }
                  this.myFormItemMapper.deleteAllInBatch(items);
                }
                this.myFormMapper.deleteAllInBatch(forms);
                task.setForm(null);
                task.setReciver(null);
                this.myTaskMapper.save(task);
                this.myTaskMapper.delete(task);
                return HttpResponseRebackCode.Ok;
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        return HttpResponseRebackCode.Fail;
    }
}
