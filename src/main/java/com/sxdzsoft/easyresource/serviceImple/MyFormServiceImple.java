package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.MyFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MyFormServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/25 9:00
 * @Version 1.0
 **/
@Service
public class MyFormServiceImple implements MyFormService {
    @Autowired
    private MyFormMapper myFormMapper;
    @Autowired
    private MyFormItemMapper myFormItemMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public int createFormTemplate(List<MyFormItem> itmes, User owner, MyForm myForm) {
        User u=this.userMapper.getById(owner.getId());
        MyForm newOne=new MyForm();
        newOne.setOwner(u);
        newOne.setIsUse(1);
        newOne.setType(myForm.getType());
        newOne.setName(myForm.getName());
        newOne.setRows(myForm.getRows());
        newOne.setCols(myForm.getCols());
        newOne.setTemplateId(0);
        newOne=this.myFormMapper.save(newOne);
        for(MyFormItem item:itmes){
            item.setIsUse(1);
            item.setStatu(0);
            item.setItemId(-1);
            item.setMyForm(newOne);
            this.myFormItemMapper.save(item);
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public DataTableModel<MyForm> queryMyFormForTable(MyForm form, DataTableModel<MyForm> table) {
        Page<MyForm> forms=this.myFormMapper.findAll(new MyFormSpecification(form), PageRequest.of(table.getStart()/table.getLength(), table.getLength()));
        DataTableModel<MyForm> result=new DataTableModel<MyForm>();
        result.setData(forms.getContent());
        result.setRecordsFiltered(Long.valueOf(forms.getTotalElements()).intValue());
        result.setRecordsTotal(forms.getNumberOfElements());
        return result;
    }

    @Override
    public MyForm queryMyFormByFormId(int formId, int isUse) {
        return  this.myFormMapper.queryByIdIsAndIsUseIs(formId,isUse);

    }

    @Override
    @Transactional
    public int delMyForm(int formId, int isUse) {
        MyForm form=this.myFormMapper.getById(formId);
        form.setIsUse(isUse);
        this.myFormMapper.save(form);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<MyForm> queryFormByOwnerAndFormType(int ownerId, int formType) {
        return this.myFormMapper.queryByOwnerIdIsAndIsUseIsAndTypeIs(ownerId,1,formType);
    }

    @Override
    public List<MyForm> queryFormByFormType(int formType) {
        return this.myFormMapper.queryByIsUseIsAndTypeIs(1,formType);
    }

    @Override
    public MyForm queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(int ownerId, int taskId, int isUse, int type) {
        return this.myFormMapper.queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(ownerId,taskId,isUse,type);
    }

    @Override
    public MyFormItem queryMyFormItemById(int id) {
        return this.myFormItemMapper.getById(id);
    }

    @Override
    @Transactional
    public int modifyItemValue(int itemId, int value, User modify) {
        MyFormItem item=this.myFormItemMapper.getById(itemId);
        int formId=item.getMyForm().getId();
        int orgValue=item.getItemValue();
        item.setItemValue(value);
        item.setLastModify(modify);
        item.setLastModifyTime(new Date());
        this.myFormItemMapper.save(item);
        //如果是接收者输入值更改
        if(item.getType()==2){
           MyFormItem countItem=this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(formId,4);
           int newValue=countItem.getItemValue()-orgValue+value;
            countItem.setItemValue(newValue);
            this.myFormItemMapper.save(countItem);
        }
        //如果是审核者输入值更改
        if(item.getType()==3){
            MyFormItem countItem=this.myFormItemMapper.queryByMyFormIdIsAndTypeIs(formId,5);
            int newValue=countItem.getItemValue()-orgValue+value;
            countItem.setItemValue(newValue);
            this.myFormItemMapper.save(countItem);
        }

        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<MyFormItem> renderMyForm(MyForm myForm) {
        MyForm templateForm=myForm.getMyTask().getForm();
        List<MyFormItem> templateItems=templateForm.getItmes();
        List<MyFormItem>  userItems=myForm.getItmes();
        for(MyFormItem templateItem:templateItems){
            for(MyFormItem userItem:userItems){
                if(userItem.getItemId()==templateItem.getId().intValue()){
                    if(templateItem.getType()==3){
                        User u=userItem.getLastModify();
                        if(u!=null){
                            templateItem.setLastModifyUserName(u.getRealname());
                            SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                            templateItem.setLastModifyTimeStr(format.format(userItem.getLastModifyTime()));
                        }
                    }
                    templateItem.setTotalFiles(userItem.getTotalFiles());
                    templateItem.setId(userItem.getId());
                    templateItem.setStatu(userItem.getStatu());
                    templateItem.setItemValue(userItem.getItemValue());
                }
            }
        }
        Collections.sort(templateItems);
        return templateItems;
    }

    @Override
    public MyFormItem queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(int type, int formId, int row) {
        return this.myFormItemMapper.queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(type,formId,row);
    }
}
