package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CampusNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/15 10:04
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: Myform2Mapper
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewsMapper extends JpaRepository<CampusNews, Integer>, JpaSpecificationExecutor<CampusNews> {

    /**
     * @Description: 根据菜单名称查询
     * @data:[name]
     * @return: com.sxdzsoft.easyresource.domain.StudentCareMenu
     * @Author: YangXiaoDong
     * @Date: 2023/3/21 14:16
     */
    public CampusNews queryByTitleAndIsUse(String name,Integer isuse);


    /**
     * @Description: 根据启用状态查询
     * @data:[isuse]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 16:57
     */
    public List<CampusNews> queryByIsUse(Integer isuse);

    @Query(value = "SELECT * FROM t_campusnews_db where is_use = 1 ORDER BY id  DESC LIMIT 0,5;",nativeQuery = true)
    public List<CampusNews> queryByDeviceShow();
}
