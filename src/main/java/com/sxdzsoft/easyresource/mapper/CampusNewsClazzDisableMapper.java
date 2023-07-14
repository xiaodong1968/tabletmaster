package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable;
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
public interface CampusNewsClazzDisableMapper extends JpaRepository<CampusNewsClazzDisable, Integer>, JpaSpecificationExecutor<CampusNewsClazzDisable> {

    /**
     * @Description: 根据班级id查询
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable>
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 19:03
     */
    public List<CampusNewsClazzDisable> queryByClazzId(Integer clazzId);


    /**
     * @Description: 根据新闻id和班级id查询
     * @data:[clazzId, mewsId]
     * @return: com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 19:26
     */
    public CampusNewsClazzDisable queryByCampNewsIdAndClazzId(Integer newsId,Integer clazzId);


    /**
     * @Description: 根据新闻id查询
     * @data:[newsId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable>
     * @Author: YangXiaoDong
     * @Date: 2023/7/14 8:48
     */
    public List<CampusNewsClazzDisable> queryByCampNewsId(Integer newsId);

}
