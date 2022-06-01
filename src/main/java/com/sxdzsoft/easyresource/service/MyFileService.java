package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.MyFile;
import com.sxdzsoft.easyresource.domain.User;

import java.util.List;

/**
 * @ClassName MyFileService
 * @Description 我的文件服务接口
 * @Author wujian
 * @Date 2022/5/31 15:29
 * @Version 1.0
 **/
public interface MyFileService {
    /**
     * @Description 新增表单文件
     * @Author wujian
     * @Date 14:42 2022/6/1
     * @Params [fileType文件类型,fileSize文件大小,itemId归属目录明细ID, store存储路径, orgname文件原始名, owner文件拥有者]
     * @Return
     **/
    public int addFormFile(int fileType,long fileSize,int itemId, String store, String orgname, User owner);
    /**
     * @Description 根据文件ID查询文件
     * @Author wujian
     * @Date 15:31 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    public MyFile queryFileById(int fileId);
    /**
     * @Description 查询指定文件夹下所有文件
     * @Author wujian
     * @Date 15:58 2022/6/1
     * @Params [dirId]
     * @Return
     **/
    public List<MyFile> queryByMyDirIdIs(int dirId,int isUse);
    /**
     * @Description 删除表单文件
     * @Author wujian
     * @Date 17:36 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    public int delFormFile(int fileId);
}
