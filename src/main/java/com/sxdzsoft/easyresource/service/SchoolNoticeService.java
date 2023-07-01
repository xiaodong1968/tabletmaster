package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.SchoolNotice;

import java.util.List;

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
     * @Description: 获取校园通知表单数据
     * @data:[schoolNotice, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.SchoolNotice>
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 8:40
     */
    public DataTableModel<SchoolNotice> querySchoolNoticeTable(SchoolNotice schoolNotice, DataTableModel<SchoolNotice> table);


    /**
     * @Description: 新增校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:44
     */
    public int addSchoolNotice(SchoolNotice schoolNotice);

    /**
     * @Description: 修改校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:44
     */
    public int editSchoolNotice(SchoolNotice schoolNotice);


    /**
     * @Description: 根据id查询
     * @data:[schoolNoticeId]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNotice
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 9:21
     */
    public SchoolNotice querySchoolNoticeById(Integer schoolNoticeId);


    /**
     * @Description: 查询所有的通知
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.SchoolNotice>
     * @Author: YangXiaoDong
     * @Date: 2023/6/16 10:31
     */
    public List<SchoolNotice> queryAllNotice();

    /**
     * @Description: 删除校园通知
     * @data:[schoolNoticeId, isUse]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/17 13:23
     */
    public SchoolNotice delSchoolNoticeById(Integer schoolNoticeId,Integer isUse);

    /**
     * @Description: 获取最近一条校园新闻
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNotice
     * @Author: YangXiaoDong
     * @Date: 2023/6/27 17:09
     */
    public SchoolNotice getNoticeFirst(Integer clazzId);
}
