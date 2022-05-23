package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.BaseFile;
import com.sxdzsoft.easyresource.domain.DirFilesModel;
import com.sxdzsoft.easyresource.domain.MyDir;
import com.sxdzsoft.easyresource.domain.User;

import java.util.List;

/**
 * @ClassName MyDirService
 * @Description 我的网盘服务
 * @Author wujian
 * @Date 2022/5/19 14:13
 * @Version 1.0
 **/
public interface MyDirService {
    /**
     * @Description 根据ID获取目录中子目录及文件
     * @Author wujian
     * @Date 14:19 2022/5/19
     * @Params [parentId]
     * @Return
     **/
    public DirFilesModel openDir(int parentId);
    /**
     * @Description 查询指定用户的个人顶级目录
     * @Author wujian
     * @Date 14:31 2022/5/19
     * @Params [ownerId, isUse, isTop]
     * @Return
     **/
    public MyDir queryByOwnerIdIsAndIsUseIsAndTopIs(int ownerId, int isUse, boolean isTop);
    /**
     * @Description 为指定用户创建个人顶级目录
     * @Author wujian
     * @Date 14:35 2022/5/19
     * @Params [userId]
     * @Return
     **/
    public MyDir addUserTopDir(int userId);
    /**
     * @Description 创建普通个人目录
     * @Author wujian
     * @Date 15:21 2022/5/19
     * @Params [name, parentId, owner]
     * @Return
     **/
    public MyDir addDir(String name,int parentId,User owner);
    /**
     * @Description 重命名目录
     * @Author wujian
     * @Date 14:41 2022/5/20
     * @Params [dirId, name]
     * @Return
     **/
    public int resetDirName(int dirId,String name);
    /**
     * @Description 粘贴目录（将当前目录移动到指定父目录下）
     * @Author wujian
     * @Date 16:20 2022/5/20
     * @Params [dirId, parentId]
     * @Return
     **/
    public int parseDir(int dirId,int parentId);
    /**
     * @Description 删除目录
     * @Author wujian
     * @Date 13:58 2022/5/23
     * @Params [dirId]
     * @Return
     **/
    public int delDir(int dirId);
    /**
     * @Description 共享目录到群组
     * @Author wujian
     * @Date 15:41 2022/5/23
     * @Params [currentDir, groups]
     * @Return
     **/
    public int shareDirToGroup(int currentDir,int[] groups);
}
