package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.MyForm;
import com.sxdzsoft.easyresource.domain.MyFormItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 表单明细-选项明细数据服务接口
 * @Author wujian
 * @Date 17:06 2022/5/24
 * @Params
 * @Return
 **/
public interface MyFormItemOptionMapper extends JpaRepository<MyFormItemOption,Integer> {


}
