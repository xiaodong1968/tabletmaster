package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.MyFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        newOne=this.myFormMapper.save(newOne);
        for(MyFormItem item:itmes){
            item.setIsUse(1);
            item.setStatu(0);
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
}
