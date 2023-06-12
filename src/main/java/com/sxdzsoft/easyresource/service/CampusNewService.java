package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 14:02
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: queryCampusNewService
 * @Description: TODO
 * @Version 1.0
 */
public interface CampusNewService {

    /**
     * @Description: 查询表格数据-校园新闻
     * @data:[campusNews, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/5/15 14:05
     */
    public DataTableModel<CampusNews> queryCampusNewse(CampusNews campusNews, DataTableModel<CampusNews> table);

    /**
     * @Description: 根据id查询新闻
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.CampusNews
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 8:56
     */
    public CampusNews queryById(Integer id);


    /**
     * @Description: 新增校园新闻
     * @data:[campusNews]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 9:41
     */
    public int addCampusNews(CampusNews campusNews);

    /**
     * @Description: 修改校园新闻
     * @data:[campusNews, multipartFiles]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 16:01
     */
    public int editCampusNews(CampusNews campusNews, MultipartFile[] multipartFiles);


    /**
     * @Description: 删除表单-校园新闻
     * @data:[campusNews]
     * @return: com.sxdzsoft.easyresource.domain.CampusNews
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 17:26
     */
    public CampusNews delCampusNews(CampusNews campusNews);


    /**
     * @Description: 获取所有新闻
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/5/18 14:21
     */
    public List<CampusNewsVo2> queryAllNews();
}
