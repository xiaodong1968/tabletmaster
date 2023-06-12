package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/17 9:34
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: ClazzMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface ClazzMapper extends JpaRepository<Clazz,Integer>, JpaSpecificationExecutor<Clazz> {


    /**
     * @Description: 通过班级名称进行查询
     * @data:[clazzName]
     * @return: com.sxdzsoft.easyresource.domain.Clazz
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:53
     */
    public Clazz queryByClazzNameAndIsUse(String clazzName,Integer isUse);


    /**
     * @Description: 根据班级状态查询班级信息
     * @data:[isUse]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Clazz>
     * @Author: YangXiaoDong
     * @Date: 2023/6/7 13:51
     */
    public List<Clazz> queryAllByIsUse(Integer isUse);
}
