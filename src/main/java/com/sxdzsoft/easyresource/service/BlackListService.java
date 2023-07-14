package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.BlackList;
import com.sxdzsoft.easyresource.domain.WhiteList;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 16:28
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: WhiteListService
 * @Description: TODO
 * @Version 1.0
 */
public interface BlackListService {

    /**
     * @Description:变更黑名单
     * @data:[blackList]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 14:13
     */
    public int changeBlackList(BlackList blackList);


    /**
     * @Description: 查询黑名单
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.WhiteList
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 14:14
     */
    public BlackList queryBlack();
}
