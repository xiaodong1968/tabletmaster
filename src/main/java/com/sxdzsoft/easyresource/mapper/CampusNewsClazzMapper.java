package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/15 10:04
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: Myform2Mapper
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewsClazzMapper extends JpaRepository<CampusNewsClazz, Integer>, JpaSpecificationExecutor<CampusNewsClazz> {

    /**
     * @Description: 根据班级id查询
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsClazz>
     * @Author: YangXiaoDong
     * @Date: 2023/6/30 16:37
     */
    public List<CampusNewsClazz> queryByClazzId(Integer clazzId);



    /**
     * @Description: 根据新闻id查询
     * @data:[campNewsId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsClazz>
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 18:16
     */
    public List<CampusNewsClazz> queryByCampNewsId(Integer campNewsId);

    /**
     * @Description: 删除班级对应展示新闻
     * @data:'[newsId, clazzId]'
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 14:49
     */
    public CampusNewsClazz queryByCampNewsIdAndClazzId(Integer newsId,Integer clazzId);
}
