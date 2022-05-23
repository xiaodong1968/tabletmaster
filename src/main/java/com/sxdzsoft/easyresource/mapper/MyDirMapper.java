package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.MyDir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Description 目录服务接口
 * @Author wujian
 * @Date 10:12 2022/5/19
 * @Params
 * @Return
 **/
public interface MyDirMapper extends JpaRepository<MyDir, Integer>, JpaSpecificationExecutor<MyDir> {
    /**
     * @Description 根据父目录ID查询子目录
     * @Author wujian
     * @Date 14:22 2022/5/19
     * @Params [parentId, isUse]
     * @Return
     **/
    public List<MyDir> queryByParentIdIsAndIsUseIs(int parentId, int isUse);
    /**
     * @Description 查询指定用户的个人顶级目录
     * @Author wujian
     * @Date 14:30 2022/5/19
     * @Params [ownerId, isUse, isTop]
     * @Return
     **/
    public MyDir queryByOwnerIdIsAndIsUseIsAndRootDirIs(int ownerId,int isUse,boolean isRoot);
    /**
     * @Description 根据指定目录名称统计同层级下，是否存在同名目录
     * @Author wujian
     * @Date 15:31 2022/5/19
     * @Params [name, isUse, parentId]
     * @Return
     **/
    public long countByNameIsAndIsUseIsAndParentIdIs(String name,int isUse,int parentId);
}
