package com.sxdzsoft.easyresource.service;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:04
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: SchoolNoticeService
 * @Description: TODO
 * @Version 1.0
 */
public interface SchoolNoticeService {

    /**
     * @Description: 新增校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:44
     */
    public int addSchoolNotice(String content);

    /**
     * @Description: 修改校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:44
     */
    public int schoolNoticeUpdate(String content);
}
