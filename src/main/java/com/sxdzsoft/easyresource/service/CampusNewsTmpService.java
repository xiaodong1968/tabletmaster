package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.CampusNewsTmp;
import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import com.sxdzsoft.easyresource.form.ResultVo;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 14:02
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: queryCampusNewService
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewsTmpService {

    /**
     * @Description: 查询数据-校园新闻从表
     * @data:[campusNews, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/5/15 14:05
     */
    public List<CampusNewsVo2> queryCampusNewsTmp();


    /**
     * @Description: 数据更新
     * @data:[campusNews]
     * @return: com.sxdzsoft.easyresource.form.ResultVo
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:12
     */
    public ResultVo deleteAndinsert(List<CampusNews> campusNews);


    /**
     * @Description: 根据id判断当前数据库是否存在此条数据
     * @data:[CampusNewsTmpId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:16
     */
    public int isContain(int CampusNewsTmpId);
}
