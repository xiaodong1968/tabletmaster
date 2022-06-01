package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.MyDir;
import com.sxdzsoft.easyresource.domain.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
     * @Description 查询指定文件夹下所有文件
     * @Author wujian
     * @Date 15:57 2022/6/1
     * @Params [dirId]
     * @Return
     **/
    public List<MyFile> queryByMyDirIdIsAndIsUseIs(int dirId,int isUse);
    /**
     * @Description 查询指定表单明细下的所有文件
     * @Author wujian
     * @Date 18:01 2022/6/1
     * @Params [itemId, isUse]
     * @Return
     **/
    public List<MyFile> queryByMyFormItemIdIsAndIsUseIs(int itemId,int isUse);
}
