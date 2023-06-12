package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.MyFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sxdzsoft.easyresource.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Description 我的文件数据接口
 * @Author wujian
 * @Date 15:28 2022/5/31
 * @Params
 * @Return
 **/
public interface MyFileMapper extends JpaRepository<MyFile, Integer>, JpaSpecificationExecutor<MyFile> {



    /**
     * @Description: 通过文件存储的位置信息查询
     * @data:[name]
     * @return: com.sxdzsoft.easyresource.domain.MyFile
     * @Author: YangXiaodong
     * @Date: 2022/8/30 10:56
     */
    public MyFile queryByStore(String name);

    /**
     * @Description: 通过Id查询文件信息
     * @data:[id]
     * @return: com.sxdzsoft.easyresource.domain.MyFile
     * @Author: YangXiaodong
     * @Date: 2022/9/15 11:23
     */
    public MyFile searchById(Integer id);


    /**
     * @Description: 根据班级id查询班级风采
     * @data:[clazzId, isUse, pageable]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 11:15
     */
    public Page<MyFile> queryByClazzMienIdAndIsUse(Integer clazzMineId, int i, Pageable  of);


}
