package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.WhiteList;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 16:28
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: WhiteListService
 * @Description: TODO
 * @Version 1.0
 */
public interface WhiteListService {

    /**
     * @Description: 变更白名单
     * @data:[whiteList]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 16:33
     */
    public int changewhiteList(WhiteList whiteList);


    /**
     * @Description: 查询白名单
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.WhiteList
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 16:50
     */
    public WhiteList queryWhite();
}
