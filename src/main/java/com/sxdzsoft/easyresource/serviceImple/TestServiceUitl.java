package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.MyDirMapper;
import com.sxdzsoft.easyresource.mapper.MyFormItemMapper;
import com.sxdzsoft.easyresource.mapper.MyFormMapper;
import com.sxdzsoft.easyresource.mapper.MyTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName TestServiceUitl
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/30 14:42
 * @Version 1.0
 **/
@Service
public class TestServiceUitl {
    @Autowired
    private MyTaskMapper myTaskMapper;
    @Autowired
    private MyFormMapper myFormMapper;
    @Autowired
    private MyDirMapper myDirMapper;
    @Autowired
    private MyFormItemMapper myFormItemMapper;
    @Transactional
    public int createJob(int taskId) {
        MyTask task = this.myTaskMapper.getById(taskId);
        task.setStatu(2);//修改任务状态未进行中
        List<User> recivers = task.getReciver();//获取任务接收者
        MyForm formTemplate = task.getForm();
        for (User u : recivers) {
            MyDir rootDir=this.myDirMapper.queryByOwnerIdIsAndIsUseIsAndRootDirIs(u.getId(),1,true);
            List<MyFormItem> items = formTemplate.getItmes();
            List<MyFormItem> newList= new ArrayList<MyFormItem>();
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
                it=this.myFormItemMapper.save(it);
                // myFormItemMapper.save(it);
                newList.add(it);
            }
            newOne.setStoreDir(myDir);
            newOne.setItmes(newList);
            this.myFormMapper.save(newOne);
        }
        return 0;
    }
}
