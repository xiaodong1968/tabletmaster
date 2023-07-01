package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.SchoolNoticeClazz;

/**
 * @Author YangXiaoDong
 * @Date 2023/7/1 21:48
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: SchoolNoticeClazzService
 * @Description: TODO
 * @Version 1.0
 */
public interface SchoolNoticeClazzService {

    /**
     * @Description: 变更通知
     * @data:[schoolNoticeClazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 22:11
     */
    public int changeNoticeClazz(SchoolNoticeClazz schoolNoticeClazz);
}
