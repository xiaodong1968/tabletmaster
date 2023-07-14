package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 14:02
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: queryCampusNewService
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewsClazzDisableService {

    /**
     * @Description: 新增记录
     * @data:[campusNewsClazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 18:09
     */
    public int changeNewsClazz(CampusNewsClazzDisable campusNewsClazzDisable);


    /**
     * @Description: 根据班级id查询
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsClazz>
     * @Author: YangXiaoDong
     * @Date: 2023/6/30 16:37
     */
    public List<CampusNewsClazzDisable> queryByClazzId(Integer clazzId);



    /**
     * @Description: 根据新闻id和班级id查询
     * @data:[clazzId, mewsId]
     * @return: com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 19:26
     */
    public CampusNewsClazzDisable queryByCampNewsIdAndClazzId(Integer clazzId,Integer mewsId);

}
