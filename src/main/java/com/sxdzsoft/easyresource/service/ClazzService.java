package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.form.ClazzHonorVo;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/17 14:44
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: ClazzService
 * @Description: TODO
 * @Version 1.0
 */
public interface ClazzService {

    /**
     * @Description: 根据id查询班级信息
     * @data:[clazzId]
     * @return: com.sxdzsoft.easyresource.domain.Clazz
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 14:46
     */
    public Clazz queryClazzById(Integer clazzId);



    /**
     * @Description: 查询班级分页表格
     * @data:[clazz, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.Clazz>
     * @Author: YangXiaoDong
     * @Date: 2023/3/10 13:24
     */
    public DataTableModel<Clazz> queryClazzForTable(Clazz clazz, DataTableModel<Clazz> table);


    /**
     * @Description: 根据id查询班级信息
     * @data:[id]
     * @return: com.sxdzsoft.easyresource.domain.Clazz
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:25
     */
    public Clazz queryById(Integer id);


    /**
     * @Description: 新增班级
     * @data:[clazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 16:06
     */
    public int addClazz(Clazz clazz);


    /**
     * @Description: 编辑班级
     * @data:[clazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 16:26
     */
    public int editClazz(Clazz clazz);


    /**
     * @Description:  班级荣誉照片更改
     * @data:[clazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/18 11:24
     */
    public int clazzHonorUpdate(ClazzHonorVo clazzHonorVo);

    /**
     * @Description: 班级荣誉照片删除
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 17:17
     */
    public int clazzHonorDelete(ClazzHonorVo clazzHonorVo);


    /**
     * @Description: 班级风采照片删除
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 9:43
     */
    public int clazzMienDelete(ClazzHonorVo clazzHonorVo);



    /**
     * @Description: 班级风采照片更改
     * @data:[clazzHonorVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/21 21:33
     */
    public int clazzMienUpdate(ClazzHonorVo clazzHonorVo);


    /**
     * @Description: 查询所有正在启用的班级信息
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Clazz>
     * @Author: YangXiaoDong
     * @Date: 2023/6/7 13:49
     */
    public List<Clazz> queryAllClazzAndStar();


    /**
     * @Description: 查询班级新闻
     * @data:[clazzId]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/6/30 22:09
     */
    public DataTableModel<CampusNews> queryClazzNews(CampusNews campusNews,Integer clazzId,DataTableModel<CampusNews> table);

}
