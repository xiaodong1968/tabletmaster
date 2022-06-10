package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.*;

import java.util.List;

/**
 * @ClassName MyFormService
 * @Description 表单服务
 * @Author wujian
 * @Date 2022/5/25 8:59
 * @Version 1.0
 **/
public interface MyFormService {
    /**
     * @Description 表单工厂创建表单提交
     * @Author wujian
     * @Date 9:00 2022/5/25
     * @Params [itmes]
     * @Return
     **/
    public int createFormTemplate(List<MyFormItem> itmes, User owner, MyForm myForm);
    /**
     * @Description 查询我的表单分页表格
     * @Author wujian
     * @Date 14:34 2022/5/25
     * @Params [form, table]
     * @Return
     **/
    public DataTableModel<MyForm> queryMyFormForTable(MyForm form, DataTableModel<MyForm> table);
    /**
     * @Description 查看表单
     * @Author wujian
     * @Date 14:55 2022/5/25
     * @Params [formId, isUse]
     * @Return
     **/
    public MyForm queryMyFormByFormId(int formId,int isUse);
    /**
     * @Description 删除我的表单
     * @Author wujian
     * @Date 17:39 2022/5/25
     * @Params [formId, isUse]
     * @Return
     **/
    public int delMyForm(int formId,int isUse);
    /**
     * @Description 获取指定用户指定类型的表单
     * @Author wujian
     * @Date 13:46 2022/5/26
     * @Params [ownerId, formType]
     * @Return
     **/
    public List<MyForm> queryFormByOwnerAndFormType(int ownerId,int formType);
    /**
     * @Description 获取指定类型的表单
     * @Author wujian
     * @Date 9:17 2022/5/27
     * @Params [formType]
     * @Return
     **/
    public List<MyForm> queryFormByFormType(int formType);
    /**
     * @Description 查找指定任务的指定用户的 用户表表单
     * @Author wujian
     * @Date 15:55 2022/5/30
     * @Params [ownerId, taskId, isUse, type]
     * @Return
     **/
    public MyForm queryByOwnerIdIsAndMyTaskIdAndIsUseIsAndTypeIs(int ownerId,int taskId,int isUse,int type);
    /**
     * @Description 查询指定ID的表单明细
     * @Author wujian
     * @Date 15:32 2022/5/31
     * @Params [id]
     * @Return
     **/
    public MyFormItem queryMyFormItemById(int id);
    /**
     * @Description  修改表格明细的值
     * @Author wujian
     * @Date 10:38 2022/6/9
     * @Params [itemId, value, modify]
     * @Return
     **/
    public int modifyItemValue(int itemId,int value,User modify);
}
