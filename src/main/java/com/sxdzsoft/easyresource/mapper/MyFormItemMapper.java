package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.MyForm;
import com.sxdzsoft.easyresource.domain.MyFormItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description 表单明细数据服务接口
 * @Author wujian
 * @Date 17:06 2022/5/24
 * @Params
 * @Return
 **/
public interface MyFormItemMapper extends JpaRepository<MyFormItem,Integer> {
    /**
     * @Description 根据表单ID获取表单明细
     * @Author wujian
     * @Date 18:15 2022/5/30
     * @Params [formId, isUse]
     * @Return
     **/
    public List<MyFormItem> queryByMyFormIdIsAndIsUseIs(int formId, int isUse);
    /**
     * @Description 查询指定表单ID指定类型的表单明细
     * @Author wujian
     * @Date 18:02 2022/6/9
     * @Params [formId, type]
     * @Return
     **/
    public MyFormItem queryByMyFormIdIsAndTypeIs(int formId,int type);
    /**
     * @Description 查询指定表单指定行指定类型的明细
     * @Author wujian
     * @Date 11:20 2022/6/13
     * @Params [type, formId, row]
     * @Return
     **/
    public MyFormItem queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(int type, int formId, int row);
}
